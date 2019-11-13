package com.hoster.app.controllers;

import com.hoster.app.models.Customer;
import com.hoster.app.models.CustomerModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController<listCustomer> implements Initializable {
    private ObservableList<Customer> listCustomer = FXCollections.observableArrayList();;
    private CustomerModel customerModel;

    @FXML private TableView<Customer> dataTableCustomer;
    @FXML private TableColumn<Customer, Integer> id;
    @FXML private TableColumn<Customer, String> namaDepan;
    @FXML private TableColumn<Customer, String> namaBelakang;
    @FXML private TableColumn<Customer, String> noTelp;
    @FXML private TableColumn<Customer, String> email;
    @FXML private TableColumn<Customer, String> noKTP;
    @FXML private TableColumn<Customer, String> alamat;
    @FXML private TableColumn<Customer, String> kota;
    @FXML private TableColumn<Customer, String> negara;
    @FXML private TableColumn<Customer, String> jk;
    @FXML private TextField queryCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerModel = new CustomerModel();
        try {
            updateTable(customerModel.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cariCustomer(ActionEvent actionEvent) {
        String query = queryCustomer.getText();
        if (query.isEmpty()) {
            try {
                updateTable(customerModel.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            updateTable(customerModel.find(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTable(ResultSet rs) throws SQLException {
        listCustomer.removeAll();
        dataTableCustomer.getItems().clear();
        while (rs.next()) {
            listCustomer.add(
                    new Customer(
                            rs.getInt("id"),
                            rs.getString("no_ktp"),
                            rs.getString("nama_depan"),
                            rs.getString("nama_belakang"),
                            rs.getString("jenis_kelamin"),
                            rs.getString("email"),
                            rs.getString("telepon"),
                            rs.getString("alamat"),
                            rs.getString("kota"),
                            rs.getString("negara")
                    )
            );
        }
        dataTableCustomer.setItems(listCustomer);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaDepan.setCellValueFactory(new PropertyValueFactory<>("namaDepan"));
        namaBelakang.setCellValueFactory(new PropertyValueFactory<>("namaBelakang"));
        noTelp.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        noKTP.setCellValueFactory(new PropertyValueFactory<>("noKtp"));
        alamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        kota.setCellValueFactory(new PropertyValueFactory<>("kota"));
        negara.setCellValueFactory(new PropertyValueFactory<>("negara"));
        jk.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
    }
}
