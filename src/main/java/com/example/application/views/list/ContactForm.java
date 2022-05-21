package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.service.NotificationService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import java.util.List;

public class ContactForm extends AbstractForm<Contact> {

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");


    private final transient List<Company> companies;
    private final transient List<Status> statuses;

    public ContactForm(NotificationService notificationService, List<Company> companies, List<Status> statuses) {
        super(Contact.class, notificationService);

        this.companies = companies;
        this.statuses = statuses;

        populateItems();

        add(firstName,
            lastName,
            email,
            company,
            status,
            createButtonsLayout());
        initBinder();
    }

    private void setStatuses(List<Status> statuses) {
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);
    }

    private void setCompanies(List<Company> companies) {
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
    }

    @Override
    protected void populateItems() {
        setCompanies(companies);
        setStatuses(statuses);
    }

    @Override
    void onDeleteValue(ClickEvent<Button> event) {
        notificationService.showConfirmationDialog("Are you sure?", () -> fireEvent(new DeleteEvent(this, getValue())));
    }

    @Override
    void onClose(ClickEvent<Button> event) {
        fireEvent(new CloseEvent(this));
    }

    @Override
    protected AbstractForm.SaveEvent<Contact> createSaveEvent() {
        return new SaveEvent(this, getValue());
    }

    public static class SaveEvent extends AbstractForm.SaveEvent<Contact> {

        SaveEvent(ContactForm form, Contact contact) {
            super(form, contact);
        }
    }

    public static class DeleteEvent extends AbstractForm.DeleteEvent<Contact> {

        DeleteEvent(ContactForm form, Contact contact) {
            super(form, contact);
        }
    }

    public static class CloseEvent extends AbstractForm.CloseEvent<Contact> {

        CloseEvent(ContactForm form) {
            super(form);
        }
    }
}