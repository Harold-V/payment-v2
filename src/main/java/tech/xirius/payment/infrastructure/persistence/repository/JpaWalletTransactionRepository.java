package tech.xirius.payment.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;

/**
 * Repositorio JPA para la entidad {@link WalletTransactionEntity}.
 * Esta interfaz extiende {@link JpaRepository}, proporcionando métodos para
 * realizar operaciones CRUD
 * en la tabla {@code wallet_transactions} de la base de datos, además de
 * consultas personalizadas para obtener
 * las transacciones asociadas a una billetera.
 * 
 * <p>
 * Este repositorio se utiliza para interactuar con la base de datos de
 * transacciones de billetera,
 * permitiendo obtener transacciones asociadas a una billetera específica con
 * paginación.
 * </p>
 */
public interface JpaWalletTransactionRepository extends JpaRepository<WalletTransactionEntity, UUID> {

    /**
     * Consulta todas las transacciones de una billetera específica utilizando el ID
     * de la billetera.
     * 
     * @param walletId El ID de la billetera cuyos registros de transacciones se
     *                 desean consultar.
     * @param pageable El objeto Pageable que permite la paginación de los
     *                 resultados.
     * @return Una página de transacciones de billetera asociadas al ID de la
     *         billetera proporcionada.
     */
    Page<WalletTransactionEntity> findAllByWalletId(UUID walletId, Pageable pageable);

}
