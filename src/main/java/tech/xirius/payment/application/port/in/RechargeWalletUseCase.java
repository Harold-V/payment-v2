package tech.xirius.payment.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Caso de uso para recargar dinero en la billetera del usuario.
 * <p>
 * Este caso de uso permite recargar una cantidad específica de dinero en la
 * billetera de un usuario.
 * </p>
 */
public interface RechargeWalletUseCase {
    void recharge(String userId, BigDecimal amount, UUID paymentId);
}
