package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TableNavigationPane extends BorderPane {
    private final Button btnBranches  = new Button("Филиалы");
    private final Button btnClients   = new Button("Клиенты");
    private final Button btnServices1 = new Button("Услуги1");
    private final Button btnServices2 = new Button("Услуги2");
    private final Button btnServiceTypes = new Button("Виды услуг");

    private final StackPane content = new StackPane();

    public TableNavigationPane(BranchsView branchView,
                               ClientsView clientView,
                               Services1View svc1View,
                               Services2View svc2View,
                               ServicesTypesView serviceTypeView) {

        HBox toolbar = new HBox(10, btnBranches, btnClients, btnServices1, btnServices2, btnServiceTypes);
        toolbar.setPadding(new Insets(10));
        setTop(toolbar);

        content.getChildren().add(svc1View.getView());
        setCenter(content);

        btnBranches.setOnAction(e -> switchTo(branchView.getView()));
        btnClients.setOnAction(e -> switchTo(clientView.getView()));
        btnServices1.setOnAction(e -> switchTo(svc1View.getView()));
        btnServices2.setOnAction(e -> switchTo(svc2View.getView()));
        btnServiceTypes.setOnAction(e -> switchTo(serviceTypeView.getView()));

        btnBranches.getStyleClass().add("button");
        btnClients.getStyleClass().add("button");
        btnServices1.getStyleClass().add("button");
        btnServices2.getStyleClass().add("button");
        btnServiceTypes.getStyleClass().add("button");
    }

    private void switchTo(Node node) {

        content.getChildren().setAll(node);
    }
}
