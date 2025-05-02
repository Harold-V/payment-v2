package tech.xirius.payment.infrastructure.web.api.v1;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.xirius.payment.application.port.in.RechargeWalletUseCase;
import tech.xirius.payment.infrastructure.web.dto.RechargeRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletResource {

    private final RechargeWalletUseCase rechargeUseCase;

    @PostMapping("/rechargePrueba")
    public ResponseEntity<Void> rechargePrueba(@Valid @RequestBody RechargeRequest request) {
        log.info("Recargando wallet para usuario: {}", request.userId());
        rechargeUseCase.recharge(request.userId(), request.amount(), Optional.empty());
        return ResponseEntity.ok().build();
    }
}
