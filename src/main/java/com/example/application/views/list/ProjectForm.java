package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.MethodischeKompetenz;
import com.example.application.data.entity.Project;
import com.example.application.data.service.NotificationService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.data.selection.MultiSelectionListener;
import com.vaadin.flow.function.ValueProvider;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ProjectForm extends AbstractForm<Project> {

    @Override
    void onClose(ClickEvent<Button> event) {
        fireEvent(new CloseEvent(this));
    }

    private final transient List<MethodischeKompetenz> methodischeKompetenzItems;
    TextField title = new TextField("Titel");

    TextField description = new TextField("Kurzbeschreibung");

    ComboBox<Company> auftragGeber = new ComboBox<>("Auftraggeber");

    ComboBox<Contact> projectLeiter = new ComboBox<>("Projektleiter");
    ComboBox<Contact> stlvProjektleiter = new ComboBox<>("Stlv. Projektleiter");

    Checkbox istAktiv = new Checkbox("Aktiv");

    TextField strategischesProjektziel = new TextField("Strategisches Projektziel");
    TextField fachlichesThemengebiet = new TextField("Fachliches Themengebiet");
    CheckboxGroup<MethodischeKompetenz> methodischeKompetenz = new CheckboxGroup<>();

    private final transient List<Company> auftrageberItems;
    private final transient List<Contact> contacts;


    public ProjectForm(NotificationService notificationService, List<Company> companies, List<Contact> contacts,
        List<MethodischeKompetenz> methodischeKompetenzen) {
        super(Project.class, notificationService);

        this.auftrageberItems = companies;
        this.contacts = contacts;
        this.methodischeKompetenzItems = methodischeKompetenzen;

        populateItems();

        add(title, istAktiv, description, auftragGeber, projectLeiter, stlvProjektleiter, strategischesProjektziel,
            fachlichesThemengebiet, methodischeKompetenz, createButtonsLayout());

        this.initBinder();
    }

    @Override
    protected void initBinder() {
        this.binder = new BeanValidationBinder<>(Project.class);
        binder.bind(methodischeKompetenz,
            (ValueProvider<Project, Set<MethodischeKompetenz>>) project -> new LinkedHashSet<>(
                project.getMethodischeKompetenz()),
            (Setter<Project, Set<MethodischeKompetenz>>) (project, methodischeKompetenzs) -> project
                .setMethodischeKompetenz(
                    new LinkedList<>(methodischeKompetenzs)));
        binder.bindInstanceFields(this);
    }

    @Override
    void onDeleteValue(ClickEvent<Button> event) {
        notificationService.showConfirmationDialog("Are you sure?", () -> fireEvent(new DeleteEvent(this, getValue())));
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

        methodischeKompetenz.setItems(methodischeKompetenzItems);
        methodischeKompetenz.setLabel("Methodische Kompetenz(en)");
        methodischeKompetenz.setItemLabelGenerator(
            (ItemLabelGenerator<MethodischeKompetenz>) MethodischeKompetenz::getName);
        methodischeKompetenz.addSelectionListener(
            (MultiSelectionListener<CheckboxGroup<MethodischeKompetenz>, MethodischeKompetenz>) event -> getValue().setMethodischeKompetenz(
                new LinkedList<>(event.getValue())));
        methodischeKompetenz.addThemeVariants(CheckboxGroupVariant.LUMO_HELPER_ABOVE_FIELD);
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
