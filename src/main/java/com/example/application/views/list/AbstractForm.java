package com.example.application.views.list;

import com.example.application.data.service.NotificationService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.lang.Nullable;

public abstract class AbstractForm<T> extends FormLayout {

    Button save = new Button("Save");

    Button delete = new Button("Delete");

    Button close = new Button("Close");

    Binder<T> binder;

    private transient T value;
    private transient Class<T> clazz;

    protected transient NotificationService notificationService;

    protected AbstractForm(Class<T> clazz, NotificationService notificationService) {
        super();
        this.notificationService = notificationService;
        this.clazz = clazz;
        addClassName("contact-form");
    }

    protected void initBinder() {
        this.binder = new BeanValidationBinder<>(clazz);
        binder.bindInstanceFields(this);
    }

    HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(this::onDeleteValue);
        close.addClickListener(this::onClose);

        return new HorizontalLayout(save, delete, close);
    }

    abstract void onDeleteValue(ClickEvent<Button> event);

    void onClose(ClickEvent<Button> event) {
    }

    private void validateAndSave() {
        try {
            binder.writeBean(value);
            fireEvent(this.createSaveEvent());
        } catch (ValidationException e) {
            e.printStackTrace();
            notificationService.showErrorNotification(e.getLocalizedMessage());
        }
    }

    protected abstract AbstractForm.SaveEvent<T> createSaveEvent();

    public void setValue(T value) {
        this.value = value;
        binder.readBean(value);
    }

    protected abstract void populateItems();

    public T getValue() {
        return this.value;
    }

    public abstract static class AbstractFormEvent<T> extends ComponentEvent<AbstractForm<T>> {

        private final transient T value;

        protected AbstractFormEvent(AbstractForm<T> source, @Nullable T value) {
            super(source, false);
            this.value = value;
        }

        T getValue() {
            return value;
        }

    }

    public static class SaveEvent<T> extends AbstractFormEvent<T> {

        SaveEvent(AbstractForm<T> source, T value) {
            super(source, value);
        }
    }

    public static class DeleteEvent<T> extends AbstractFormEvent<T> {

        DeleteEvent(AbstractForm<T> source, T value) {
            super(source, value);
        }

    }

    public static class CloseEvent<T> extends AbstractFormEvent<T> {

        CloseEvent(AbstractForm<T> source) {
            super(source, null);
        }
    }

    @Override
    public <E extends ComponentEvent<?>> Registration addListener(Class<E> eventType,
        ComponentEventListener<E> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
