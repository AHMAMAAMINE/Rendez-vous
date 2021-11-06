package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sample.enums.enumTypedemedecin;
import sample.enums.enumVille;
import sample.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerSignUp extends  javax.swing.JFrame{
    @FXML
    private TextField txtEmail,txtLastname,txtFirstname;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox typeMedecin,City;
    @FXML
    private Label ErrorCon;
    Connection con ;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void handleButtonSave(MouseEvent mouseEvent) {
        String firstname=txtFirstname.getText();
        String lastname=txtLastname.getText();
        String email=txtEmail.getText();
        String pasword=password.getText();

        String fullname=firstname+lastname;
        if(email!="" && lastname!="" && firstname!="" && pasword!="" && City.getValue()!=null &&  typeMedecin.getValue()!=null){
            String TypedeMedecin=typeMedecin.getValue().toString();
            String Ville =City.getValue().toString();
            existDejaDansLaBaseDeDonnes(email, fullname,TypedeMedecin,Ville,pasword);
        }
    }
    public ControllerSignUp() {
        this.con = ConnectionUtil.conDB();
    }
    private String existDejaDansLaBaseDeDonnes(String email,String fullname, String medecintype, String city, String password ) {
        String sql = "SELECT * FROM Doctors Where email = ? AND fullname = ?";
        try {
            this.preparedStatement = this.con.prepareStatement(sql);
            this.preparedStatement.setString(1, email);
            this.preparedStatement.setString(2, fullname);
            this.resultSet = this.preparedStatement.executeQuery();
            if(this.resultSet.next()){
                this.ErrorCon.setTextFill(Color.TOMATO);
                this.ErrorCon.setText("Ce compte exist deja");
            }
            else {
                String sqll="Insert Into Doctors (email,fullname,password,typedemedecin,Ville) values (?,?,?,?,?)";
                try {
                    this.preparedStatement=this.con.prepareStatement(sqll);
                    this.preparedStatement.setString(1, email);
                    this.preparedStatement.setString(2, fullname);
                    this.preparedStatement.setString(3, password);
                    this.preparedStatement.setString(4, medecintype);
                    this.preparedStatement.setString(5, city);
                    int valide = this.preparedStatement.executeUpdate();
                    if(valide ==1){
                        this.ErrorCon.setTextFill(Color.GREEN);
                        this.ErrorCon.setText("inscription bien faite");
                    }
                }catch (SQLException var5) {
                    System.err.println(var5.getMessage());
                    return "Exception";}
            }
        }catch (SQLException var5) {
            System.err.println(var5.getMessage());
            return "Exception";
        }

        return sql;
    }

    public void fill(MouseEvent mouseEvent) {
        typeMedecin.getItems().addAll(enumTypedemedecin.values());

    }

    public void fillVille(MouseEvent mouseEvent) {

        City.getItems().addAll(enumVille.values());
    }


//    public void look(KeyEvent keyEvent) {
//        String recherche="";
//        for(int i=0;i<typeMedecin.getItems().size();i++){
//            recherche+=keyEvent.getCode().toString();
//            if(typeMedecin.getItems().get(i).toString().toLowerCase().contains(recherche)){
//                typeMedecin.getSelectionModel().select(i);
//                typeMedecin.setVisible(true);
//            }
//        }
//    }
}
