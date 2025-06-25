package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TableNavigationPane extends BorderPane {
    private final Button btnBranches  = new Button("Branches");
    private final Button btnClients   = new Button("Clients");
    private final Button btnServices1 = new Button("Services1");
    private final Button btnServices2 = new Button("Services2");
    private final Button btnServiceTypes = new Button("Service Types");

    private final StackPane content = new StackPane();

    public TableNavigationPane(BranchView branchView,
                               ClientView clientView,
                               Service1View svc1View,
                               Service2View svc2View,
                               ServiceTypeView serviceTypeView) {

        HBox toolbar = new HBox(10, btnBranches, btnClients, btnServices1, btnServices2, btnServiceTypes);
        toolbar.setPadding(new Insets(10));
        setTop(toolbar);

        content.getChildren().add(svc1View.getView());
        setCenter(content);

        btnBranches.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                switchTo(branchView.getView());
            }
        });

        btnClients.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                switchTo(clientView.getView());
            }
        });

        btnServices1.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                switchTo(svc1View.getView());
            }
        });

        btnServices2.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                switchTo(svc2View.getView());
            }
        });

        btnServiceTypes.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                switchTo(serviceTypeView.getView());
            }
        });
    }

    private void switchTo(Node node) {
        content.getChildren().setAll(node);
    }
}
