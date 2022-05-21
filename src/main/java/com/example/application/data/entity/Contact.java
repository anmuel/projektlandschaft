package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.example.application.views.list.Nameable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

@Entity
public class Contact extends AbstractEntity implements Nameable {

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"employees"})
    private Company company;

    @Override
    public String getName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }

    @NotNull
    @ManyToOne
    private Status status;

    @Email
    @NotEmpty
    private String email = "";

    @OneToMany(mappedBy = "projektLeiter")
    @Nullable
    List<Project> projekteInLeitung;

    @OneToMany(mappedBy = "projektleiterStellvertreter")
    List<Project> projekteInLeitungStellvertreter;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
