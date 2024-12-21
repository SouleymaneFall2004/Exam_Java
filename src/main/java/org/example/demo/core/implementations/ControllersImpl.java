package org.example.demo.core.implementations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.demo.core.Controllers;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.data.entities.enums.Status;

import java.io.IOException;
import java.util.Objects;

public abstract class ControllersImpl implements Controllers {
    @Override
    public void redirectTo(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Stage currentStage = (Stage) Stage.getWindows().stream()
                    .filter(window -> window instanceof Stage && window.isShowing())
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No active stage found"));

            Scene currentScene = currentStage.getScene();

            if (currentScene != null) {
                currentScene.setRoot(root);
            } else {
                currentStage.setScene(new Scene(root, 1000, 600));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Role checkRole(Object role) {
        if (Objects.equals(role.toString(), Role.ADMIN.name())) {
            return Role.ADMIN;
        }
        if (Objects.equals(role.toString(), Role.SHOPKEEPER.name())) {
            return Role.SHOPKEEPER;
        }
        if (Objects.equals(role.toString(), Role.CLIENT.name())) {
            return Role.CLIENT;
        }
        return null;
    }

    public void debtTable(TableColumn<Debt, Integer> idColumn, TableColumn<Debt, Integer> amountTotalColumn, TableColumn<Debt, Integer> amountSentColumn, TableColumn<Debt, Integer> amountMissingColumn, TableColumn<Debt, Status> statusColumn) {
        if (idColumn.getCellValueFactory() == null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            amountTotalColumn.setCellValueFactory(new PropertyValueFactory<>("amountTotal"));
            amountSentColumn.setCellValueFactory(new PropertyValueFactory<>("amountSent"));
            amountMissingColumn.setCellValueFactory(new PropertyValueFactory<>("amountMissing"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        }
    }

    @FXML
    private void accounts() {
        redirectTo("/org/example/demo/admin/accountList.fxml");
    }

    @FXML
    private void articles() {
        redirectTo("/org/example/demo/admin/articleList.fxml");
    }

    @FXML
    private void paidDebts() {
        redirectTo("/org/example/demo/admin/paidDebtList.fxml");
    }

    @FXML
    private void clients() {
        redirectTo("/org/example/demo/clientList.fxml");
    }

    @FXML
    private void debts() {
        redirectTo("/org/example/demo/debtList.fxml");
    }

    @FXML
    private void add() {
        redirectTo("/org/example/demo/debtAdd.fxml");
    }
}