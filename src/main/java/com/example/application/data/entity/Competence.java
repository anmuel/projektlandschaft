package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.ManyToMany;

public class Competence extends AbstractEntity {

    @ManyToMany
    private List<Project> projects = new LinkedList<>();

    private CompetenceType type;

    private String name;

    public List<Project> getProjects() {
        return projects;
    }

    public CompetenceType getType() {
        return type;
    }

    public void setType(CompetenceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
