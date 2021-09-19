package librarianapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.VBox;
import librarianapp.model.* ;
import librarianapp.resources.DataBaseController;


public class ReturnRequestsController implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public TableView<BookRecord> tableView;
    public TableColumn<BookRecord, String> refNumCol, accountIDCol,  bookIDCol, titleCol, authorCol, issuedOnCol, requestedOnCol ;
    public HBox hbox;
    public VBox vbox ;
    public ChoiceBox<String> choiceBox;
    public TextField searchField;
    public Button searchBtn;

    private final ConfirmationBox confirmationBox = new ConfirmationBox() ;
    private final MessageBox messageBox = new MessageBox() ;

    private DataBaseController dataBaseController  ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            initializeChoiceBox();
            initializeTable();

            this.searchField.setOnKeyPressed(keyEvent ->
            {
                if (!searchField.getText().isBlank() && keyEvent.getCode() == KeyCode.ENTER)
                    searchItem();
                else if (keyEvent.getCode() == KeyCode.BACK_SPACE && searchField.getText().isBlank())
                    this.tableView.setItems(dataBaseController.getAllBooksRequestedToReturn());
            });

            header.setId("header_style");
            searchBtn.setId("search_edit_print_btn_style");
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeChoiceBox()
    {
        ObservableList<String> itemList = FXCollections.observableArrayList() ;
        itemList.addAll("by Ref. Num", "by Book ID", "by Account ID", "by Title", "by Author", "by Request Sent On", "by Issued On") ;
        this.choiceBox.setItems(itemList) ;
        this.choiceBox.setValue("by Ref. Num") ;
    }


    private void initializeTable()
    {
        // initialize all the columns
        this.refNumCol.setCellValueFactory(new PropertyValueFactory<>("refNum")) ;
        this.accountIDCol.setCellValueFactory(new PropertyValueFactory<>("accountID")) ;
        this.bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
        this.titleCol.setCellValueFactory(new PropertyValueFactory<>("title")) ;
        this.authorCol.setCellValueFactory(new PropertyValueFactory<>("author")) ;
        this.issuedOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfIssue")) ;
        this.requestedOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfRequestToReturn")) ;

        this.tableView.setItems(dataBaseController.getAllBooksRequestedToReturn()) ;
        this.tableView.setOnMouseClicked(mouseEvent ->
        {
            if(mouseEvent.getClickCount() == 2)
            {
                BookRecord selectedBookRecord = tableView.getSelectionModel().getSelectedItem() ;
                String message = " Accept the request to return " + selectedBookRecord.getTitle() + " by the account holder of " + selectedBookRecord.getAccountID() + "?" ;

                if(confirmationBox.show(message, "Confirmation", "Yes", "No"))
                {
                    if(dataBaseController.returnBook(selectedBookRecord, dataBaseController.getAccount(selectedBookRecord.getAccountID(), selectedBookRecord.getAccountType())))
                        messageBox.show("The process was successful.", "Success") ;
                    else
                        messageBox.show("Process failed. Try again later", "Failed") ;
                }

            }
        });
    }


    public void searchItem()
    {
        String field, enteredText ;

        switch (this.choiceBox.getValue())
        {
            case "by Ref. Num": field = "RefNum" ;
                break ;
            case "by Book ID": field = "BookID" ;
                break ;
            case "by Account ID": field = "AccountID" ;
                break ;
            case "by Title": field = "Title" ;
                break ;
            case "by Author": field = "Author" ;
                break ;
            case "by Reservation Date": field = "ReservedDate" ;
                break ;
            case "by Issued Date": field = "IssuedDate" ;
                break ;
            default: field = "" ;
                break ;
        }

        enteredText = this.searchField.getText() ;

        this.tableView.setItems(dataBaseController.getFromBookRecords(field, enteredText)) ;
    }
}
