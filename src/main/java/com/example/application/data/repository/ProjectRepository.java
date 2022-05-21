package com.example.application.data.repository;

import com.example.application.data.entity.Project;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("select p from Project p " +
        "where lower(p.title) like lower(concat('%', :searchTerm, '%')) " +
        "or lower(p.description) like lower(concat('%', :searchTerm, '%'))")
    List<Project> search(String searchTerm);
}
