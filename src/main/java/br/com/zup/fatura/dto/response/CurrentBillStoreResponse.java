package br.com.zup.fatura.dto.response;

import br.com.zup.fatura.model.Store;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CurrentBillStoreResponse {

    private String name;
    private String city;
    private String address;

    public CurrentBillStoreResponse(Store store) {
        this.name = store.getName();
        this.city = store.getCity();
        this.address = store.getAddress();
    }
}
