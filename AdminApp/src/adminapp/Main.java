package adminapp;

import adminapp.model.Account;
import adminapp.view.MessageBox;
import javafx.application.Application ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application
{
    public static Account userAccount ;

    private MessageBox messageBox = new MessageBox() ;

    public static void main(String[] args)
    {
	    Application.launch(args) ;
    }

    @Override
    public void start(Stage stage)
    {
        stage = new Stage() ;
        stage.setResizable(false) ;
        stage.setTitle("A D M I N   S I G N   I N") ;

        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/adminapp/view/SignInPage_FXML.fxml"))) ;

            Scene scene = new Scene(root) ;
            scene.getStylesheets().add("/adminapp/resources/css/LoginPageStyleSheet.css") ;

            stage.setScene(scene) ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in Main.start() -> ") ;
            e.printStackTrace() ;

            messageBox.show("Oops! Something went wrong.", "ERROR") ;

            System.exit(0) ;
        }

        stage.show() ;
    }
}