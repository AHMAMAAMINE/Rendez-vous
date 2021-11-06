package sample.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.util.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControllerSwitchPassword {
    @FXML
    PasswordField Confirmationmdp,mdp;
    @FXML
    Label resultatdemodification;
    Connection con ;
    PreparedStatement preparedStatement = null;
    Controller controller=new Controller();
    private Stage stage;
    private Parent root;
    private Scene scene;
    public ControllerSwitchPassword() {
        this.con = ConnectionUtil.conDB();
    }
    public String confiremCode(MouseEvent mouseEvent)  {
        if(Confirmationmdp.getText().equals(mdp.getText())){

            String emails =controller.getEmail() ;
            String pasword=mdp.getText();
            String sql = "UPDATE admins SET password=? where email=?";
            try {
                this.preparedStatement = this.con.prepareStatement(sql);
                this.preparedStatement.setString(2, emails);
                this.preparedStatement.setString(1, pasword);
                int a = this.preparedStatement.executeUpdate();
                if (a==1){
                    resultatdemodification.setTextFill(Color.GREEN);
                    resultatdemodification.setText("changement de mot de passe bien faite");
                    root = FXMLLoader.load(getClass().getResource("/sample/Fxml/signin.fxml"));
                    stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                    scene =new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }catch (SQLException | IOException var5){
                System.err.println(var5.getMessage());
                return "Exception";
            }
        }
        else{
            resultatdemodification.setTextFill(Color.TOMATO);
            resultatdemodification.setText("mot de passe incompatible avec ca confirmation");
        }
        return null;
    }

}
