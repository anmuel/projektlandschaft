package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class TechnologischeKompetenz extends AbstractEntity {

    private String name;

    @ManyToMany()
    @JoinTable(name = "projekte_technologische_kompetenzen", joinColumns = @JoinColumn(name = "competence_id",
        referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Project> projects = new LinkedList<>();

    List<Project> getProjects() {
        return projects;
    }

    void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
