package com.example.application.views;

import com.example.application.views.list.ListView;
import com.example.application.views.list.ProjectListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
            new DrawerToggle(),
            logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Kontakte", ListView.class);
        RouterLink projectsLink = new RouterLink("Projekte", ProjectListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        projectsLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            projectsLink,
            listLink,
            dashboardLink
        ));
    }
}