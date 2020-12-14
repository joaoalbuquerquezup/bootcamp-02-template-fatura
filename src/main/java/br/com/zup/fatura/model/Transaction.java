package br.com.zup.fatura.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    private BigDecimal value;

    @NotNull
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    private Store store;

    @NotNull
    @ManyToOne(optional = false)
    private Card card;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime madeAt;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bill bill;

    @NotBlank
    @Column(nullable = false)
    private String referenceId;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Transaction() { }

    public Transaction(BigDecimal value,
                       @NotNull Store store,
                       @NotNull Card card,
                       @NotNull LocalDateTime madeAt,
                       @NotBlank String referenceId) {
        this.value = value;
        this.store = store;
        this.card = card;
        this.madeAt = madeAt;
        this.referenceId = referenceId;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "value=" + value +
                ", referenceId='" + referenceId + '\'' +
                '}';
    }
}
