package com.hoster.app.controllers;

import com.hoster.app.models.Karyawan;
import com.hoster.app.models.KaryawanModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class KaryawanController implements Initializable {
    private ObservableList<Karyawan> listKaryawan = FXCollections.observableArrayList();;
    private KaryawanModel karyawanModel;
    @FXML private TableView<Karyawan> dataTableKaryawan;
    @FXML private TableColumn<Karyawan, Integer> id;
    @FXML private TableColumn<Karyawan, String> namaDepan;
    @FXML private TableColumn<Karyawan, String> namaBelakang;
    @FXML private TableColumn<Karyawan, String> noTelp;
    @FXML private TableColumn<Karyawan, String> email;
    @FXML private TableColumn<Karyawan, String> noKTP;
    @FXML private TableColumn<Karyawan, String> alamat;
    @FXML private TableColumn<Karyawan, String> kota;
    @FXML private TableColumn<Karyawan, String> tglRekrut;
    @FXML private TableColumn<Karyawan, String> jk;
    @FXML private TextField queryNamaKaryawan;

    public KaryawanController() {
        karyawanModel = new KaryawanModel();
    }

    @FXML
    protected void cariKaryawan(ActionEvent event) {
        String query = queryNamaKaryawan.getText();
        if (query.isEmpty()) {
            try {
                updateTable(karyawanModel.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            updateTable(karyawanModel.find(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateTable(karyawanModel.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTable(ResultSet rs) throws SQLException {
        listKaryawan.removeAll();
        dataTableKaryawan.getItems().clear();
        while (rs.next()) {
            listKaryawan.add(
                new Karyawan(
                    rs.getInt("id"),
                    rs.getString("no_ktp"),
                    rs.getString("nama_depan"),
                    rs.getString("nama_belakang"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("email"),
                    rs.getString("telepon"),
                    rs.getString("alamat"),
                    rs.getString("kota"),
                    rs.getString("tanggalRekrut")
                )
            );
        }
        dataTableKaryawan.setItems(listKaryawan);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaDepan.setCellValueFactory(new PropertyValueFactory<>("namaDepan"));
        namaBelakang.setCellValueFactory(new PropertyValueFactory<>("namaBelakang"));
        noTelp.setCellValueFactory(new PropertyValueFactory<>("telepon"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        noKTP.setCellValueFactory(new PropertyValueFactory<>("noKtp"));
        alamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        kota.setCellValueFactory(new PropertyValueFactory<>("kota"));
        tglRekrut.setCellValueFactory(new PropertyValueFactory<>("tanggalRekrut"));
        jk.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
    }

}
