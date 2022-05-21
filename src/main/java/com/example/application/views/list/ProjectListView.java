package com.example.application.views.list;

import com.example.application.data.entity.Project;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.example.application.views.list.ProjectForm.DeleteEvent;
import com.example.application.views.list.ProjectForm.SaveEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Collections;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Route(value = "projects", layout = MainLayout.class)
@PageTitle("Projects | Projektlandschaft")
@Component
@Scope("prototype")
public class ProjectListView extends AbstractListView<Project> {

    public ProjectListView(CrmService crmService) {
        super(crmService, Project.class);
    }

    @Override
    protected void updateList() {
        grid.setItems(crmService.findAllProjects(filterText.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new ProjectForm(Collections.emptyList(), Collections.emptyList());
        form.setWidth("25em");
        form.addListener(ProjectForm.SaveEvent.class, this::saveProject);
        form.addListener(ProjectForm.DeleteEvent.class, this::deleteProject);
        form.addListener(ProjectForm.CloseEvent.class, e -> closeEditor());
    }

    private <E extends ComponentEvent<?>> void deleteProject(DeleteEvent e) {
        crmService.deleteProject(e.getValue());
        afterValueInteraction();
    }

    private <E extends ComponentEvent<?>> void saveProject(SaveEvent e) {
        crmService.saveProject(e.getValue());
        afterValueInteraction();
    }

    protected void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "istAktiv");
//        grid.addColumn(project -> project.getAuftragGeber().getName()).setHeader("Auftraggeber");
//        grid.addColumn(project -> project.getProjektLaufzeitVon().toString()).setHeader("Projektlaufzeit Von");
//        grid.addColumn(project -> project.getProjektLaufzeitBis().toString()).setHeader("Projektlaufzeit Bis");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editValue(event.getValue()));
    }
}
