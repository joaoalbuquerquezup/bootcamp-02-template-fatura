package br.com.zup.fatura.listener;

import br.com.zup.fatura.event.TransactionEvent;
import br.com.zup.fatura.model.Bill;
import br.com.zup.fatura.model.Transaction;
import br.com.zup.fatura.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class TransactionKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionKafkaListener.class);

    private final EntityManager entityManager;
    private final BillService billService; // 1

    public TransactionKafkaListener(EntityManager entityManager,
                                    BillService billService) {
        this.entityManager = entityManager;
        this.billService = billService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    @Transactional
    public void listen(TransactionEvent transactionEvent) { // 2

        Bill bill = this.billService.getBillBy(transactionEvent); // 3

        Transaction transaction = transactionEvent.toModel(bill.getCard()); // 5
        bill.addTransaction(transaction);

        this.entityManager.persist(bill);
        this.entityManager.persist(transaction);

        LOGGER.info("Transação salva: {}", transaction);
    }
}
