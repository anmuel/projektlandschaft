package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.FachlicheKompetenz;
import com.example.application.data.entity.MethodischeKompetenz;
import com.example.application.data.entity.Project;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.FachlicheKompetenzRepository;
import com.example.application.data.repository.MethodischeKompetenzRepository;
import com.example.application.data.repository.ProjectRepository;
import com.example.application.data.repository.StatusRepository;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;
    private final ProjectRepository projectRepository;
    private final FachlicheKompetenzRepository fachlicheKompetenzRepository;
    private final MethodischeKompetenzRepository methodischeKompetenzRepository;

    public CrmService(ContactRepository contactRepository,
        CompanyRepository companyRepository,
        StatusRepository statusRepository,
        ProjectRepository projectRepository,
        FachlicheKompetenzRepository fachlicheKompetenzRepository,
        MethodischeKompetenzRepository methodischeKompetenzRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
        this.projectRepository = projectRepository;
        this.fachlicheKompetenzRepository = fachlicheKompetenzRepository;
        this.methodischeKompetenzRepository = methodischeKompetenzRepository;
    }

    /**
     * Find all contacts according to the stringFilter.
     *
     * @param stringFilter if present (non-null or non-blank String), this will be applied to the firstName and lastName
     *                     of the contacts
     * @return all contacts
     * @see ContactRepository#search(String)
     */
    public List<Contact> findAllContacts(@Nullable String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }

    public List<Project> findAllProjects(@Nullable String filterTextValue) {
        if (filterTextValue == null || filterTextValue.isEmpty()) {
            return projectRepository.findAll();
        } else {
            return projectRepository.search(filterTextValue);
        }
    }

    public void saveProject(Project project) {
        this.projectRepository.save(project);
    }

    public void deleteProject(Project project) {
        this.projectRepository.delete(project);
    }

    public List<FachlicheKompetenz> findFachlicheKompetenzen(@Nullable String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return fachlicheKompetenzRepository.findAllByName(searchTerm);
        } else {
            return fachlicheKompetenzRepository.findAll();
        }
    }

    public List<MethodischeKompetenz> findMethodischeKompetenzen(@Nullable String searchTerm) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return methodischeKompetenzRepository.findAllByName(searchTerm);
        } else {
            return methodischeKompetenzRepository.findAll();
        }
    }
}