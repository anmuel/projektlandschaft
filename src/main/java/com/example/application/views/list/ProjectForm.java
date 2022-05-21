package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Project;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import java.util.List;

public class ProjectForm extends AbstractForm<Project> {

    TextField title = new TextField("Titel");

    TextField description = new TextField("Kurzbeschreibung");

    ComboBox<Company> auftragGeber = new ComboBox<>("Auftraggeber");

    private transient final List<Company> auftrageberItems;


    public ProjectForm(List<Company> companies, List<Contact> contacts) {
        super(Project.class);

        this.auftrageberItems = companies;

        populateItems();

        add(title, description, auftragGeber, createButtonsLayout());
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
        auftragGeber.setItems(this.auftrageberItems);
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
