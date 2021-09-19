package librarianapp.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.* ;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import librarianapp.* ;
import librarianapp.resources.DataBaseController;
import librarianapp.resources.Encrypter;


public class SignInPageController implements Initializable
{
    public AnchorPane anchorPane;
    public VBox mainVbox, vbox_1, vbox_2 ;
    public Label mainHeader, subHeader, usernameLabel, passwordLabel ;
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
            this.dataBaseController = new DataBaseController() ;

            anchorPane.setStyle("-fx-background-color: #122333") ;
            mainHeader.setId("header_style") ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in SignInPageController.initialize() -> ") ;
            e.printStackTrace() ;

            messageBox.show(" Failed to connect to the database server. ", "ERROR") ;
            System.exit(0) ;
        }
    }


    public void keyPressedSignIn(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)    signIn(event) ;
    }


    public void buttonPressedSignIn(ActionEvent event)
    {
        signIn(event) ;
    }


    private void signIn(Event e)
    {
        String salt, encryptedPassword ;

        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
        {
            messageBox.show("Please enter your username and password.", "ERROR") ;
        }
        else
        {
            salt = dataBaseController.getSalt("LIBRARIAN", usernameField.getText()) ;
            encryptedPassword = encrypter.encrypt(passwordField.getText(), salt) ;

            Main.userAccount = dataBaseController.signInToAcct("LIBRARIAN", usernameField.getText(), encryptedPassword) ;

            if(Main.userAccount == null)
                messageBox.show("Incorrect username or password", "Sign In Failed");
            else
                loadHomePage(e) ;
        }
    }


    private void loadHomePage(Event event)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeScreen_FXML.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/librarianapp/resources/css/HomeScreenStyleSheet.css") ;

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage stage = new Stage() ;
            stage.setTitle("L I B R A R I A N  A P P L I C A T I O N") ;
            stage.setResizable(true);
            stage.setScene(scene) ;

            stage.show() ;
            window.close() ;
        }
        catch(Exception e) {
            System.out.println("\nExceptions in SignInPageController.loadHomePage(Event event) -> " + e) ;
            e.printStackTrace() ;
        }
    }
}