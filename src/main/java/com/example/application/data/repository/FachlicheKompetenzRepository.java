package com.example.application.data.repository;

import com.example.application.data.entity.FachlicheKompetenz;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()
public interface FachlicheKompetenzRepository extends JpaRepository<FachlicheKompetenz, UUID> {

    @Query
    List<FachlicheKompetenz> findAllByName(String name);
}
