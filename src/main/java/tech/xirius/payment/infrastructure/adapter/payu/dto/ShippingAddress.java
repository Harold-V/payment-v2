package tech.xirius.payment.infrastructure.adapter.payu.dto;

/**
 * Representa la dirección de envío asociada con una transacción de pago.
 * Esta clase se utiliza para almacenar la información de la dirección de envío
 * 
 * <p>
 * Contiene todos los campos necesarios para describir la dirección de envío,
 * tales como la calle, ciudad, estado, país y código postal.
 * </p>
 */
public record ShippingAddress(
                /**
                 * Calle principal de la dirección de envío.
                 * Este campo debe contener la primera línea de la dirección de envío.
                 */
                String street1,

                /**
                 * Segunda línea de la dirección de envío, si es aplicable.
                 * Este campo es opcional y se utiliza para agregar detalles adicionales a la
                 * dirección, como un apartamento o edificio.
                 */
                String street2,

                /**
                 * Ciudad donde se encuentra la dirección de envío.
                 * Este campo es obligatorio y debe contener el nombre de la ciudad.
                 */
                String city,

                /**
                 * Estado o provincia de la dirección de envío.
                 * Este campo es obligatorio si la ubicación lo requiere, por ejemplo, en países
                 * como los Estados Unidos.
                 */
                String state,

                /**
                 * País de destino de la dirección de envío.
                 * Este campo es obligatorio para poder identificar el país de destino.
                 */
                String country,

                /**
                 * Código postal de la dirección de envío.
                 * Este campo es obligatorio y debe contener el código postal correspondiente a
                 * la dirección.
                 */
                String postalCode,

                /**
                 * Número de teléfono de contacto asociado con la dirección de envío.
                 * Este campo es necesario para facilitar el contacto en caso de problemas con
                 * el envío.
                 */
                String phone) {

}
