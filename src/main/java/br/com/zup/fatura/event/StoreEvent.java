package br.com.zup.fatura.event;

import br.com.zup.fatura.model.Store;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class StoreEvent {

    @JsonProperty(value = "nome")
    private String name;

    @JsonProperty(value = "cidade")
    private String city;

    @JsonProperty(value = "endereco")
    private String address;

    public Store toModel() {
        return new Store(name, city, address);
    }
}
