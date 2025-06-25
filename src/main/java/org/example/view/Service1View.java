package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.controller.ServiceController;
import org.example.model.Service1;
import org.example.model.Service2;

public class ServiceView {

    private final ServiceController controller = new ServiceController();

    public void show(Stage stage) {
        // Создаём TabPane для вкладок
        TabPane tabs = new TabPane();
        Tab requestsTab = createRequestsTab();
        Tab pricingTab = createPricingTab();

        tabs.getTabs().addAll(requestsTab, pricingTab);

        // Создаём кнопки для переключения вкладок
        Button btnRequests = new Button("Requests");
        Button btnPricing = new Button("Pricing");

        btnRequests.setOnAction(e -> tabs.getSelectionModel().select(requestsTab));
        btnPricing.setOnAction(e -> tabs.getSelectionModel().select(pricingTab));

        // Располагаем кнопки и TabPane в окне
        HBox buttonBox = new HBox(10, btnRequests, btnPricing);
        buttonBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(buttonBox); // Кнопки сверху
        root.setCenter(tabs);   // TabPane в центре

        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Управление услугами");
        stage.setScene(scene);
        stage.show();
    }

    private Tab createRequestsTab() {
        TableView<Service1> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getAllService1()));

        TableColumn<Service1, Number> colId  = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        TableColumn<Service1, Number> colCli = new TableColumn<>("Client");
        colCli.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        TableColumn<Service1, Number> colTyp = new TableColumn<>("Type");
        colTyp.setCellValueFactory(new PropertyValueFactory<>("serviceTypeId"));
        TableColumn<Service1, Number> colBr  = new TableColumn<>("Branch");
        colBr.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        TableColumn<Service1, Number> colReq = new TableColumn<>("Req #");
        colReq.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));

        table.getColumns().addAll(colId, colCli, colTyp, colBr, colReq);

        Button add = new Button("Добавить");
        Button edit = new Button("Изменить");
        Button del = new Button("Удалить");
        HBox box = new HBox(10, add, edit, del);
        box.setPadding(new Insets(10));

        add.setOnAction(e -> editService1(null, table));
        edit.setOnAction(e -> editService1(table.getSelectionModel().getSelectedItem(), table));
        del.setOnAction(e -> {
            Service1 s = table.getSelectionModel().getSelectedItem();
            controller.deleteService1(s.getServiceId());
            table.getItems().remove(s);
        });

        BorderPane bp = new BorderPane(table);
        bp.setBottom(box);

        return new Tab("Requests", bp);
    }

    private Tab createPricingTab() {
        TableView<Service2> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(controller.getAllService2()));

        TableColumn<Service2, Number> colId = new TableColumn<>("Service ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        TableColumn<Service2, Number> colPr = new TableColumn<>("Price");
        colPr.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Service2, Number> colDi = new TableColumn<>("Discount");
        colDi.setCellValueFactory(new PropertyValueFactory<>("discount"));

        table.getColumns().addAll(colId, colPr, colDi);

        Button add = new Button("Добавить");
        Button edit = new Button("Изменить");
        Button del = new Button("Удалить");
        HBox box = new HBox(10, add, edit, del);
        box.setPadding(new Insets(10));

        add.setOnAction(e -> editService2(null, table));
        edit.setOnAction(e -> editService2(table.getSelectionModel().getSelectedItem(), table));
        del.setOnAction(e -> {
            Service2 s = table.getSelectionModel().getSelectedItem();
            controller.deleteService2(s.getServiceId());
            table.getItems().remove(s);
        });

        BorderPane bp = new BorderPane(table);
        bp.setBottom(box);

        return new Tab("Pricing", bp);
    }

    private void editService1(Service1 svc, TableView<Service1> table) {
        Stage d = new Stage();
        d.initModality(Modality.APPLICATION_MODAL);

        TextField tfClient = new TextField();
        TextField tfType   = new TextField();
        TextField tfBranch = new TextField();
        TextField tfReq    = new TextField();

        if (svc != null) {
            tfClient.setText(String.valueOf(svc.getClientId()));
            tfType  .setText(String.valueOf(svc.getServiceTypeId()));
            tfBranch.setText(String.valueOf(svc.getBranchId()));
            tfReq   .setText(String.valueOf(svc.getRequestNumber()));
        }

        Button save = new Button("Сохранить");
        save.setOnAction(e -> {
            int cli = Integer.parseInt(tfClient.getText());
            int typ = Integer.parseInt(tfType.getText());
            int br  = Integer.parseInt(tfBranch.getText());
            int rq  = Integer.parseInt(tfReq.getText());
            if (svc == null) {
                Service1 s = controller.createService1(cli, typ, br, rq, null, null, 0, 0, 0);
                table.getItems().add(s);
            } else {
                controller.updateService1(svc.getServiceId(), cli, typ, br, rq, null, null, 0, 0, 0);
                svc.setClientId(cli);
                svc.setServiceTypeId(typ);
                svc.setBranchId(br);
                svc.setRequestNumber(rq);
                table.refresh();
            }
            d.close();
        });

        VBox vb = new VBox(5,
                new Label("Client ID"), tfClient,
                new Label("Type ID"),   tfType,
                new Label("Branch ID"), tfBranch,
                new Label("Req №"),     tfReq,
                save
        );
        vb.setPadding(new Insets(10));

        d.setScene(new Scene(vb));
        d.showAndWait();
    }

    private void editService2(Service2 svc, TableView<Service2> table) {
        Stage d = new Stage();
        d.initModality(Modality.APPLICATION_MODAL);

        TextField tfId = new TextField();
        TextField tfPr = new TextField();
        TextField tfDi = new TextField();

        if (svc != null) {
            tfId.setText(String.valueOf(svc.getServiceId()));
            tfPr.setText(String.valueOf(svc.getPrice()));
            tfDi.setText(String.valueOf(svc.getDiscount()));
        }

        Button save = new Button("Сохранить");
        save.setOnAction(e -> {
            int id   = Integer.parseInt(tfId.getText());
            double p = Double.parseDouble(tfPr.getText());
            int dsc  = Integer.parseInt(tfDi.getText());
            if (svc == null) {
                Service2 s = controller.createService2(id, p, dsc);
                table.getItems().add(s);
            } else {
                controller.updateService2(id, p, dsc);
                svc.setPrice(p);
                svc.setDiscount(dsc);
                table.refresh();
            }
            d.close();
        });

        VBox vb = new VBox(5,
                new Label("Service ID"), tfId,
                new Label("Price"),      tfPr,
                new Label("Discount"),   tfDi,
                save
        );
        vb.setPadding(new Insets(10));

        d.setScene(new Scene(vb));
        d.showAndWait();
    }
}
