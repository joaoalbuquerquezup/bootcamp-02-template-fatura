package br.com.zup.fatura.event;

import br.com.zup.fatura.model.Card;
import br.com.zup.fatura.model.Transaction;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TransactionEvent {

    private String id;

    @JsonProperty("valor")
    private BigDecimal value;

    @JsonProperty("estabelecimento")
    private StoreEvent storeEvent;

    @JsonProperty("cartao")
    private CardEvent cardEvent;

    @JsonProperty("efetivadaEm")
    private LocalDateTime madeAt;

    public Transaction toModel(Card card) {
        return new Transaction(this.value, this.storeEvent.toModel(), card, this.madeAt, this.id);
    }

    public Card getCardModel(Function<String, Optional<Card>> cardLoader) {
        String cardReferenceId = this.cardEvent.getId();
        Optional<Card> optionalCard = cardLoader.apply(cardReferenceId);
        return optionalCard.orElse(this.cardEvent.toModel());
    }

}
