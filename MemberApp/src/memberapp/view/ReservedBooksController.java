package memberapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.* ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import memberapp.resources.DataBaseController;
import memberapp.Main;
import memberapp.model.BookRecord;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservedBooksController implements Initializable
{
    public AnchorPane anchorPane ;
    public TableView<BookRecord> tableView ;
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
            header.setId("sub_header_style");
            anchorPane.setId("center_anchor_pane");
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
        refNumCol.setCellValueFactory(new PropertyValueFactory<>("refNum")) ;
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfReservation")) ;

        // get the list of borrowed books from the database controller and set it as the table's items
        tableView.setItems(dataBaseController.getReservedBooks(Main.userAccount.getId())) ;
        tableView.setOnMouseClicked(mouseEvent ->
        {
            // prompt the user to cancel his reservation of the selected book or not
            if(mouseEvent.getClickCount() == 2)
                cancelReservation(tableView.getSelectionModel().getSelectedItem()) ;
        });
    }


    private void cancelReservation(BookRecord selectedBook)
    {
        if(confirmationBox.show(("Cancel your reservation of " + selectedBook.getBook().getTitle() + " by " + selectedBook.getBook().getAuthor()) + "?", "Cancellation Of Reserved Book", "Yes", "No"))
        {
            if(dataBaseController.cancelReservation(selectedBook))
            {
                messageBox.show("The reservation was successfully cancelled.", "Cancelled.") ;
                tableView.getItems().remove(selectedBook) ;
            }
            else
            {
                messageBox.show("There was an error cancelling your reservation of the book. Please try again later.", "ERROR") ;
            }
        }
    }
}