package adminapp.view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import adminapp.Main ;


public class HomeViewController extends Main implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public GridPane gridPane ;
    public Button membersBtn, librariansBtn, booksBtn, editProfileBtn, signOutBtn ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        header.setText("WELCOME, " + Main.userAccount.getFirstName().toUpperCase()) ;

        root.setId("root_style") ;
        header.setId("main_header_style") ;
        membersBtn.setId("option_btn_style") ;
        librariansBtn.setId("option_btn_style") ;
        booksBtn.setId("option_btn_style") ;
        editProfileBtn.setId("option_btn_style") ;
    }


    public void loadMembersView()
    {
        try
        {
            AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/MemberAccounts_FXML.fxml"))) ;
            newRoot.setPrefWidth(this.root.getWidth()) ;
            newRoot.setPrefHeight(this.root.getHeight()) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) this.membersBtn.getScene().getWindow() ;
            window.setScene(scene) ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in HomeViewController.loadMembersView() -> \n" + e) ;
            e.printStackTrace() ;
        }
    }


    public void loadLibrariansView()
    {
        try
        {
            AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/LibrarianAccounts_FXML.fxml"))) ;
            newRoot.setPrefWidth(this.root.getWidth()) ;
            newRoot.setPrefHeight(this.root.getHeight()) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) this.librariansBtn.getScene().getWindow() ;
            window.setScene(scene) ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in HomeViewController.loadLibrariansView() -> \n" + e) ;
            e.printStackTrace() ;
        }
    }


    public void loadBooksPage()
    {
        try
        {
            AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/BooksPage_FXML.fxml"))) ;
            newRoot.setPrefWidth(this.root.getWidth()) ;
            newRoot.setPrefHeight(this.root.getHeight()) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) this.booksBtn.getScene().getWindow() ;
            window.setScene(scene) ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in HomeViewController.loadBooksPage() -> \n" + e) ;
            e.printStackTrace() ;
        }
    }


    public void editProfile()
    {
        try
        {
            AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/EditProfile_FXML.fxml"))) ;
            newRoot.setPrefWidth(this.root.getWidth()) ;
            newRoot.setPrefHeight(this.root.getHeight()) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) this.editProfileBtn.getScene().getWindow() ;
            window.setScene(scene) ;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void signOut()
    {
        try
        {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/adminapp/view/SignInPage_FXML.fxml"))) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/LoginPageStyleSheet.css") ;

            Stage window = (Stage) this.signOutBtn.getScene().getWindow() ;
            window.close() ;

            Stage stage = new Stage() ;
            stage.setTitle("LOGIN TO YOUR ACCOUNT") ;
            stage.setResizable(false) ;
            stage.setScene(scene) ;
            stage.show() ;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}