package br.com.zup.fatura.listener;

import br.com.zup.fatura.event.TransactionEvent;
import br.com.zup.fatura.model.Bill;
import br.com.zup.fatura.model.Card;
import br.com.zup.fatura.model.Transaction;
import br.com.zup.fatura.repository.BillRepository;
import br.com.zup.fatura.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static br.com.zup.fatura.model.enums.BillStatus.OPEN;

@Component
public class TransactionKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionKafkaListener.class);

    private final CardRepository cardRepository;
    private final BillRepository billRepository;
    private final EntityManager entityManager; //2

    public TransactionKafkaListener(CardRepository cardRepository,
                                    BillRepository billRepository,
                                    EntityManager entityManager) {
        this.cardRepository = cardRepository;
        this.billRepository = billRepository;
        this.entityManager = entityManager;
    }

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    @Transactional
    public void listen(TransactionEvent transactionEvent) { //3

        Card card = transactionEvent.getCardModel(this.cardRepository::findByReferenceId); //5

        var optionalBill = this.billRepository.findByCardReferenceIdAndBillStatus(card.getReferenceId(), OPEN); //6
        Bill bill = optionalBill.orElse(new Bill(card)); //7

        Transaction transaction = transactionEvent.toModel(card); // 8
        bill.addTransaction(transaction);

        this.billRepository.save(bill);
        this.entityManager.persist(transaction);

        LOGGER.info("Transação salva: {}", transaction);
    }
}
