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

public class ReservedBooksController implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public TableView<BookRecord> tableView;
    public TableColumn<BookRecord, String> refNumCol, accountIDCol,  bookIDCol, titleCol, authorCol, reservedOnCol ;
    public HBox hbox;
    public VBox vbox ;
    public ChoiceBox<String> choiceBox;
    public TextField searchField;
    public Button searchBtn;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;
    private final ConfirmationBox confirmationBox = new ConfirmationBox() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            initializeChoiceBox() ;
            initializeTable() ;

            this.searchField.setOnKeyPressed(keyEvent ->
            {
               if(!searchField.getText().isBlank() && keyEvent.getCode() == KeyCode.ENTER)
                    searchItem() ;
               else if(keyEvent.getCode() == KeyCode.BACK_SPACE && searchField.getText().isBlank())
                    this.tableView.setItems(dataBaseController.getAllReservedBooks()) ;
            }) ;

            header.setId("header_style") ;
            searchBtn.setId("search_edit_print_btn_style") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeChoiceBox()
    {
        ObservableList<String> itemList = FXCollections.observableArrayList() ;
        itemList.addAll("by Ref. Num", "by Book ID", "by Account ID", "by Title", "by Author", "by Reservation Date") ;
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
        this.reservedOnCol.setCellValueFactory(new PropertyValueFactory<>("dateOfReservation")) ;

        this.tableView.setItems(dataBaseController.getAllReservedBooks()) ;
        this.tableView.setOnMouseClicked(mouseEvent ->
        {
           if(mouseEvent.getClickCount() == 2)
           {
               BookRecord selectedBookRecord = this.tableView.getSelectionModel().getSelectedItem() ;
               String message = " Issue " + selectedBookRecord.getTitle() + " by " + selectedBookRecord.getAuthor() + " to " + dataBaseController.getFromAccount(selectedBookRecord.getAccountID(), selectedBookRecord.getAccountType(), "FirstName") + "? " ;

               if(confirmationBox.show(message, "ISSUE BOOK", "Yes", "No"))
               {
                   if(dataBaseController.issueBook(selectedBookRecord))
                   {
                       this.tableView.getItems().remove(selectedBookRecord) ;
                       messageBox.show("The book was successfully issued", "SUCCESS");
                   }
               }
           }
        }) ;
    }


    public void searchItem()
    {
        String field, enteredText ;

        switch (this.choiceBox.getValue())
        {
            case "by Ref. Num" : field = "RefNum" ;
                                 break ;
            case "by Book ID" : field = "BookID" ;
                                break ;
            case "by Account ID" : field = "AccountID" ;
                                   break ;
            case "by Title" : field = "Title" ;
                               break ;
            case "by Author" : field = "Author" ;
                               break ;
            case "by Reservation Date" : field = "ReservedDate" ;
                                         break ;
            default: field = "" ;
                     break ;
        }

        enteredText = this.searchField.getText() ;

        this.tableView.setItems(dataBaseController.getFromBookRecords(field, enteredText)) ;
    }
}
