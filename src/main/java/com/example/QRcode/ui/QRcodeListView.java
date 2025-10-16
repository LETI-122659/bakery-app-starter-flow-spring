package com.example.QRcode.ui;

import com.example.QRcode.QRcode;
import com.example.QRcode.QRcodeService;
import com.example.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route("")//Por questão de capricho, apenas para abrir em alguma página
@PageTitle("QR Code Generator")
@Menu(order = 0, icon = "vaadin:qrcode", title = "QR Codes")
public class QRcodeListView extends Main {

    private final QRcodeService qrService;

    final TextField linkField;
    final Button createBtn;
    final Grid<QRcode> qrGrid;

    public QRcodeListView(QRcodeService qrService) {
        this.qrService = qrService;

        linkField = new TextField();
        linkField.setPlaceholder("Enter link to generate QR code");
        linkField.setAriaLabel("QR Code link");
        linkField.setMaxLength(QRcode.DESCRIPTION_MAX_LENGTH);
        linkField.setMinWidth("20em");

        createBtn = new Button("Generate QR", event -> createQR());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(getLocale())
                .withZone(ZoneId.systemDefault());

        qrGrid = new Grid<>();
        qrGrid.setItems(query -> qrService.list(toSpringPageRequest(query)).stream());

        qrGrid.addColumn(QRcode::getLink).setHeader("Link");
        qrGrid.addColumn(qr -> dateTimeFormatter.format(qr.getCreationDate())).setHeader("Creation Date");
        qrGrid.addComponentColumn(this::buildImage).setHeader("QR Code");
        qrGrid.setSizeFull();

        setSizeFull();
        addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Gap.SMALL
        );

        add(new ViewToolbar("QR Codes", ViewToolbar.group(linkField, createBtn)));
        add(qrGrid);
    }

    private void createQR() {
        qrService.createQRcode(linkField.getValue());
        qrGrid.getDataProvider().refreshAll();
        linkField.clear();
        Notification.show("QR Code generated", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private Image buildImage(QRcode qr) {
        String base64 = Base64.getEncoder().encodeToString(qr.getImageData());
        Image image = new Image("data:image/png;base64," + base64, "QR Code");
        image.setWidth("100px");
        image.setHeight("100px");
        return image;
    }
}
