package com.enigma.scream_api.repository;

import com.enigma.scream_api.entity.Game;
import com.enigma.scream_api.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_game (id, name_game, price, platform, category, stock, description,store_id) VALUES (:id, :name_game, :price, :platform, :category, :stock, :description, :store_id)", nativeQuery = true)
    void createGame(@Param("id") String id,
                         @Param("name_game") String name_game,
                         @Param("price") Long price,
                         @Param("platform") String platform,
                         @Param("category") String category,
                         @Param("stock") Integer stock,
                         @Param("description") String description,
                         @Param("store_id") String store_id);

    @Query(value = "SELECT * FROM m_game", nativeQuery = true)
    List<Game> findAllGames();

    @Query(value = "SELECT * FROM m_game WHERE id = :id", nativeQuery = true)
    Optional<Game> findGameById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_game SET name_game = :name_game, price = :price, platform= :platform, category = :category, stock = :stock, description = :description, store_id = :store_id WHERE id = :id", nativeQuery = true)
    void updateGame(@Param("id") String id,
                    @Param("name_game") String name_game,
                    @Param("price") Long price,
                    @Param("platform") String platform,
                    @Param("category") String category,
                    @Param("stock") Integer stock,
                    @Param("description") String description,
                    @Param("store_id") String store_id);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM m_game WHERE id = :id", nativeQuery = true)
    void deleteGame(@Param("id") String id);

}
