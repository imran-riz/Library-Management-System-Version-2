package memberapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.* ;
import javafx.stage.Stage;
import memberapp.model.Account;
import memberapp.view.MessageBox;

import java.util.Objects;

public class Main extends Application
{
    public static Account userAccount ;

    private final MessageBox messageBox = new MessageBox() ;

    public static void main(String[] args)
    {
        launch(args) ;
    }


    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/memberapp/view/SignInPage_FXML.fxml"))) ;

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/memberapp/resources/css/LoginPageStyleSheet.css");

            primaryStage.setTitle("M E M B E R   A P P L I C A T I O N  -  S I G N  I N");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace() ;

            messageBox.show("Oops! Something went wrong.", "ERROR") ;

            System.exit(0) ;
        }
    }
}