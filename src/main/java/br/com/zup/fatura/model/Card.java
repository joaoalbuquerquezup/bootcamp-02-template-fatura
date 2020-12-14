package br.com.zup.fatura.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String referenceId;

    @NotBlank
    @Column(nullable = false)
    private String email;
    
    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Card() { }

    public Card(@NotBlank String referenceId,
                @NotBlank String email) {
        this.referenceId = referenceId;
        this.email = email;
    }

    public String getReferenceId() {
        return referenceId;
    }
}
