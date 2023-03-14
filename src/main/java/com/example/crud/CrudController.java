package com.example.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class CrudController implements Initializable {

    @FXML
    private AnchorPane crudPane;

    @FXML
    private TableColumn<Etudiant, LocalDate> dateNaissance;

    @FXML
    private TableColumn<Etudiant, Integer> id;

    @FXML
    private TableColumn<Etudiant, String> matricule;

    @FXML
    private TableColumn<Etudiant, Float> moyenne;

    @FXML
    private TableColumn<Etudiant, String> nom;

    @FXML
    private Button btnView;
    @FXML
    private Button btnAjout;

    @FXML
    private TableColumn<Etudiant, String> prenom;

    @FXML
    private TableView<Etudiant> table;

    @FXML
    private DatePicker txtDateNaissance;

    @FXML
    private TextField txtMoyenne;
    @FXML
    private Button btnlogout;

    @FXML
    private TextField txtNom;
    @FXML
    private ComboBox comboClasse;
    @FXML
    private TextField txtPrenom;
    private DB db = new DB();
    public String generate(String nom, String prenom){
        String mat;
        int size = nom.length() + prenom.length();
        char n = nom.charAt(0);
        char p = prenom.charAt(0);
        mat = "ET@" + n + p + size;
        return mat;
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/connexion.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();
        Stage stage1 = (Stage) btnlogout.getScene().getWindow();
        stage1.close();
    }
    @FXML
    void add(ActionEvent event) {
        String sql = "INSERT INTO etudiant(matricule, nom, prenom, dateNaissance,  moyenne) VALUES (?,?,?,?,?)";
        try{
            db.initPrepar(sql);

            db.getPstm().setString(1,generate(txtNom.getText(), txtPrenom.getText()));
            db.getPstm().setString(2,txtNom.getText());
            db.getPstm().setString(3,txtPrenom.getText());
            db.getPstm().setString(4,txtDateNaissance.getValue().toString());
            db.getPstm().setString(5,txtMoyenne.getText());
            db.executeMaj();
            db.closeConnection();
            view();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Notification!");
            alert.setTitle("Success");
            alert.setContentText("Etudiant ajouté");
            alert.showAndWait();
            vider();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        String sql = "DELETE FROM etudiant WHERE id=?";
        try{
            db.initPrepar(sql);

            db.getPstm().setString(1, String.valueOf(getID));
            db.executeMaj();
            db.closeConnection();
            view();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Notification!");
            alert.setTitle("Success");
            alert.setContentText("Etudiant supprimé");
            alert.showAndWait();
            vider();

        }catch (Exception e){
            e.printStackTrace();
        }
        vider();
        view();
        btnAjout.setDisable(false);
    }

    @FXML
    void update(ActionEvent event) {
        String sql = "UPDATE etudiant SET matricule= ?, nom = ? , prenom = ? , moyenne = ? WHERE id=?";
        try{
            db.initPrepar(sql);

            db.getPstm().setString(1,generate(txtNom.getText(), txtPrenom.getText()));
            db.getPstm().setString(2,txtNom.getText());
            db.getPstm().setString(3,txtPrenom.getText());
            //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // db.getPstm().setString(4,sdf.format(txtDateNaissance.getValue()));
            db.getPstm().setString(4,txtMoyenne.getText());
            db.getPstm().setString(5, String.valueOf(getID));
            db.executeMaj();
            db.closeConnection();
            view();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Notification!");
            alert.setTitle("Success");
            alert.setContentText("Etudiant mis à jour");
            alert.showAndWait();
            vider();

        }catch (Exception e){
            e.printStackTrace();
        }
        vider();
        view();
        btnAjout.setDisable(false);
    }
    private int getID;
    @FXML
    void getData(MouseEvent event) {

    Etudiant et = table.getSelectionModel().getSelectedItem();
    getID = et.getId();
    txtNom.setText(et.getNom());
    txtPrenom.setText(et.getPrenom());
    txtDateNaissance.setValue(et.getDateNaissance());
    txtMoyenne.setText(et.getMoyenne().toString());
    btnAjout.setDisable(true);
    }

    @FXML
    void choix(ActionEvent event) {

    }


    @FXML
    void vider() {
        txtMoyenne.setText("");
        txtNom.setText("");
        txtPrenom.setText("");
        txtDateNaissance.setValue(null);
        btnAjout.setDisable(false);
    }

    public ObservableList<Etudiant> data = FXCollections.observableArrayList();
    @FXML
    void view() {
        table.getItems().clear();
        try{
            Connection con = DB.getConnection();
            String sql = "SELECT * FROM etudiant ORDER BY nom ASC";
            PreparedStatement stat = con.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();
            while(rs.next()){
                data.add(new Etudiant(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDate(5).toLocalDate(),rs.getFloat(6)));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<Etudiant, Integer>("id"));
        matricule.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("matricule"));
        nom.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Etudiant, String>("prenom"));
        dateNaissance.setCellValueFactory(new PropertyValueFactory<Etudiant, LocalDate>("dateNaissance"));
        moyenne.setCellValueFactory(new PropertyValueFactory<Etudiant, Float>("moyenne"));
        table.setItems(data);
        btnView.setText("Actualiser");
        vider();
        btnAjout.setDisable(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
