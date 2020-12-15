package br.com.zup.fatura.dto.response;

import br.com.zup.fatura.model.Bill;
import br.com.zup.fatura.model.enums.BillStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.zup.fatura.model.enums.BillStatus.OPEN;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CurrentBillResponse {

    private LocalDate initialDate;

    private LocalDate finalDate;

    private LocalDate payDay;

    private List<CurrentBillTransactionResponse> currentBillTransactionResponseList = new ArrayList<>();

    private BillStatus billStatus = OPEN;

    private BigDecimal currentTotalValue;


    public CurrentBillResponse(Bill bill) {
        this.initialDate = bill.getInitialDate();
        this.finalDate = bill.getFinalDate();
        this.payDay = bill.getPayDay();
        this.currentTotalValue = bill.getTotalValue();
        this.currentBillTransactionResponseList = bill.getTransactionList().stream()
                .map(CurrentBillTransactionResponse::new).collect(Collectors.toList());
    }
}
