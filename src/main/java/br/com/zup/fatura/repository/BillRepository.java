package br.com.zup.fatura.repository;

import br.com.zup.fatura.model.Bill;
import br.com.zup.fatura.model.enums.BillStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {

    Optional<Bill> findByCardReferenceIdAndBillStatus(String cardReferenceId, BillStatus open);

    @EntityGraph(attributePaths = { "transactionList", "transactionList.store" })
    Optional<Bill> findByCardIdAndAndBillStatus(UUID cardId, BillStatus billStatus);
}
