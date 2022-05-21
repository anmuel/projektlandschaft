package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Project;
import com.example.application.data.service.NotificationService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import java.util.List;

public class ProjectForm extends AbstractForm<Project> {

    TextField title = new TextField("Titel");

    TextField description = new TextField("Kurzbeschreibung");

    ComboBox<Company> auftragGeber = new ComboBox<>("Auftraggeber");

    ComboBox<Contact> projectLeiter = new ComboBox<>("Projektleiter");
    ComboBox<Contact> stlvProjektleiter = new ComboBox<>("Stlv. Projektleiter");

    private final transient List<Company> auftrageberItems;
    private final transient List<Contact> contacts;


    public ProjectForm(NotificationService notificationService, List<Company> companies, List<Contact> contacts) {
        super(Project.class, notificationService);

        this.auftrageberItems = companies;
        this.contacts = contacts;

        populateItems();

        add(title, description, auftragGeber, projectLeiter, stlvProjektleiter, createButtonsLayout());
        initBinder();
    }

    @Override
    void onDeleteValue(ClickEvent<Button> event) {
        fireEvent(new DeleteEvent(this, getValue()));
    }

    @Override
    protected SaveEvent createSaveEvent() {
        return new SaveEvent(this, getValue());
    }

    @Override
    protected void populateItems() {
        this.title.setRequired(true);

        auftragGeber.setItems(this.auftrageberItems);
        auftragGeber.setItemLabelGenerator((ItemLabelGenerator<Company>) Company::getName);

        projectLeiter.setItems(this.contacts);
        projectLeiter.setItemLabelGenerator((ItemLabelGenerator<Contact>) Contact::getName);
        projectLeiter.setRequired(true);

        stlvProjektleiter.setItems(this.contacts);
        stlvProjektleiter.setItemLabelGenerator((ItemLabelGenerator<Contact>) Contact::getName);
    }

    public static class SaveEvent extends AbstractForm.SaveEvent<Project> {

        SaveEvent(ProjectForm form, Project project) {
            super(form, project);
        }
    }

    public static class DeleteEvent extends AbstractForm.DeleteEvent<Project> {

        DeleteEvent(ProjectForm form, Project project) {
            super(form, project);
        }
    }

    public static class CloseEvent extends AbstractForm.CloseEvent<Project> {

        CloseEvent(ProjectForm form) {
            super(form);
        }
    }
}
