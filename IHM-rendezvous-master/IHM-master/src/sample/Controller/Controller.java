package sample.Controller;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.java.random;
import sample.util.*;
import sample.util.EnvoyerEmail;

public class Controller {
    private Stage stage;
    private Parent root;
    private Scene scene;
    private static String CoodeEmail;
    private static String Email;
    Connection con ;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignin,btnConsultation,btnSignup;
    @FXML
    private Label showPassword;
    @FXML
    private ImageView hidepassword;
    @FXML
    private ImageView showpassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label ErrorConnection;
    @FXML
    private Label btnForgot;
    @FXML
    private TextField Code;
    @FXML
    private Label labela;
    @FXML
    PasswordField Confirmationmdp,mdp;
    @FXML
    Label resultatdemodification;

    public static String getEmail() {
        return Email;
    }

    public void handleButtonSignup(MouseEvent mouseEvent) throws IOException {
             root = FXMLLoader.load(getClass().getResource("/sample/Fxml/signup.fxml"));
             stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
             scene =new Scene(root);
             stage.setScene(scene);
             stage.show();
    }




    public void handleButtonConsultation(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/Fxml/Consultation.fxml"));
        stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void handleShowPassword(MouseEvent mouseEvent) {
        showPassword.setVisible(true);
        showPassword.setText(txtPassword.getText());
        txtPassword.setPromptText("");
        txtPassword.setText("");
        hidepassword.setVisible(false);
        showpassword.setVisible(true);

    }


    public void handleHidePassword(MouseEvent mouseEvent) {

        txtPassword.setText(showPassword.getText());
        txtPassword.positionCaret(txtPassword.getText().length());
        showPassword.setVisible(false);
        if(txtPassword.getText()=="")
            txtPassword.setPromptText("password");
        txtPassword.setVisible(true);
        hidepassword.setVisible(true);
        showpassword.setVisible(false);
    }

    public void Entred(MouseEvent mouseEvent) {
            btnForgot.setCursor(Cursor.HAND);
            hidepassword.setCursor(Cursor.HAND);
            showpassword.setCursor(Cursor.HAND);
            btnSignin.setCursor(Cursor.HAND);
            btnConsultation.setCursor(Cursor.HAND);
            btnSignup.setCursor(Cursor.HAND);
    }
    public void initialize(URL url, ResourceBundle rb) {
        if (this.con == null) {
            this.ErrorConnection.setTextFill(Color.TOMATO);
            this.ErrorConnection.setText("Server Error : Check");
        } else {
            this.ErrorConnection.setTextFill(Color.GREEN);
            this.ErrorConnection.setText("Server is up : Good to go");
        }

    }
    public Controller() {
        this.con = ConnectionUtil.conDB();
    }

    private String logIn() {
        String email = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        String sql = "SELECT * FROM admins Where email = ? and password = ?";

        try {
            this.preparedStatement = this.con.prepareStatement(sql);
            this.preparedStatement.setString(1, email);
            this.preparedStatement.setString(2, password);
            this.resultSet = this.preparedStatement.executeQuery();
            if (!this.resultSet.next()) {
                this.ErrorConnection.setTextFill(Color.TOMATO);
                this.ErrorConnection.setText("Enter Correct Email/Password");
                System.err.println("Wrong Logins --///");
                return "Error";
            } else {
                this.ErrorConnection.setTextFill(Color.GREEN);
                this.ErrorConnection.setText("Login Successful..Redirecting..");
                System.out.println("Successfull Login");
                return "Success";
            }
        } catch (SQLException var5) {
            System.err.println(var5.getMessage());
            return "Exception";
        }
    }
    @FXML
    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == this.btnSignin && this.logIn().equals("Success")) {


            }
        }

    public String handleLostPassword(MouseEvent mouseEvent)  {
        if(txtUsername.getText()!=""){
            String email = this.txtUsername.getText();
            String sql = "SELECT * FROM admins Where email = ?";
            try {
                this.preparedStatement = this.con.prepareStatement(sql);
                this.preparedStatement.setString(1, email);
                this.resultSet = this.preparedStatement.executeQuery();
                if (!this.resultSet.next() && mouseEvent.getSource()==btnForgot) {
                    this.ErrorConnection.setTextFill(Color.TOMATO);
                    this.ErrorConnection.setText("Entre Correctement Email");
                    return "Error";
                }else {
                   this.Email=txtUsername.getText();
                    root = FXMLLoader.load(getClass().getResource("/sample/Fxml/ForgotPassword.fxml"));
                    stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                    scene =new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    EnvoyerEmail emaile = new EnvoyerEmail();
                    random Random = new random();
                    this.CoodeEmail = Random.rndom();
                    emaile.envoyer(this.Email,this.CoodeEmail);
                }
            }catch (SQLException | IOException var5){
                System.err.println(var5.getMessage());
                return "Exception";
            }

        return null;
    }
        else return "Error";
    }
    public void confirmCode(MouseEvent mouseEvent) throws IOException {
        if(this.Code.getText().equals(this.CoodeEmail)){
            root = FXMLLoader.load(getClass().getResource("/sample/Fxml/ChangeMotDePasse.fxml"));
            stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            scene =new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            labela.setTextFill(Color.TOMATO);
            labela.setText("Try again");
            Code.setText("");
        }
    }


    public void signinByEntreKey(KeyEvent keyEvent) {
        if( keyEvent.getCode()== KeyCode.ENTER){
            logIn();
        }

    }


    public void cancel(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/Fxml/signin.fxml"));
        stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void fillSpec(MouseEvent mouseEvent) {

    }

    public void fillCity(MouseEvent mouseEvent) {
    }

    public void back(ActionEvent actionEvent) {
    }
}

