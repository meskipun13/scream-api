package com.enigma.scream_api.repository;

import com.enigma.scream_api.entity.Developer;
import com.enigma.scream_api.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, String> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_developer (id, name, phone_number, email, website) VALUES (:id, :name, :phone_number, :email, :website)", nativeQuery = true)
    void createDeveloper(@Param("id") String id,
                      @Param("name") String name,
                      @Param("email") String email,
                      @Param("phone_number") String phoneNumber,
                      @Param("website") String website);

    @Query(value = "SELECT * FROM m_developer", nativeQuery = true)
    List<Developer> findAllDevelopers();

    @Query(value = "SELECT * FROM m_developer WHERE id = :id", nativeQuery = true)
    Optional<Developer> findDeveloperById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_developer SET name = :name, email = :email, phone_number = :phone_number, website = :website WHERE id = :id", nativeQuery = true)
    void updateDeveloper(@Param("id") String id,
                         @Param("name") String name,
                         @Param("email") String email,
                         @Param("phone_number") String phoneNumber,
                         @Param("website") String website);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM m_developer WHERE id = :id", nativeQuery = true)
    void deleteDeveloper(@Param("id") String id);
}
