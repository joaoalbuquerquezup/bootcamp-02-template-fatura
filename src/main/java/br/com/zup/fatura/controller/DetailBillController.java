package br.com.zup.fatura.controller;

import br.com.zup.fatura.dto.response.CurrentBillResponse;
import br.com.zup.fatura.model.Bill;
import br.com.zup.fatura.repository.BillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static br.com.zup.fatura.model.enums.BillStatus.OPEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/card")
public class DetailBillController {

    private final BillRepository billRepository;

    public DetailBillController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @GetMapping("/{cardId}/current-bill")
    public ResponseEntity<CurrentBillResponse> detail(@PathVariable UUID cardId) {

        Optional<Bill> optionalBill = this.billRepository.findByCardIdAndAndBillStatus(cardId, OPEN);
        Bill bill = optionalBill.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        CurrentBillResponse currentBillResponse = new CurrentBillResponse(bill);

        return ResponseEntity.ok(currentBillResponse);
    }
}
