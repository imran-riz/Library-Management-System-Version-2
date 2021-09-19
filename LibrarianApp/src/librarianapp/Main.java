package librarianapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Objects;

import librarianapp.model.* ;
import librarianapp.view.MessageBox;

import javax.naming.CommunicationException;


public class Main extends Application
{
    private final MessageBox messageBox = new MessageBox() ;

    public static Account userAccount ;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/librarianapp/view/SignInPage_FXML.fxml")));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/librarianapp/resources/css/LoginPageStyleSheet.css");

            primaryStage.setTitle("L I B R A R I A N   A P P L I C A T I O N  -  S I G N  I N");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show() ;
        }
        catch (Exception exception)
        {
            System.out.println("\nExceptions in Main.start() -> ") ;
            exception.printStackTrace() ;

            messageBox.show(" Oops! Something went wrong. Try again later. ", "ERROR") ;
            System.exit(0) ;
        }
    }

}
