package br.com.zup.fatura.event;

import br.com.zup.fatura.model.Card;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CardEvent {

    private String id;
    private String email;

    public Card toModel() {
        return new Card(id, email);
    }

    public String getId() {
        return id;
    }
}
