package com.hoster.app.controllers;

import com.hoster.app.models.Kamar;
import com.hoster.app.models.Reservasi;
import com.hoster.app.models.ReservasiModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataReservasiController implements Initializable {
    private ObservableList<Reservasi> listReservasi = FXCollections.observableArrayList();
    private ReservasiModel reservasiModel;

    public TableView<Reservasi> tableReservasi;
    public TableColumn<Reservasi, Integer> id;
    public TableColumn<Reservasi, String> namaCustomer;
    public TableColumn<Reservasi, String> namaKamar;
    public TableColumn<Reservasi, String> jenisKamar;
    public TableColumn<Reservasi, String> bookingDate;
    public TableColumn<Reservasi, String> checkInDate;
    public TableColumn<Reservasi, String> checkOutDate;
    public TableColumn<Reservasi, String> createdAt;
    public TextField queryKamar;
    public DatePicker queryTanggal;
    public Button btnCariKamarTgl;
    public ChoiceBox statusReservasi;
    public Button btnCariStatus;
    public Button btnCariKamarQuery;

    public DataReservasiController() {
        reservasiModel = new ReservasiModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateTable(reservasiModel.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cariKamar(ActionEvent actionEvent) {
    }

    public void cariKamarKosong(ActionEvent actionEvent) {
    }

    public void checkReservasi(InputMethodEvent inputMethodEvent) {
    }

    public void cariStatus(ActionEvent actionEvent) {
    }

    public void updateTable(ResultSet rs) throws SQLException {
        tableReservasi.getItems().clear();
        while(rs.next()) {
            listReservasi.add(
                    new Reservasi(
                            rs.getInt("t.id"),
                            rs.getString("nama_customer"),
                            rs.getString("nama_kamar"),
                            rs.getString("nama_jenis_kamar"),
                            rs.getString("t.book_date"),
                            rs.getString("t.check_in"),
                            rs.getString("t.check_out"),
                            rs.getString("t.created_at")
                    )
            );
        }
        tableReservasi.setItems(listReservasi);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        namaCustomer.setCellValueFactory(new PropertyValueFactory<>("namaCustomer"));
        namaKamar.setCellValueFactory(new PropertyValueFactory<>("namaKamar"));
        jenisKamar.setCellValueFactory(new PropertyValueFactory<>("jenisKamar"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        checkInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    }
}
