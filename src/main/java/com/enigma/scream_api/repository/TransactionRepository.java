package com.enigma.scream_api.repository;

import com.enigma.scream_api.constant.PaymentStatus;
import com.enigma.scream_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO t_transaction (id, transaction_date, total_price, player_id, game_id, store_id, payment_status) " +
            "VALUES (:id, :transactionDate, :totalPrice, :playerId, :gameId, :storeId, :paymentStatus)", nativeQuery = true)
    void createTransaction(@Param("id") String id,
                           @Param("transactionDate") LocalDateTime transactionDate,
                           @Param("totalPrice") Long totalPrice,
                           @Param("playerId") String playerId,
                           @Param("gameId") String gameId,
                           @Param("storeId") String storeId,
                           @Param("paymentStatus") String paymentStatus);

    @Query(value = "SELECT * FROM t_transaction", nativeQuery = true)
    List<Transaction> findAllTransactions();

    @Query(value = "SELECT * FROM t_transaction WHERE id = :id", nativeQuery = true)
    Optional<Transaction> findTransactionById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE t_transaction SET transaction_date = :transactionDate, total_price = :totalPrice, player_id = :playerId, " +
            "game_id = :gameId, store_id = :storeId, payment_status = :paymentStatus WHERE id = :id", nativeQuery = true)
    void updateTransaction(@Param("id") String id,
                           @Param("transactionDate") LocalDateTime transactionDate,
                           @Param("totalPrice") Long totalPrice,
                           @Param("playerId") String playerId,
                           @Param("gameId") String gameId,
                           @Param("storeId") String storeId,
                           @Param("paymentStatus") String paymentStatus);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM t_transaction WHERE id = :id", nativeQuery = true)
    void deleteTransaction(@Param("id") String id);

    @Query(value = "SELECT * FROM t_transaction WHERE player_id = :playerId", nativeQuery = true)
    List<Transaction> findTransactionsByPlayerId(@Param("playerId") String playerId);

    @Query(value = "SELECT * FROM t_transaction WHERE game_id = :gameId", nativeQuery = true)
    List<Transaction> findTransactionsByGameId(@Param("gameId") String gameId);
}
