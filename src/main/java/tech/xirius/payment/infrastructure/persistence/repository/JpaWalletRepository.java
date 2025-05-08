package tech.xirius.payment.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio JPA para la entidad {@link WalletEntity}.
 * Esta interfaz extiende {@link JpaRepository}, proporcionando métodos para
 * realizar operaciones CRUD
 * en la tabla wallet de la base de datos, además de consultas
 * personalizadas para buscar una billetera
 * por el ID de usuario y consultar el saldo de una billetera por el ID de
 * usuario.
 * 
 * <p>
 * Este repositorio se utiliza para interactuar con la base de datos de
 * billeteras, permitiendo el acceso a los datos de las billeteras y sus saldos.
 * </p>
 */
public interface JpaWalletRepository extends JpaRepository<WalletEntity, UUID> {

    /**
     * Busca una billetera en la base de datos utilizando el ID del usuario.
     * 
     * @param userId El ID del usuario cuya billetera se desea buscar.
     * @return Un Optional que contiene la {@link WalletEntity} encontrada,
     *         si existe.
     */
    Optional<WalletEntity> findByUserId(String userId);

    /**
     * Consulta el saldo de la billetera de un usuario en la base de datos.
     * 
     * @param userId El ID del usuario cuyo saldo se desea consultar.
     * @return Un Optional que contiene el saldo encontrado, si existe.
     */
    @Query("SELECT w.balance FROM WalletEntity w WHERE w.userId = :userId")
    Optional<BigDecimal> findBalanceByUserId(String userId);

}
