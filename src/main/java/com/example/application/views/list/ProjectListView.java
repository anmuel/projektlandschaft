package com.example.application.views.list;

import com.example.application.data.entity.Project;
import com.example.application.data.service.CrmService;
import com.example.application.data.service.NotificationService;
import com.example.application.views.MainLayout;
import com.example.application.views.list.ProjectForm.DeleteEvent;
import com.example.application.views.list.ProjectForm.SaveEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
        form = new ProjectForm(notificationService,
            crmService.findAllCompanies(),
            crmService.findAllContacts(null),
            crmService.findMethodischeKompetenzen(null),
            crmService.findAllProjects(null));
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

    @Override
    protected void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description");
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
            .setAutoWidth(true).setSortable(true).setComparator((p1, p2) -> {
                if (p1.istAktiv() && p2.istAktiv()) {
                    return 0;
                } else if (p1.istAktiv() && !p2.istAktiv()) {
                    return 1;
                } else if (!p1.istAktiv() && p2.istAktiv()) {
                    return -1;
                } else {
                    return 0;
                }
            });
        grid.addColumn(project -> project.getAuftragGeber() != null ? project.getAuftragGeber().getName() : "")
            .setHeader("Auftraggeber").setSortable(true);
        grid.addColumn(project -> project.getProjektLaufzeitVon() != null ? project.getProjektLaufzeitVon().toString()
            : null).setHeader("Projektlaufzeit Von").setSortable(true);
        grid.addColumn(project -> project.getProjektLaufzeitBis() != null ? project.getProjektLaufzeitBis().toString()
            : null).setHeader("Projektlaufzeit Bis").setSortable(true);
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editValue(event.getValue()));
    }

    private static final SerializableBiConsumer<Span, Project> statusComponentUpdater = (span, project) -> {
        boolean isAvailable = project.istAktiv();
        String theme = String.format("badge %s", isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        VaadinIcon vaadinIcon = isAvailable ? VaadinIcon.CHECK : VaadinIcon.CLOSE_SMALL;
        Icon icon = vaadinIcon.create();
        icon.getElement().getThemeList().add(theme);
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        // Accessible label
        String label = isAvailable ? "aktiv" : "inaktiv";
        icon.getElement().setAttribute("aria-label", label);
        // Tooltip
        icon.getElement().setAttribute("title", label);
        span.add(icon);
    };

    private static ComponentRenderer<Span, Project> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }
}
