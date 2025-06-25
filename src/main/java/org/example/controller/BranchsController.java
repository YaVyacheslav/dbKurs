package org.example.controller;

import org.example.model.Branchs;
import org.example.view.BranchsView;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

public class BranchsController implements Observer {
    private final Branchs model;
    private final BranchsView view;

    public BranchsController(Branchs model, BranchsView view) {
        this.model = model;
        this.view = view;
        initializeButtons();
        model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        model.refreshTable(view.getTableView());
    }

    private void initializeButtons() {
        view.getAddButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                try {
                    model.addBranch(view.getAddressField().getText());
                } catch (SQLException ex) {
                    view.showError("Ошибка при добавлении филиала: " + ex.getMessage());
                }
            }
        });

        view.getUpdateButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                try {
                    int id = view.getTableView().getSelectionModel().getSelectedItem().getBid();
                    String addr = view.getAddressField().getText();
                    model.updateBranch(id, addr);
                } catch (Exception ex) {
                    view.showError("Ошибка при обновлении: " + ex.getMessage());
                }
            }
        });

        view.getDeleteButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent e) {
                try {
                    int id = view.getTableView().getSelectionModel().getSelectedItem().getBid();
                    model.removeBranch(id);
                } catch (Exception ex) {
                    view.showError("Ошибка при удалении: " + ex.getMessage());
                }
            }
        });
    }
}
