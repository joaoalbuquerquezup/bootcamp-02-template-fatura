package br.com.zup.fatura.dto.response;

import br.com.zup.fatura.model.Transaction;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CurrentBillTransactionResponse {

    private BigDecimal value;

    private CurrentBillStoreResponse store;

    private LocalDateTime madeAt;

    public CurrentBillTransactionResponse(Transaction transaction) {
        this.value = transaction.getValue();
        this.store = new CurrentBillStoreResponse(transaction.getStore());
        this.madeAt = transaction.getMadeAt();
    }
}
