package com.example.application.data.service;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void showErrorNotification(String text) {
        this.showNotification(text, NotificationVariant.LUMO_ERROR);
    }

    public void showSuccessNotification(String text) {
        this.showNotification(text, NotificationVariant.LUMO_SUCCESS);
    }

    public void showNotification(String text, NotificationVariant notificationVariant) {
        // When creating a notification using the constructor,
        // the duration is 0-sec by default which means that
        // the notification does not close automatically.
        Notification notification = new Notification();
        notification.addThemeVariants(notificationVariant);

        Div textDiv = new Div(new Text(text));

        Button closeButton = new Button(new IronIcon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(textDiv, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    public Dialog showConfirmationDialog(String text, OnDialogConfirmAction action) {
        Dialog dialog = new Dialog();
        VerticalLayout layout = new VerticalLayout();
        Div div = new Div();
        div.setText(text);
        layout.add(div);
        dialog.add(layout);
        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(okButton);
        buttonLayout.add(cancelButton);
        okButton.addClickListener(e -> {
            action.run();
            dialog.close();
        });
        cancelButton.addClickListener(e -> dialog.close());
        layout.add(buttonLayout);

        dialog.open();
        return dialog;
    }

    public interface OnDialogConfirmAction {

        void run();
    }
}
