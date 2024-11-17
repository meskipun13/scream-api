package com.enigma.scream_api.repository;

import com.enigma.scream_api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_store (id, name_store, region, currency, developer_id) VALUES (:id, :name_store, :region, :currency, :developer_id)", nativeQuery = true)
    void createStore(@Param("id") String id,
                      @Param("name_store") String name_store,
                      @Param("region") String region,
                      @Param("currency") String currency,
                      @Param("developer_id") String developer_id);

    @Query(value = "SELECT * FROM m_store", nativeQuery = true)
    List<Store> findAllStores();

    @Query(value = "SELECT * FROM m_store WHERE id = :id", nativeQuery = true)
    Optional<Store> findStoreById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_store SET name_store = :name_store, region = :region, currency = :currency, developer_id = :developer_id WHERE id = :id", nativeQuery = true)
    void updateStore(@Param("id") String id,
                     @Param("name_store") String name_store,
                     @Param("region") String region,
                     @Param("currency") String currency,
                     @Param("developer_id") String developer_id);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM m_store WHERE id = :id", nativeQuery = true)
    void deleteStore(@Param("id") String id);

}
