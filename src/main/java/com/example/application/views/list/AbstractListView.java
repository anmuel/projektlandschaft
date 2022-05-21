package com.example.application.views.list;

import com.example.application.data.AbstractEntity;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.lang.reflect.InvocationTargetException;
import org.springframework.lang.Nullable;

public abstract class AbstractListView<T extends AbstractEntity> extends VerticalLayout {

    protected transient CrmService crmService;
    protected Grid<T> grid;
    TextField filterText = new TextField();
    protected AbstractForm<T> form;

    private final Class<T> clazz;

    protected AbstractListView(CrmService service, Class<T> clazz) {
        super();
        this.crmService = service;
        this.clazz = clazz;
        this.grid = new Grid<>(clazz);

        addClassName("list-view");

        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    protected void afterValueInteraction() {
        updateList();
        closeEditor();
    }

    protected abstract void updateList();

    protected abstract void configureForm();

    protected void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
    }

    protected void editValue(@Nullable T value) {
        if (value == null) {
            closeEditor();
        } else {
            form.setValue(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    protected HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addButton = new Button("Add " + this.clazz.getSimpleName());
        addButton.addClickListener(click -> addValue());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    protected void addValue() {
        grid.asSingleSelect().clear();
        try {
            editValue((T) this.clazz.getConstructors()[0].newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    protected void closeEditor() {
        form.setValue(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
