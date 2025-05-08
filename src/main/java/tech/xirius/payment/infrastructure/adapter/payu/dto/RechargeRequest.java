package tech.xirius.payment.infrastructure.adapter.payu.dto;

import java.math.BigDecimal;

import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.PaymentMethod;
import tech.xirius.payment.domain.model.PaymentProvider;

/**
 * Representa una solicitud de recarga en el sistema de pagos.
 * Esta clase contiene toda la información necesaria para procesar una recarga,
 * ya sea a través de tarjeta de crédito/débito o mediante PSE.
 * 
 * <p>
 * Este record incluye detalles sobre el usuario, el pago y los datos de la
 * tarjeta de crédito o del pago por PSE.
 * </p>
 */
public record RechargeRequest(
                /**
                 * Identificador único del usuario que realiza la recarga.
                 * Este campo es obligatorio para asociar la recarga al usuario correspondiente.
                 */
                String userId,

                /**
                 * El monto de la recarga a realizar. Debe ser una cantidad positiva.
                 */
                BigDecimal amount,

                /**
                 * La moneda en la que se realiza la recarga, COP.
                 */
                Currency currency,

                /**
                 * El proveedor de pagos utilizado para procesar la transacción, por ejemplo,
                 * PayU.
                 */
                PaymentProvider paymentProvider,

                /**
                 * El método de pago utilizado, como tarjeta de crédito o débito, PSE.
                 */
                PaymentMethod paymentMethod,

                /**
                 * Descripción adicional de la transacción.
                 */
                String description,

                /**
                 * Nombre completo del titular de la cuenta o usuario que está realizando la
                 * recarga.
                 */
                String fullName,

                /**
                 * Dirección de correo electrónico del usuario.
                 * Este campo es necesario para la comunicación de transacciones o problemas
                 * relacionados con el pago.
                 */
                String emailAddress,

                /**
                 * Número de teléfono de contacto del usuario.
                 * Este campo se puede utilizar para contacto directo en caso de problemas con
                 * la transacción.
                 */
                String contactPhone,

                /**
                 * Número de identificación del usuario. Puede ser un número de documento de
                 * identidad o similar.
                 */
                String dniNumber,

                /**
                 * Tipo de documento de identificación utilizado por el usuario, como DNI,
                 * pasaporte, CC, etc.
                 */
                String dniType,

                /**
                 * Dirección de envío asociada con el pago. Utilizado para pagos que involucren
                 * productos físicos.
                 */
                ShippingAddress shippingAddress,

                /**
                 * Este campo solo se utiliza cuando el usuario desea pagar con tarjeta de
                 * crédito o débito.
                 * Contiene los detalles de la tarjeta de crédito/débito proporcionada por el
                 * usuario.
                 */
                CardDetails cardDetails,

                /**
                 * Este campo solo se utiliza cuando el usuario desea pagar mediante PSE (Pago
                 * Seguro en Línea).
                 * Define el tipo de usuario relacionado con la transacción (por ejemplo,
                 * Persona Natural (N) o Persona Juridica (J)).
                 */
                String userType) {

        public RechargeRequest {
                if (currency == null) {
                        currency = Currency.COP;
                }
        }

}
