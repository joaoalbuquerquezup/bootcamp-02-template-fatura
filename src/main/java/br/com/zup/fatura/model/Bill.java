package br.com.zup.fatura.model;

import br.com.zup.fatura.model.enums.BillStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;
import static javax.persistence.EnumType.STRING;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"card_id", "billStatus"}))
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private LocalDate initialDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate finalDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate payDay;

    @NotNull @NotEmpty
    @OneToMany(mappedBy = "bill")
    private List<Transaction> transactionList = new ArrayList<>();

    @NotNull @Column(nullable = false)
    @Enumerated(STRING)
    private BillStatus billStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    private Card card;

    /**
     * Hibernate usage only
     */
    @Deprecated
    protected Bill() { }

    public Bill(@NotNull Card card) {
        this.card = card;

        LocalDate now = LocalDate.now();
        this.initialDate = now.withDayOfMonth(1);
        this.finalDate = now.withDayOfMonth(now.lengthOfMonth());
        this.payDay = finalDate.plusDays(10);
        this.billStatus = BillStatus.OPEN;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
        transaction.setBill(this);
    }

    public List<Transaction> getTransactionList() {
        return Collections.unmodifiableList(this.transactionList);
    }

    public Card getCard() {
        return card;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public LocalDate getPayDay() {
        return payDay;
    }

    public BigDecimal getTotalValue() {
        return this.transactionList.stream()
                .map(Transaction::getValue)
                .reduce(ZERO, BigDecimal::add);
    }

}
