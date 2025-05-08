package tech.xirius.payment.infrastructure.persistence.adapter;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;
import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;
import tech.xirius.payment.infrastructure.persistence.mapper.WalletTransactionMapper;
import tech.xirius.payment.infrastructure.persistence.repository.JpaWalletTransactionRepository;

/**
 * Adaptador de repositorio para la entidad {@link WalletTransaction} que
 * implementa la interfaz {@link WalletTransactionRepositoryPort}.
 * Esta clase se encarga de la persistencia de las transacciones de la billetera
 * en la base de datos utilizando JPA.
 * 
 * <p>
 * Proporciona métodos para guardar una transacción de billetera y consultar
 * todas las transacciones asociadas a una billetera por su ID.
 * </p>
 */
@RequiredArgsConstructor
@Component
public class JpaWalletTransactionAdapter implements WalletTransactionRepositoryPort {

    /**
     * Repositorio JPA utilizado para interactuar con la base de datos de
     * transacciones de billetera.
     */
    private final JpaWalletTransactionRepository transactionJpaRepository;

    /**
     * Mapeador utilizado para convertir entre entidades de la base de datos y
     * objetos de dominio de {@link WalletTransaction}.
     */
    private final WalletTransactionMapper transactionMapper;

    /**
     * Guarda una transacción de billetera en la base de datos.
     * 
     * @param transaction El objeto {@link WalletTransaction} que se desea guardar.
     */
    @Override
    public void save(WalletTransaction transaction) {
        WalletTransactionEntity entity = transactionMapper.toEntity(transaction); // Mapea el objeto de dominio a la
                                                                                  // entidad JPA
        transactionJpaRepository.save(entity); // Guarda la entidad en la base de datos
    }

    /**
     * Consulta todas las transacciones de una billetera por su ID.
     * 
     * @param walletId El ID de la billetera asociada con las transacciones.
     * @param pageable El objeto {@link Pageable} que permite la paginación de los
     *                 resultados.
     * @return Una página de transacciones de billetera.
     */
    @Override
    public Page<WalletTransaction> findAllByWalletId(UUID walletId, Pageable pageable) {
        return transactionJpaRepository.findAllByWalletId(walletId, pageable)
                .map(transactionMapper::toDomain); // Mapea las entidades a objetos de dominio
    }

}
