package com.example.application.views.list;

import com.example.application.data.entity.Project;
import com.example.application.data.service.CrmService;
import com.example.application.data.service.NotificationService;
import com.example.application.views.MainLayout;
import com.example.application.views.list.ProjectForm.DeleteEvent;
import com.example.application.views.list.ProjectForm.SaveEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Route(value = "projects", layout = MainLayout.class)
@PageTitle("Projects | Projektlandschaft")
@Component
@Scope("prototype")
public class ProjectListView extends AbstractListView<Project> {

    public ProjectListView(CrmService crmService, NotificationService notificationService) {
        super(crmService, notificationService, Project.class);
    }

    @Override
    protected void updateList() {
        grid.setItems(crmService.findAllProjects(filterText.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new ProjectForm(notificationService, crmService.findAllCompanies(), crmService.findAllContacts(null));
        form.setWidth("25em");
        form.addListener(ProjectForm.SaveEvent.class, this::saveProject);
        form.addListener(ProjectForm.DeleteEvent.class, this::deleteProject);
        form.addListener(ProjectForm.CloseEvent.class, e -> closeEditor());
    }

    private <E extends ComponentEvent<?>> void deleteProject(DeleteEvent e) {
        crmService.deleteProject(e.getValue());
        notificationService.showNotification(String.format("Project %s deleted", e.getValue().getTitle()),
            NotificationVariant.LUMO_SUCCESS);
        afterValueInteraction();
    }

    private <E extends ComponentEvent<?>> void saveProject(SaveEvent e) {
        crmService.saveProject(e.getValue());
        notificationService.showNotification(String.format("Project %s saved", e.getValue().getTitle()),
            NotificationVariant.LUMO_SUCCESS);
        afterValueInteraction();
    }

    protected void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description");
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
            .setAutoWidth(true);
        grid.addColumn(project -> project.getAuftragGeber() != null ? project.getAuftragGeber().getName() : "")
            .setHeader("Auftraggeber");
        grid.addColumn(project -> project.getProjektLaufzeitVon() != null ? project.getProjektLaufzeitVon().toString()
            : null).setHeader("Projektlaufzeit Von");
        grid.addColumn(project -> project.getProjektLaufzeitBis() != null ? project.getProjektLaufzeitBis().toString()
            : null).setHeader("Projektlaufzeit Bis");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editValue(event.getValue()));
    }

    private static final SerializableBiConsumer<Span, Project> statusComponentUpdater = (span, project) -> {
        boolean isAvailable = project.istAktiv();
        String theme = String.format("badge %s", isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(project.istAktiv() ? "aktiv" : "inaktiv");
    };

    private static ComponentRenderer<Span, Project> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }
}
