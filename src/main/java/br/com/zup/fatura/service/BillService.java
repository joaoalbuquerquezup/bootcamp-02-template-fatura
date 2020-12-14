package br.com.zup.fatura.service;

import br.com.zup.fatura.event.TransactionEvent;
import br.com.zup.fatura.model.Bill;
import br.com.zup.fatura.model.Card;
import br.com.zup.fatura.repository.BillRepository;
import br.com.zup.fatura.repository.CardRepository;
import org.springframework.stereotype.Service;

import static br.com.zup.fatura.model.enums.BillStatus.OPEN;

@Service
public class BillService {

    private final CardRepository cardRepository;
    private final BillRepository billRepository;

    public BillService(CardRepository cardRepository, BillRepository billRepository) {
        this.cardRepository = cardRepository;
        this.billRepository = billRepository;
    }

    public Bill getBillBy(TransactionEvent transactionEvent) {
        Card card = transactionEvent.getCardModel(this.cardRepository::findByReferenceId);
        var optionalBill = this.billRepository.findByCardReferenceIdAndBillStatus(card.getReferenceId(), OPEN);
        return optionalBill.orElse(new Bill(card));
    }
}
