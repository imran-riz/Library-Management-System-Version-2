package memberapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import memberapp.model.BookRecord;
import memberapp.resources.DataBaseController;
import memberapp.Main;

import java.net.URL;
import java.util.ResourceBundle;


public class IssuedBooksController implements Initializable
{
    public AnchorPane anchorPane ;
    public TableView<BookRecord> tableView;
    public TableColumn<BookRecord, String> refNumCol, bookIDCol, dateCol, titleCol, authorCol ;
    public Label header ;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;
    private final ConfirmationBox confirmationBox = new ConfirmationBox() ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController();

            initializeTableView();

            anchorPane.setId("center_anchor_pane");
            header.setId("sub_header_style");
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }

    private void initializeTableView()
    {
        // first initialize all the columns
        refNumCol.setCellValueFactory(new PropertyValueFactory<>("refNum")) ;
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfIssue")) ;
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title")) ;
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author")) ;

        // get the list of borrowed books from the database controller and set it as the table's items
        tableView.setItems(dataBaseController.getIssuedBooks(Main.userAccount.getId())) ;
        tableView.setOnMouseClicked(mouseEvent ->
        {
            // prompt the user to return the book or not
            if(mouseEvent.getClickCount() == 2)
            {
                BookRecord selectedBook = tableView.getSelectionModel().getSelectedItem() ;

                if(confirmationBox.show(("Do you wish to return " + selectedBook.getBook().getTitle() + " by " + selectedBook.getBook().getAuthor() + "?"), "Return A Book", "Yes", "No"))
                {
                    if(dataBaseController.sendRequestToReturn(selectedBook))
                        messageBox.show("Request to return the book has been sent!", "Message Sent!") ;
                    else
                        messageBox.show("Failed to send request. Please try again later", "ERROR") ;
                }
            }
        });
    }
}
