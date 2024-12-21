package org.example.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.demo.controllers.utils.Factory;
import org.example.demo.controllers.utils.GlobalFilter;
import org.example.demo.core.implementations.ControllersImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.services.interfaces.IServiceAccount;
import org.example.demo.services.interfaces.IServiceClient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerClientList extends ControllersImpl implements Initializable {
    private final Factory factory = new Factory();
    private IServiceAccount serviceAccount;
    private IServiceClient serviceClient;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField researchField;
    @FXML
    private Label messageLabel;
    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableColumn<Client, Integer> idColumn;
    @FXML
    private TableColumn<Client, String> surnameColumn;
    @FXML
    private TableColumn<Client, String> phoneColumn;
    @FXML
    private TableColumn<Client, String> addressColumn;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private CheckBox accountCheckBox;
    @FXML
    private VBox accountFields;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.serviceAccount = factory.getServiceAccount();
        this.serviceClient = factory.getServiceClient();
        filterComboBox.setValue(GlobalFilter.getCurrentComboBoxFilter());
        researchField.setText(GlobalFilter.getCurrentStringFilter());
        loadClients();
    }

    @Override
    public void displayErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
        surnameField.clear();
        phoneField.clear();
        addressField.clear();
        researchField.clear();
    }

    @FXML
    private void toggleAccountFields() {
        boolean isChecked = accountCheckBox.isSelected();
        accountFields.setVisible(isChecked);
        accountFields.setManaged(isChecked);
    }

    private void loadClients() {
        if (idColumn.getCellValueFactory() == null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        }

        List<Client> clients = switch (GlobalFilter.getCurrentComboBoxFilter()) {
            case "with an account" -> serviceClient.fetchAllWithAccount();
            case "without an account" -> serviceClient.fetchAllWithoutAccount();
            default -> serviceClient.show();
        };

        if (!GlobalFilter.getCurrentStringFilter().isBlank()) {
            Client client = serviceClient.fetchByPhone(GlobalFilter.getCurrentStringFilter());
            if (client != null) {
                clients.clear();
                clients.add(client);
            }
        }

        ObservableList<Client> observableClients = FXCollections.observableArrayList(clients);
        clientTableView.setItems(observableClients);
    }

    @FXML
    private void filter() {
        String filterValue = filterComboBox.getValue();
        String researchValue = researchField.getText();
        GlobalFilter.setCurrentComboBoxFilter(filterValue);
        GlobalFilter.setCurrentStringFilter(researchValue);
        redirectTo("/org/example/demo/clientList.fxml");
    }

    @FXML
    private void saveClient() {
        String surname = surnameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (surname.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            displayErrorMessage("Please fill in all values");
            return;
        }

        if (accountCheckBox.isSelected()) {
            String login = loginField.getText();
            String password = passwordField.getText();
            if (login.isEmpty() || password.isEmpty()) {
                displayErrorMessage("Please fill in login and password");
                return;
            }
            Account account = new Account(login, password, Role.CLIENT);
            serviceAccount.save(account);
            Client client = new Client(surname, phone, address, account);
            serviceClient.save(client);
            serviceAccount.editAccountOfClient(account, client);
        } else {
            Client client = new Client(surname, phone, address);
            serviceClient.save(client);
        }
        redirectTo("/org/example/demo/clientList.fxml");
    }
}