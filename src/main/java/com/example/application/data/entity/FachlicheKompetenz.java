package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class FachlicheKompetenz extends AbstractEntity {

    @ManyToMany()
    @JoinTable(name = "projekte_fachliche_kompetenzen",
        joinColumns = {@JoinColumn(name = "competence_id",
            referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "project_id",
                referencedColumnName = "id")})
    private final List<Project> projects = new LinkedList<>();


    private String name;

    public List<Project> getProjects() {
        return projects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FachlicheKompetenz that = (FachlicheKompetenz) o;
        return projects.equals(that.projects) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), projects, name);
    }
}
