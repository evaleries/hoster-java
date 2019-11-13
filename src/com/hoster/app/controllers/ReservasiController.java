package com.hoster.app.controllers;

import com.hoster.app.helpers.AlertHelper;
import com.hoster.app.models.Customer;
import com.hoster.app.models.CustomerModel;
import com.hoster.app.models.ReservasiModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReservasiController implements Initializable {
    private CustomerModel customerModel;
    private ReservasiModel reservasiModel;
    private ArrayList<String> arrayListCheckoutKamar = new ArrayList<>();
    private ObservableList<String> listCheckoutKamar = FXCollections.observableArrayList();
    private ObservableList<String> listJenisKamar = FXCollections.observableArrayList();
    private ObservableList<String> listKamar = FXCollections.observableArrayList();

    public TextField id_customer;
    public TextField id_kamar;
    public TextField nama_depan;
    public TextField nama_belakang;
    public TextField noKtp;
    public TextField email;
    public TextArea alamat;
    public TextField noTelepon;
    public TextField kota;
    public TextField negara;
    public Button btnSimpanCustomer;
    public Button btnResetData;
    public Button btnReservasi;
    public ChoiceBox jenisKamar;
    public ChoiceBox namaKamar;
    public TextField lamaInap;
    public TextField harga;
    public Label totalHarga;
    public DatePicker tanggalInap;
    public Button hitungBtn;
    // Checkout
    public Button btnCheckout;
    public ChoiceBox detailCheckout;
    public TextField checkoutNamaKamar;

    public ReservasiController() {
        customerModel = new CustomerModel();
        reservasiModel = new ReservasiModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet jKamar = reservasiModel.getListJenisKamar();
        try {
            while (jKamar.next()) {
                listJenisKamar.add(jKamar.getString("nama"));
            }
            this.jenisKamar.setItems(listJenisKamar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tanggalInap.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dt = DateTimeFormatter.ofPattern(pattern);
            {
                tanggalInap.setPromptText(pattern.toLowerCase());
            }
            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dt.format(localDate);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String s) {
                if (s != null && !s.isEmpty()) {
                    return LocalDate.parse(s, dt);
                } else {
                    return null;
                }
            }
        });
        jenisKamar.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            updateChoiceKamar(listJenisKamar.get(t1.intValue()));
        });
        namaKamar.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            if (listKamar.isEmpty()) return;
            updateHargaReservasi(listKamar.get(t1.intValue()));
        });
    }

    public void updateChoiceKamar(String jenis) {
        namaKamar.getItems().clear();
        if (! listKamar.isEmpty()) listKamar.removeAll();
        Window owner = btnReservasi.getScene().getWindow();
        if (tanggalInap.getValue().toString().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Data kurang!", "Tanggal reservasi harus diisi.");
            return;
        }
        if (tanggalInap.getValue().isBefore(LocalDate.now())) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Data Tidak Valid!", "Tanggal reservasi tidak valid.");
            return;
        }
        System.out.println(tanggalInap.getValue().toString());
        ResultSet listKamarByJenis = reservasiModel.getKamarKosongByJenis(jenis, tanggalInap.getValue().toString(), Integer.parseInt(lamaInap.getText()));
        try {
            while (listKamarByJenis.next()) {
                listKamar.add(listKamarByJenis.getString("k.nama"));
            }
            namaKamar.setItems(listKamar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHargaReservasi(String namaKamar) {
        int lama = Integer.parseInt(lamaInap.getText());
        if (lama <= 0) return;
        ResultSet rs = reservasiModel.getHargaByKamar(namaKamar);
        int hargaKamar = 0;
        try {
            while (rs.next()) {
                hargaKamar = rs.getInt("harga");
                id_kamar.setText(String.valueOf(rs.getInt("id")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println("SelectedHargaKamar: " + hargaKamar);
        harga.setText(String.valueOf(hargaKamar));
        totalHarga.setText(String.valueOf(hargaKamar * lama));
    }

    public void kalkulasiHarga(ActionEvent actionEvent) {
        int h = Integer.parseInt(harga.getText());
        int l = Integer.parseInt(lamaInap.getText());
//        System.out.println("Harga: " + h);
//        System.out.println("Lama: " + l);
//        System.out.println("Total: " + String.valueOf(h*l));
        totalHarga.setText("Rp " + String.valueOf(h * l) + ",-");
    }

    public void doReservasi(ActionEvent actionEvent) {
        String idKamar = id_kamar.getText();
        String idCustomer = id_customer.getText();
        System.out.println(idKamar +"|"+ idCustomer);
        Window owner = btnReservasi.getScene().getWindow();
        if (idKamar.isEmpty() || idCustomer.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Gagal", "Data customer belum tersimpan atau kamar belum dipilih");
            return;
        }
        Optional<ButtonType> confirmReservation = AlertHelper.confirm(owner, "Apakah Anda yakin?", "Konfirmasi Reservasi atas nama " + nama_depan.getText() + " " + nama_belakang.getText() + "?");
        if (confirmReservation.get() != ButtonType.OK) return;
        Map<String, String> rsrvData = new HashMap<>();
        rsrvData.put("id_karyawan", "1");
        rsrvData.put("id_customer", idCustomer);
        rsrvData.put("id_kamar", idKamar);
        rsrvData.put("id_metode_pembayaran", "1");
        rsrvData.put("id_status_transaksi", "1");
        rsrvData.put("book_date", tanggalInap.getValue().toString());
        rsrvData.put("check_out", tanggalInap.getValue().plusDays(Long.parseLong(lamaInap.getText())).toString());
        rsrvData.put("check_in", tanggalInap.getValue().toString());
        boolean storeReservasi = reservasiModel.store(rsrvData);
        if (storeReservasi) {
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Berhasil", "Reservasi Berhasil!");
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Gagal", "Reservasi Gagal!");
        }
        noKtp.disableProperty().setValue(false);
    }

    public void resetForm(ActionEvent actionEvent) {
        Window owner = btnResetData.getScene().getWindow();
        Optional<ButtonType> confirmation = AlertHelper.confirm(owner, "Apakah anda yakin?", "Form Customer akan direset");
        if (confirmation.get() != ButtonType.OK) return;
        nama_depan.clear();
        nama_belakang.clear();
        noKtp.clear();
        email.clear();
        alamat.clear();
        noTelepon.clear();
        kota.clear();
        negara.clear();
    }

    public void storeCustomer() {
        String namaDepan    = nama_depan.getText();
        String namaBelakang = nama_belakang.getText();
        String noKTP        = noKtp.getText();
        String email        = this.email.getText();
        String alamat       = this.alamat.getText();
        String noTelp       = noTelepon.getText();
        String kota         = this.kota.getText();
        String negara       = this.negara.getText();
        boolean NotEmpty = ! namaDepan.isEmpty() && !namaBelakang.isEmpty() && !noKTP.isEmpty() && ! email.isEmpty() && ! noTelp.isEmpty() && ! alamat.isEmpty();
        Window owner = btnSimpanCustomer.getScene().getWindow();
        if (NotEmpty) {
            Map<String, String> customer = new Customer(1, noKTP, namaDepan, namaBelakang, "L", email, noTelp, alamat, kota, negara).toHashMap();
            boolean store = customerModel.store(customer);
            if (store) {
                noKtp.disableProperty().setValue(true);
//                btnSimpanCustomer.disableProperty().setValue(true);
//                btnReservasi.disableProperty().setValue(false);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Berhasil", "Customer berhasil disimpan! Lanjutkan ke Reservasi");
                try {
                    ResultSet rs = reservasiModel.getIdCustomerByNameKTP(namaDepan, noKTP);
                    while (rs.next()) {
                        id_customer.setText(String.valueOf(rs.getInt("id")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Gagal", "Customer gagal disimpan!");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Data Kosong", "Terdapat Data penting yang kosong.");
        }
    }

    public void doCheckout(ActionEvent actionEvent) throws SQLException {

//        if (find.getFetchSize() > 0) {
//            AlertHelper.confirm(owner, "Konfirmasi Checkout", "Apakah anda yakin checkout kamar " + kamar);
//        } else {
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Reservasi Kamar tidak ditemukan!");
//        }
    }

    public void checkoutGetKamar(ActionEvent actionEvent) throws SQLException {
        detailCheckout.getItems().clear();
        String kamar = checkoutNamaKamar.getText();
        Window owner = btnCheckout.getScene().getWindow();
        if (kamar.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Warning", "Data Kamar kosong!");
            return;
        }
        ResultSet rs = reservasiModel.find(kamar);
        while (rs.next()) {
            listCheckoutKamar.add(String.format("%s - %s", rs.getString("nama_kamar"), rs.getString("nama_customer")));
            arrayListCheckoutKamar.add(String.format("%s - %s", rs.getString("nama_kamar"), rs.getString("nama_customer")));
        }
        detailCheckout.setItems(listCheckoutKamar);
        Integer selectedKamar = detailCheckout.getSelectionModel().selectedIndexProperty().getValue();
        System.out.println(selectedKamar);
        System.out.println(arrayListCheckoutKamar.get(selectedKamar));
        arrayListCheckoutKamar.stream().forEach(System.out::println);
    }
}
