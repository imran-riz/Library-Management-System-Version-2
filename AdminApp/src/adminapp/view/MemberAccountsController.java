package adminapp.view;

import adminapp.resources.DataBaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import adminapp.model.Account ;
import adminapp.Main ;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MemberAccountsController extends  Main implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public HBox hbox ;
    public VBox vbox ;
    public ChoiceBox<String> choiceBox ;
    public TextField searchField ;
    public Button searchBtn, backBtn, createBtn;
    public TableView<Account> tableView ;
    public TableColumn<Account, String> memberIDCol, firstNameCol, lastNameCol, usernameCol, emailCol ;

    private DataBaseController dataBaseController ;

    protected static Account selectedAccount ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController();

            initializeTableView();
            initializeChoiceBox();

            searchField.setOnKeyPressed(keyEvent ->
            {
                if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    if (searchField.getText().isBlank())
                        tableView.setItems(dataBaseController.getAllMemberAccounts());
                } else if (keyEvent.getCode() == KeyCode.ENTER) {
                    search();
                }
            });

            root.setId("root_style");
            header.setId("header_style");
            backBtn.setId("back_btn_style");
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeTableView()
    {
        memberIDCol.setCellValueFactory(new PropertyValueFactory<>("id")) ;
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName")) ;
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName")) ;
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username")) ;
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email")) ;

        tableView.setItems(dataBaseController.getAllMemberAccounts()) ;
        tableView.setOnMouseClicked(mouseEvent ->
        {
            if(mouseEvent.getClickCount() == 2)
            {
                selectedAccount = tableView.getSelectionModel().getSelectedItem() ;

                try
                {
                    Stage stage = new Stage() ;
                    stage.setResizable(false) ;

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/MemberAccountView_FXML.fxml"))) ;

                    Scene scene = new Scene(root) ;
                    scene.getStylesheets().add("/adminapp/resources/css/InfoViewStyleSheet.css") ;

                    stage.setScene(scene) ;
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait() ;

                }
                catch (Exception e)
                {
                    e.printStackTrace() ;
                }
            }
        });
    }


    private void initializeChoiceBox()
    {
        ObservableList<String> list = FXCollections.observableArrayList() ;
        list.addAll("By Member ID", "By First Name", "By Last Name", "By Username") ;

        this.choiceBox.setItems(list) ;
        this.choiceBox.setValue("By Member ID") ;
    }


    public void goBack()
    {
        try
        {
            AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/HomeView_FXML.fxml"))) ;
            newRoot.setPrefWidth(root.getWidth()) ;
            newRoot.setPrefHeight(root.getHeight()) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) this.backBtn.getScene().getWindow() ;
            window.setScene(scene) ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in MemberAccountsController.goBack() -> \n" + e) ;
            e.printStackTrace() ;
        }
    }


    public void search()
    {
        String field = "" ;
        ObservableList<Account> list ;


        switch (choiceBox.getValue().toLowerCase())
        {
            case "by member id" : field = "MemberID" ;
                break ;
            case "by first name" : field = "FirstName" ;
                break ;
            case "by last name" : field = "LastName" ;
                break ;
            case "by username" : field = "Username" ;
                break ;
        }

        list = dataBaseController.getFromAllAccounts("MEMBER", field, searchField.getText()) ;

        this.tableView.getItems().clear() ;

        if(!list.isEmpty()) this.tableView.setItems(list) ;
    }


    public void createAccount()
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/adminapp/view/AddMemberAcct_FXML.fxml"))) ;

            Scene scene = new Scene(root) ;
            scene.getStylesheets().add("/adminapp/resources/css/InfoViewStyleSheet.css") ;

            Stage stage = new Stage() ;
            stage.setOnCloseRequest(windowEvent ->
            {
                this.searchField.setText("") ;
                this.tableView.setItems(dataBaseController.getAllMemberAccounts()) ;
            }) ;
            stage.setResizable(false) ;
            stage.initModality(Modality.APPLICATION_MODAL) ;
            stage.setScene(scene) ;
            stage.showAndWait() ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }
}
