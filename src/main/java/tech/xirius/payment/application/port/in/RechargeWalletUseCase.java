package tech.xirius.payment.application.port.in;

import java.util.Map;

import tech.xirius.payment.infrastructure.web.dto.RechargeRequest;

/**
 * Caso de uso para recargar dinero en la billetera del usuario.
 * <p>
 * Este caso de uso permite recargar una cantidad específica de dinero en la
 * billetera de un usuario.
 * </p>
 */
public interface RechargeWalletUseCase {

    Map<String, Object> recharge(RechargeRequest paymentRequest);
}
