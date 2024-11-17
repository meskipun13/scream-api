package com.enigma.scream_api.repository;

import com.enigma.scream_api.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_player (id, name, address , phone_number, email) VALUES (:id, :name, :address, :phone_number, :email)", nativeQuery = true)
    void createPlayer(@Param("id") String id,
                       @Param("name") String name,
                       @Param("email") String email,
                       @Param("phone_number") String phoneNumber,
                       @Param("address") String address);

    @Query(value = "SELECT * FROM m_player", nativeQuery = true)
    List<Player> findAllPlayers();

    @Query(value = "SELECT * FROM m_player WHERE id = :id", nativeQuery = true)
    Optional<Player> findPlayerById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_player SET name = :name, email = :email, phone_number = :phone_number, address = :address WHERE id = :id", nativeQuery = true)
    void updatePlayer(@Param("id") String id,
                       @Param("name") String name,
                       @Param("email") String email,
                       @Param("phone_number") String phoneNumber,
                       @Param("address") String address);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM m_player WHERE id = :id", nativeQuery = true)
    void deletePlayer(@Param("id") String id);

}
