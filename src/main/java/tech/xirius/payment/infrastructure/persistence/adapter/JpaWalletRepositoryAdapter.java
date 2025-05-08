package tech.xirius.payment.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;
import tech.xirius.payment.infrastructure.persistence.mapper.WalletMapper;
import tech.xirius.payment.infrastructure.persistence.repository.JpaWalletRepository;

/**
 * Adaptador de repositorio para la entidad {@link Wallet} que implementa la
 * interfaz {@link WalletRepositoryPort}.
 * Esta clase se encarga de la persistencia de las carteras en la base de datos
 * utilizando JPA.
 * 
 * <p>
 * Proporciona métodos para guardar una cartera, encontrarla por ID de usuario o
 * ID de cartera, y obtener el saldo de la cartera.
 * </p>
 */
@RequiredArgsConstructor
@Component
public class JpaWalletRepositoryAdapter implements WalletRepositoryPort {

    /**
     * Repositorio JPA utilizado para interactuar con la base de datos de carteras.
     */
    private final JpaWalletRepository jpaWalletRepository;

    /**
     * Mapeador utilizado para convertir entre entidades de la base de datos y
     * objetos de dominio de {@link Wallet}.
     */
    private final WalletMapper walletMapper;

    /**
     * Busca una cartera en la base de datos utilizando el ID del usuario.
     * 
     * @param userId El ID del usuario asociado con la cartera.
     * @return Un Optional que contiene la cartera encontrada, si existe.
     */
    @Override
    public Optional<Wallet> findByUserId(String userId) {
        return jpaWalletRepository.findByUserId(userId)
                .map(walletMapper::toDomain); // Mapea la entidad a un objeto de dominio
    }

    /**
     * Busca el saldo de la cartera de un usuario en la base de datos.
     * 
     * @param userId El ID del usuario cuyo saldo se desea consultar.
     * @return Un Optional que contiene el saldo encontrado, si existe.
     */
    @Override
    public Optional<BigDecimal> findBalanceByUserId(String userId) {
        return jpaWalletRepository.findBalanceByUserId(userId); // Devuelve el saldo directamente
    }

    /**
     * Guarda una cartera en la base de datos.
     * 
     * @param wallet El objeto {@link Wallet} que se desea guardar.
     * @return El objeto {@link Wallet} guardado, ya convertido a un objeto de
     *         dominio.
     */
    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity entity = walletMapper.toEntity(wallet); // Mapea el objeto de dominio a la entidad JPA
        WalletEntity saved = jpaWalletRepository.save(entity); // Guarda la entidad en la base de datos
        return walletMapper.toDomain(saved); // Mapea la entidad guardada a un objeto de dominio
    }

    /**
     * Busca una cartera en la base de datos utilizando el ID de la cartera.
     * 
     * @param walletId El ID de la cartera a buscar.
     * @return Un Optional que contiene la cartera encontrada, si existe.
     */
    @Override
    public Optional<Wallet> findById(UUID walletId) {
        return jpaWalletRepository.findById(walletId)
                .map(walletMapper::toDomain); // Mapea la entidad a un objeto de dominio
    }

}
