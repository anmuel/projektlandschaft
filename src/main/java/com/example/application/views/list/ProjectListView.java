package com.example.application.views.list;

import com.example.application.data.entity.Project;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Route(value = "projects", layout = MainLayout.class)
@PageTitle("Projects | Projektlandschaft")
@Component
@Scope("prototype")
public class ProjectListView extends AbstractListView<Project> {

    protected String formName = "project";

    Grid<Project> grid = new Grid<>(Project.class);

    TextField filterText = new TextField();

    ProjectForm form;

    transient CrmService crmService;

    public ProjectListView(CrmService crmService) {
        super(crmService, Project.class);

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), getContent());
    }

    @Override
    protected void updateList() {
        grid.setItems(crmService.findAllProjects(filterText.getValue()));
    }

    @Override
    protected void configureForm() {

    }

    protected void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "istAktiv");
        grid.addColumn(project -> project.getAuftragGeber().getName()).setHeader("Auftraggeber");
        grid.addColumn(project -> project.getProjektLaufzeitVon().toString()).setHeader("Projektlaufzeit Von");
        grid.addColumn(project -> project.getProjektLaufzeitBis().toString()).setHeader("Projektlaufzeit Bis");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editValue(event.getValue()));
    }
}
