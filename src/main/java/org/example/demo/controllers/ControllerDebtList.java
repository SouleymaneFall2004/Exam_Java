package org.example.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.demo.controllers.utils.Factory;
import org.example.demo.controllers.utils.GlobalFilter;
import org.example.demo.core.implementations.ControllersImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;
import org.example.demo.services.interfaces.IServiceDebt;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerDebtList extends ControllersImpl implements Initializable {
    private final Factory factory = new Factory();
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private Label messageLabel;
    @FXML
    private TableColumn<Debt, Integer> idColumn;
    @FXML
    private TableColumn<Debt, Integer> amountTotalColumn;
    @FXML
    private TableColumn<Debt, Integer> amountSentColumn;
    @FXML
    private TableColumn<Debt, Integer> amountMissingColumn;
    @FXML
    private TableColumn<Debt, Status> statusColumn;
    @FXML
    private TableColumn<Debt, Void> editColumn;
    @FXML
    private TextField dateField;
    @FXML
    private TextField amountField;
    private IServiceDebt serviceDebt;
    @FXML
    private TableView<Debt> debtTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.serviceDebt = factory.getServiceDebt();
        filterComboBox.setValue(GlobalFilter.getCurrentComboBoxFilter());
        loadDebts();
        configureEditColumn();
    }

    private void loadDebts() {
        debtTable(idColumn, amountTotalColumn, amountSentColumn, amountMissingColumn, statusColumn);

        List<Debt> debts = switch (GlobalFilter.getCurrentComboBoxFilter()) {
            case "unpaid debts" -> serviceDebt.fetchAllUnpaidDebts();
            case "debt demands" -> serviceDebt.fetchAllByStatus(Status.PENDING);
            default -> serviceDebt.show();
        };

        ObservableList<Debt> observableDebts = FXCollections.observableArrayList(debts);
        debtTableView.setItems(observableDebts);
    }

    private void configureEditColumn() {
        editColumn.setCellFactory(_ -> new TableCell<>() {
            private final Button acceptButton = new Button("✔");
            private final Button cancelButton = new Button("✘");
            private final HBox buttonBox = new HBox(10, acceptButton, cancelButton);

            {
                acceptButton.setOnAction(_ -> {
                    Debt debt = getTableView().getItems().get(getIndex());
                    if (debt.getStatus() == Status.PENDING) {
                        serviceDebt.editStatus(debt, Status.ACCEPTED);
                        redirectTo("/org/example/demo/debtList.fxml");
                    }
                });
                cancelButton.setOnAction(_ -> {
                    Debt debt = getTableView().getItems().get(getIndex());
                    if (debt.getStatus() == Status.PENDING) {
                        serviceDebt.editStatus(debt, Status.CANCELLED);
                        redirectTo("/org/example/demo/debtList.fxml");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableView().getItems().get(getIndex()).getStatus() != Status.PENDING) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }


    @Override
    public void displayErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
        dateField.clear();
        amountField.clear();
    }

    @FXML
    private void filter() {
        String filterValue = filterComboBox.getValue();
        GlobalFilter.setCurrentComboBoxFilter(filterValue);
        redirectTo("/org/example/demo/debtList.fxml");
    }
}