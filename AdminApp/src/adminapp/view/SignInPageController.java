package adminapp.view;

import adminapp.resources.DataBaseController;
import adminapp.resources.Encrypter;
import adminapp.Main;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class SignInPageController extends Main implements Initializable
{
    public AnchorPane anchorPane;
    public VBox mainVbox, vbox_1, vbox_2 ;
    public Label mainHeader, usernameLabel, passwordLabel ;
    public HBox hbox_1, hbox_2 ;
    public TextField usernameField ;
    public PasswordField passwordField ;
    public Button signInBtn ;

    private DataBaseController dataBaseController ;
    private final Encrypter encrypter = new Encrypter() ;
    private final MessageBox messageBox = new MessageBox() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController();

            mainHeader.setId("header_style");
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in SignInPageController.initialize() -> ") ;
            e.printStackTrace() ;

            messageBox.show("Failed to connect to the database server.", "ERROR") ;

            System.exit(0) ;
        }
    }


    public void keyPressedSignIn(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ENTER)
            signIn(keyEvent) ;
    }


    public void buttonPressedSignIn(ActionEvent actionEvent)
    {
        signIn(actionEvent) ;
    }


    private void signIn(Event event)
    {
        String salt, encryptedPassword ;

        if(!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty())
        {
            salt = dataBaseController.getSalt("ADMIN", usernameField.getText()) ;

            encryptedPassword = encrypter.encrypt(passwordField.getText(), salt) ;

            Main.userAccount = dataBaseController.signInToAcct("ADMIN", usernameField.getText(), encryptedPassword) ;

            if(Main.userAccount != null)
                loadHomePage(event) ;
            else
                messageBox.show("Invalid username or password!", "SIGN IN FAIL") ;
        }
        else
        {
            messageBox.show("Please enter your username and password.", "SIGN IN FAIL") ;
        }
    }


    private void loadHomePage(Event event)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeView_FXML.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage stage = new Stage() ;
            stage.setTitle("A D M I N   A P P L I C A T I O N") ;
            stage.setResizable(true);
            stage.setScene(scene) ;

            stage.show() ;
            window.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nExceptions in SignInPageController.loadHomePage(Event event) -> " + e) ;
            e.printStackTrace() ;
        }
    }
}