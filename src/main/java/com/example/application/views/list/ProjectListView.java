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

@Route(value = "", layout = MainLayout.class)
@PageTitle("Projects | Projektlandschaft")
@Component
@Scope("prototype")
public class ProjectListView extends ListView {

    protected String formName = "project";

    Grid<Project> grid = new Grid<>(Project.class);

    TextField filterText = new TextField();

    ProjectForm form;

    transient CrmService crmService;

    public ProjectListView(CrmService crmService) {
        super(crmService);

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), getContent());
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("title", "description", "projektLaufzeitVon", "projektLaufzeitBis", "istAktiv");
        grid.addColumn(project -> project.getAuftragGeber().getName()).setHeader("Auftraggeber");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editProject(event.getValue()));
    }

    private void editProject(Project project) {
        if (project == null) {

        } else {
            // TODO
        }
    }
}
