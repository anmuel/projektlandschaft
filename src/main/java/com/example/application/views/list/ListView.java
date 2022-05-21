package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.example.application.views.list.ContactForm.DeleteEvent;
import com.example.application.views.list.ContactForm.SaveEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Collections;
import javax.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

@Route(value = "contacts", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@PermitAll
@org.springframework.stereotype.Component
@Scope("prototype")
public class ListView extends AbstractListView<Contact> {

    public ListView(CrmService crmService) {
        super(crmService, Contact.class);
    }

    protected void updateList() {
        grid.setItems(crmService.findAllContacts(filterText.getValue()));
    }

    protected void configureForm() {
        form = new ContactForm(Collections.emptyList(), Collections.emptyList());
        form.setWidth("25em");
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveContact(SaveEvent event) {
        crmService.saveContact(event.getValue());
        updateList();
        closeEditor();
    }

    private void deleteContact(DeleteEvent event) {
        crmService.deleteContact(event.getValue());
        updateList();
        closeEditor();
    }

    @Override()
    protected void configureGrid() {
        super.configureGrid();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editValue(event.getValue()));
    }
}