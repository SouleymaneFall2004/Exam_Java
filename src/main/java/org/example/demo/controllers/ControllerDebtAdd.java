package org.example.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.controllers.utils.Factory;
import org.example.demo.controllers.utils.Session;
import org.example.demo.core.implementations.ControllersImpl;
import org.example.demo.data.entities.*;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.data.entities.enums.Status;
import org.example.demo.services.interfaces.IServiceArticle;
import org.example.demo.services.interfaces.IServiceClient;
import org.example.demo.services.interfaces.IServiceDebt;
import org.example.demo.services.interfaces.IServiceDetail;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerDebtAdd extends ControllersImpl implements Initializable {
    private final Factory factory = new Factory();
    @FXML
    private Label messageLabel;
    private IServiceClient serviceClient;
    private IServiceDebt serviceDebt;
    private IServiceArticle serviceArticle;
    private IServiceDetail serviceDetail;
    @FXML
    private VBox debtItemsContainer;
    @FXML
    private ComboBox<String> itemComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> clientComboBox;
    @Getter
    @Setter
    private Debt debt = new Debt();
    @FXML
    private Label totalLabel;
    private double total = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.serviceArticle = factory.getServiceArticle();
        this.serviceClient = factory.getServiceClient();
        this.serviceDetail = factory.getServiceDetail();
        this.serviceDebt = factory.getServiceDebt();

        loadArticleLabels();
        loadClientLabels();

        Account account = new Account();
        account.setRole(Role.SHOPKEEPER);
        Session.setAccount(account);

    }

    private ObservableList<String> getClientLabels() {
        List<String> clientLabels = new ArrayList<>();
        serviceClient.show().forEach(client -> clientLabels.add(client.getSurname()));
        return FXCollections.observableArrayList(clientLabels);
    }

    private void loadClientLabels() {
        clientComboBox.setItems(getClientLabels());
        clientComboBox.setVisible(true);
        clientComboBox.setManaged(true);
    }

    private ObservableList<String> getArticleLabels() {
        List<String> articleLabels = new ArrayList<>();
        serviceArticle.fetchAllAvailableArticles().forEach(article -> articleLabels.add(article.getLabel()));
        return FXCollections.observableArrayList(articleLabels);
    }

    private void loadArticleLabels() {
        itemComboBox.setItems(getArticleLabels());
    }

    @Override
    public void displayErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
        quantityField.clear();
    }

    @FXML
    private void addRow() {
        HBox newRow = new HBox(10);
        ComboBox<String> newItemSelect = new ComboBox<>();
        newItemSelect.setItems(getArticleLabels());
        newItemSelect.setPromptText("Select an item");

        TextField newQuantityField = new TextField();
        newQuantityField.setPromptText("Quantity");

        newRow.getChildren().addAll(newItemSelect, newQuantityField);
        debtItemsContainer.getChildren().add(newRow);

        updateTotal();
    }

    @FXML
    private void deleteRow() {
        if (!debtItemsContainer.getChildren().isEmpty()) {
            debtItemsContainer.getChildren().removeLast();
            updateTotal();
        }
    }

    @FXML
    private void saveDebt() {
        try {
            for (var node : debtItemsContainer.getChildren()) {
                if (node instanceof HBox row) {
                    ComboBox<?> itemSelect = (ComboBox<?>) row.getChildren().get(0);
                    TextField quantityField = (TextField) row.getChildren().get(1);

                    String selectedItem = (String) itemSelect.getValue();
                    String quantityText = quantityField.getText();

                    if (selectedItem == null) {
                        displayErrorMessage("Select an item...");
                        return;
                    }
                    if (quantityText.isEmpty()) {
                        displayErrorMessage("Quantity cannot be empty");
                        return;
                    }

                    int quantity = Integer.parseInt(quantityText);
                    Article article = serviceArticle.fetchByLabel(selectedItem);

                    if (quantity > article.getStock()) {
                        displayErrorMessage("Quantity cannot be higher than stock");
                        return;
                    }

                    Detail detail = new Detail();
                    detail.setArticle(article);
                    detail.setStock(quantity);

                    debt.setAmountTotal(debt.getAmountTotal() + quantity * article.getPrice());

                    detail.setDebt(debt);
                    debt.addDetail(detail);

                    serviceArticle.editArticleStock(article, article.getStock() - quantity);
                }
            }

            if (Session.getAccount().getClient() == null) {
                String selectedClient = clientComboBox.getValue();
                if (selectedClient == null) {
                    displayErrorMessage("Please select a client...");
                    return;
                }

                Client client = serviceClient.fetchBySurname(selectedClient);

                debt.setStatus(Status.ACCEPTED);
                debt.setAmountMissing(debt.getAmountTotal());
                debt.setClient(client);
                client.addDebt(debt);
                serviceDebt.save(debt);
                debt.getDetailList().forEach(detail -> {
                    detail.setId(debt.getId());
                    serviceDetail.save(detail);
                });
                redirectTo("/org/example/demo/debtList.fxml");
            } else {
                if (debt.getAmountTotal() != 0) {
                    debt.setStatus(Status.PENDING);
                    debt.setAmountMissing(debt.getAmountTotal());
                    debt.setClient(Session.getAccount().getClient());
                    Session.getAccount().getClient().addDebt(debt);
                    serviceDebt.save(debt);
                    debt.getDetailList().forEach(detail -> {
                        detail.setId(debt.getId());
                        serviceDetail.save(detail);
                    });
                }
                redirectTo("/org/example/demo/client/debtListClient.fxml");
            }
        } catch (NumberFormatException e) {
            displayErrorMessage("Quantity must be an integer...");
        }
    }

    private void updateTotal() {
        total = 0.0;

        for (var node : debtItemsContainer.getChildren()) {
            if (node instanceof HBox row) {
                ComboBox<?> itemSelect = (ComboBox<?>) row.getChildren().get(0);
                TextField quantityField = (TextField) row.getChildren().get(1);

                String selectedItem = (String) itemSelect.getValue();
                String quantityText = quantityField.getText();

                if (selectedItem != null && !quantityText.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityText);
                        Article article = serviceArticle.fetchByLabel(selectedItem);

                        total += quantity * article.getPrice();
                    } catch (NumberFormatException e) {
                        return;
                    }
                }
            }
        }

        totalLabel.setText(String.format("%.2f", total));
    }
}