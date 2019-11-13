package com.hoster.app.controllers;


import com.hoster.app.models.Kamar;
import com.hoster.app.models.KamarModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class KamarController implements Initializable {
    private ObservableList<Kamar> listKamar = FXCollections.observableArrayList();;
    private KamarModel kamarModel;

    @FXML private TableView<Kamar> dataTableKamar;
    @FXML private TableColumn<Kamar, Integer> id;
    @FXML private TableColumn<Kamar, String> nama;
    @FXML private TableColumn<Kamar, String> tipe;
    @FXML private TableColumn<Kamar, Integer> harga;
    @FXML private TableColumn<Kamar, String> keterangan;
    @FXML private TextField queryKamar;

    public KamarController() {
        kamarModel = new KamarModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateTable(kamarModel.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cariKamar() {
        String keyword = queryKamar.getText();
        if (keyword.isEmpty()) {
            try {
                updateTable(kamarModel.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            updateTable(kamarModel.find(keyword));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cariKamarKosong() {
        try {
            updateTable(kamarModel.findEmptyRoom("2019-11-12"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearTableMeta() {
        listKamar.removeAll();
        dataTableKamar.getItems().clear();
    }

    public void updateTable(ResultSet rs) throws SQLException {
        clearTableMeta();
        while(rs.next()) {
            listKamar.add(
              new Kamar(
                      rs.getInt("id"),
                      rs.getInt("harga"),
                      rs.getString("k.nama"),
                      rs.getString("jk.nama"),
                      rs.getString("keterangan")
              )
            );
        }
        dataTableKamar.setItems(listKamar);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        harga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        tipe.setCellValueFactory(new PropertyValueFactory<>("tipe"));
        keterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

    }
}
