package tech.xirius.payment.infrastructure.web.api.v1;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.xirius.payment.application.port.in.DeductWalletUseCase;
import tech.xirius.payment.application.port.in.GetWalletBalanceUseCase;
import tech.xirius.payment.application.port.in.GetWalletTransactionsUseCase;
import tech.xirius.payment.application.port.in.RechargeWalletUseCase;
import tech.xirius.payment.infrastructure.persistence.mapper.WalletTransactionResponseMapper;
import tech.xirius.payment.infrastructure.web.dto.RechargeRequest;
import tech.xirius.payment.infrastructure.web.dto.WalletBalanceResponse;
import tech.xirius.payment.infrastructure.web.dto.WalletTransactionResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletResource {

    private final RechargeWalletUseCase rechargeUseCase;
    private final DeductWalletUseCase deductUseCase;
    private final GetWalletBalanceUseCase getWalletBalanceUseCase;
    private final GetWalletTransactionsUseCase getTransactionsUseCase;
    private final WalletTransactionResponseMapper walletTransactionResponseMapper;

    @PostMapping("/rechargeDemo")
    public ResponseEntity<Void> rechargeDemo(@Valid @RequestBody RechargeRequest request) {
        log.info("Recargando wallet para usuario: {}", request.userId());
        rechargeUseCase.recharge(request.userId(), request.amount(), Optional.empty());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deduct")
    public ResponseEntity<Void> deduct(@Valid @RequestBody RechargeRequest request) {
        log.info("Descontando wallet para usuario: {}", request.userId());
        deductUseCase.deduct(request.userId(), request.amount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<WalletBalanceResponse> getBalance(@RequestParam String userId) {
        log.info("Consultando saldo para usuario: {}", userId);

        WalletBalanceResponse response = getWalletBalanceUseCase.getWalletBalance(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Page<WalletTransactionResponse>> getTransactions(
            @PathVariable String userId,
            @PageableDefault(size = 10, sort = "timestamp") Pageable pageable) {

        log.info("Consultando transacciones paginadas para usuario: {}", userId);

        // Obtener las transacciones y mapearlas a la respuesta
        Page<WalletTransactionResponse> response = getTransactionsUseCase
                .getTransactionsByUserId(userId, pageable)
                .map(transaction -> walletTransactionResponseMapper
                        .toResponse(transaction, userId));

        return ResponseEntity.ok(response);
    }

}
