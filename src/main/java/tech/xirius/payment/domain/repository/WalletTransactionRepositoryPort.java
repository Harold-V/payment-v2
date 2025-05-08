package tech.xirius.payment.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tech.xirius.payment.domain.model.WalletTransaction;

/**
 * Puerto de repositorio para operaciones relacionadas con transacciones de
 * billetera.
 * <p>
 * Esta interfaz define los métodos necesarios para persistir y recuperar
 * información
 * de transacciones realizadas en las billeteras, siguiendo el patrón de
 * Arquitectura
 * Hexagonal (Puertos y Adaptadores).
 * </p>
 * <p>
 * Permite la gestión de transacciones independientemente de la tecnología de
 * persistencia utilizada en la implementación, siguiendo el principio de
 * inversión de dependencias.
 * </p>
 *
 * @author [Tu nombre]
 * @version 1.0
 * @see WalletTransaction
 * @see org.springframework.data.domain.Page
 * @see org.springframework.data.domain.Pageable
 * @since 1.0
 */
public interface WalletTransactionRepositoryPort {

    /**
     * Persiste una nueva transacción de billetera en el repositorio.
     * <p>
     * Este método guarda el registro de una operación financiera realizada
     * en una billetera específica. La transacción representa un movimiento
     * de fondos y su estado asociado.
     * </p>
     *
     * @param transaction La transacción de billetera a persistir
     */
    void save(WalletTransaction transaction);

    /**
     * Recupera todas las transacciones asociadas a una billetera específica de
     * forma paginada.
     * <p>
     * Este método permite consultar el historial de transacciones de una billetera
     * con soporte para paginación, facilitando la visualización y exploración del
     * historial de movimientos.
     * </p>
     *
     * @param walletId Identificador único de la billetera cuyas transacciones se
     *                 desean recuperar
     * @param pageable Configuración de paginación y ordenamiento para los
     *                 resultados
     * @return Una página de transacciones que pertenecen a la billetera
     *         especificada
     */
    Page<WalletTransaction> findAllByWalletId(UUID walletId, Pageable pageable);
}