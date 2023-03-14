package com.example.crud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConnexionController {

    @FXML
    private Button btnCon;

    @FXML
    private Label lblEtat;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private PasswordField txtPasser;

    @FXML
    private TextField txtUtilisateur;
    private IUser userDao = new UserImpl();
    @FXML
    void login(ActionEvent event) {
        String userName = txtUtilisateur.getText();
        String password = txtPasser.getText();
        if(userName.equals("") || password.equals("")){
            lblEtat.setText("Tous les champs sont obligatoire");
        }else {
            User user = userDao.seConnecter(userName,password);
            if(user == null){
                lblEtat.setText("Mot de passe et/ou nom d'utilisateur incorrecte");
            }else{
                try{
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/Fxml/crud.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Gestion des etudiants");
                    stage.show();
                    Stage stage1 = (Stage) btnCon.getScene().getWindow();
                    stage1.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }


        }
    }

}
