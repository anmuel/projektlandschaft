package com.example.application.data.repository;

import com.example.application.data.entity.MethodischeKompetenz;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodischeKompetenzRepository extends JpaRepository<MethodischeKompetenz, UUID> {
    List<MethodischeKompetenz> findAllByName(String name);
}
