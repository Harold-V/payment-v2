package tech.xirius.payment.domain.repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import tech.xirius.payment.domain.model.Wallet;

/**
 * Puerto de repositorio para operaciones relacionadas con billeteras de
 * usuarios.
 * <p>
 * Esta interfaz define los métodos necesarios para persistir y recuperar
 * información de billeteras en el sistema, siguiendo el patrón de Arquitectura
 * Hexagonal (Puertos y Adaptadores).
 */
public interface WalletRepositoryPort {

    /**
     * Busca una billetera asociada a un usuario específico.
     * <p>
     * Este método permite recuperar los detalles completos de la billetera
     * asociada a un usuario basándose en un identificador único.
     * </p>
     *
     * @param userId Identificador único del usuario
     * @return Un Optional conteniendo la billetera si existe, o un Optional vacío
     *         si no se encuentra
     */
    Optional<Wallet> findByUserId(String userId);

    /**
     * Busca una billetera por su identificador único.
     * <p>
     * Este método recupera una billetera específica utilizando su UUID.
     * </p>
     *
     * @param walletId Identificador único de la billetera
     * @return Un Optional conteniendo la billetera si existe, o un Optional vacío
     *         si no se encuentra
     */
    Optional<Wallet> findById(UUID walletId);

    /**
     * Recupera únicamente el saldo de la billetera de un usuario.
     * <p>
     * Este método está optimizado para consultas que solo necesitan conocer
     * el balance actual sin recuperar todos los detalles de la billetera.
     * </p>
     *
     * @param userId Identificador único del usuario
     * @return Un Optional conteniendo el saldo si la billetera existe, o un
     *         Optional vacío si no se encuentra
     */
    Optional<BigDecimal> findBalanceByUserId(String userId);

    /**
     * Guarda o actualiza una billetera en el repositorio.
     * <p>
     * Si la billetera no existe, este método la creará. Si ya existe, actualizará
     * sus datos con los nuevos valores proporcionados.
     * </p>
     *
     * @param wallet La billetera a guardar o actualizar
     * @return La billetera guardada, posiblemente con campos actualizados según el
     *         repositorio
     */
    Wallet save(Wallet wallet);
}