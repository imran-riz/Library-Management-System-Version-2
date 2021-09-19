package librarianapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.* ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.* ;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import librarianapp.model.* ;
import librarianapp.resources.DataBaseController;

public class AllRecordsController implements Initializable
{
    public AnchorPane root ;
    public HBox hbox1 ;
    public VBox vbox ;
    public Label header ;
    public ChoiceBox<String> choiceBox ;
    public TextField searchField ;
    public Button searchBtn, printBtn ;
    public TableView<BookRecord> tableView ;
    public TableColumn<BookRecord, String> refNumCol, bookIDCol, accountIDCol, reservedOnCol, cancelledOnCol, issuedOnCol, requestToReturnCol, returnedOnCol ;

    private DataBaseController dataBaseController ;

    protected static BookRecord selectedBookRecord ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            initializeTableView() ;
            initializeChoiceBox() ;

            searchField.setOnKeyPressed(keyEvent ->
            {
                if(keyEvent.getCode() == KeyCode.ENTER)
                {
                    searchItem() ;
                }
                else if(keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.DELETE)
                {
                    if(searchField.getText().isBlank())
                        tableView.setItems(dataBaseController.getAllBookRecords()) ;
                }
            });

            header.setId("header_style") ;
            searchBtn.setId("search_edit_print_btn_style") ;
            printBtn.setId("search_edit_print_btn_style") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeTableView()
    {
        // first initialize all the columns
        this.refNumCol.setCellValueFactory(new PropertyValueFactory<>("refNum")) ;
        this.accountIDCol.setCellValueFactory(new PropertyValueFactory<>("accountID")) ;
        this.bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
        this.reservedOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfReservation")) ;
        this.cancelledOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfCancellation")) ;
        this.issuedOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfIssue")) ;
        this.requestToReturnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfRequestToReturn")) ;
        this.returnedOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfReturn")) ;

        this.tableView.setItems(dataBaseController.getAllBookRecords()) ;
        this.tableView.setOnMouseClicked(mouseEvent ->
        {
            if(mouseEvent.getClickCount() == 2)
            {
                selectedBookRecord = this.tableView.getSelectionModel().getSelectedItem();

                if(selectedBookRecord != null)
                {
                    try
                    {
                        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BookRecordView_FXML.fxml")));

                        Scene scene = new Scene(parent);
                        scene.getStylesheets().add("/librarianapp/resources/css/InfoViewStyleSheet.css");

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(false);
                        stage.showAndWait();
                    }
                    catch (Exception e)
                    {
                        System.out.println("\nExceptions in AllRecordsController.initializeTableView() -> " + e);
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    private void initializeChoiceBox()
    {
        ObservableList<String> list = FXCollections.observableArrayList() ;
        list.addAll("by ref. number", "by book ID", "by account ID", "by date of reservation", "by date of issue") ;

        choiceBox.setItems(list) ;
        choiceBox.setValue(list.get(0)) ;
    }


    public void searchItem()
    {
        String field ;

        switch (searchField.getText().toLowerCase())
        {
            case "by ref. num":
                field = "RefNum" ;
                break ;

            case "by book id":
                field = "BookID" ;
                break ;

            case "by account id":
                field = "AccountID" ;
                break ;

            case "by date of reservation":
                field = "ReservedDate" ;
                break ;

            default:
                field = "IssuedDate" ;
                break ;
        }

        tableView.setItems(dataBaseController.getFromBookRecords(field, searchField.getText())) ;
    }


    public void print()
    {

    }
}
