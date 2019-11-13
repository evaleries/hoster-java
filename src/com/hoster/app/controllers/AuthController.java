package com.hoster.app.controllers;

import com.hoster.app.models.AuthModel;
import com.hoster.app.helpers.AlertHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    private AuthModel auth;

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button loginBtn;

    @FXML
    protected void doLogin(ActionEvent evt) {
        Window owner = loginBtn.getScene().getWindow();
        String username = usernameText.getText();
        String password = passwordText.getText();
        if (username.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error", "Username kosong");
            return;
        }
        if (password.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error", "Password kosong");
            return;
        }

        boolean checkLogin = auth.authenticate(username, password);
        if (checkLogin) {
//            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Berhasil", "Selamat datang " + username);
            changePage(evt);
            return;
        }

        AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Gagal", "username atau password salah");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        auth = new AuthModel();
    }

    private void changePage(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../views/home/main_new.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert !(root == null);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hoster - Hotel Management");
        stage.setResizable(false);
        stage.show();
    }
}
