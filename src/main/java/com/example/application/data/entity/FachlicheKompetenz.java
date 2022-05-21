package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class FachlicheKompetenz extends AbstractEntity {

    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "fachliche_kompetenz_projects",
        joinColumns = @JoinColumn(name = "fachliche_kompetenz_id", referencedColumnName = "id"))
    private List<Project> projects = new ArrayList<>();

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

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
