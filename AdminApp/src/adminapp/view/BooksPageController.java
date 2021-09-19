package adminapp.view;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import adminapp.resources.DataBaseController;
import adminapp.model.Book;


public class BooksPageController implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public HBox hbox ;
    public ChoiceBox<String> choiceBox ;
    public TextField searchField ;
    public Button searchBtn, backBtn, getReportBtn ;
    public TableView<Book> tableView ;
    public TableColumn<Book, String> bookIDCol, titleCol, authorCol, isbnCol, copiesAvailableCol ;

    protected static Book selectedBook ;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;


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
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    search();
                } else if (keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE) {
                    if (searchField.getText().isBlank())
                        this.tableView.setItems(dataBaseController.getAllBooks());
                }
            });

            root.setId("root_style");
            searchBtn.setId("search_edit_btn_style");
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
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title")) ;
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author")) ;
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn")) ;
        copiesAvailableCol.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable")) ;

        tableView.setItems(dataBaseController.getAllBooks()) ;
        tableView.setOnMouseClicked(mouseEvent ->
        {
            if(mouseEvent.getClickCount() == 2)
            {
                selectedBook = this.tableView.getSelectionModel().getSelectedItem() ;

                try
                {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/BookView_FXML.fxml"))) ;

                    Scene scene = new Scene(root) ;
                    scene.getStylesheets().add("/adminapp/resources/css/InfoViewStyleSheet.css") ;

                    Stage stage = new Stage() ;
                    stage.setTitle("Book Info") ;
                    stage.setResizable(false) ;
                    stage.setScene(scene) ;
                    stage.initModality(Modality.APPLICATION_MODAL) ;
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
        list.addAll("By Book ID", "By Title", "By Author", "By ISBN") ;

        this.choiceBox.setItems(list) ;
        this.choiceBox.setValue("By Book ID") ;
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
            System.out.println("\nExceptions in BooksPageController.goBack() -> \n" + e) ;
            e.printStackTrace() ;
        }
    }


    public void search()
    {
        ObservableList<Book> list = null ;

        switch (choiceBox.getValue().toLowerCase())
        {
            case "by book id": list = dataBaseController.getFromBooks("BookID", searchField.getText());
                break ;
            case "by title": list = dataBaseController.getFromBooks("Title", searchField.getText());
                break ;
            case "by author": list = dataBaseController.getFromBooks("Author", searchField.getText());
                break ;
            case "by isbn": list = dataBaseController.getFromBooks("ISBN", searchField.getText());
                break ;
        }

        this.tableView.setItems(list) ;
    }


    public void generateReportAsPDF()
    {
        messageBox.show(" This feature is not available right now. ", "Error") ;
    }
}
