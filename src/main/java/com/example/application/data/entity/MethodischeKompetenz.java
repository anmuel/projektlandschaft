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
public class MethodischeKompetenz extends AbstractEntity {

    private String name;

    @ManyToMany()
    @JoinTable(name = "project_methodische_kompetenzen",
        joinColumns = @JoinColumn(name = "competence_id",
            referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "project_id", referencedColumnName = "id"))
    private List<Project> projects = new LinkedList<>();

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
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
        MethodischeKompetenz that = (MethodischeKompetenz) o;
        return name.equals(that.name) && projects.equals(that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, projects);
    }
}
