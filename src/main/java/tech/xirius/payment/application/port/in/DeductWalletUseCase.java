package tech.xirius.payment.application.port.in;

import java.math.BigDecimal;

/**
 * Caso de uso para deducir dinero de la billetera del usuario.
 * <p>
 * Este caso de uso permite deducir una cantidad específica de dinero de la
 * billetera de un usuario.
 * </p>
 */
public interface DeductWalletUseCase {
    void deduct(String userId, BigDecimal amount);
}
