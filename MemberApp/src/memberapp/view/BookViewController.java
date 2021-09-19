package memberapp.view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import memberapp.* ;
import memberapp.model.BookRecord;
import memberapp.resources.DataBaseController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class BookViewController implements Initializable
{
    public AnchorPane root ;
    public VBox vbox ;
    public Button reserveBookBtn ;
    public Label bookIDLbl, titleLbl, authorLbl, isbnLbl, publisherLbl, categoryLbl, copiesAvailableLbl, totalCopiesLbl ;
    public TextField bookIDField, isbnField, publisherField, categoryField, copiesAvailableField, totalCopiesField ;

    private DataBaseController dataBaseController ;
    private final MessageBox messageBox = new MessageBox() ;


    public BookViewController() { }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            titleLbl.setText(HomeScreenController.selectedBook.getTitle()) ;
            authorLbl.setText("By " + HomeScreenController.selectedBook.getAuthor()) ;
            bookIDField.setText(HomeScreenController.selectedBook.getBookID()) ;
            isbnField.setText(HomeScreenController.selectedBook.getIsbn()) ;
            publisherField.setText(HomeScreenController.selectedBook.getPublisher()) ;
            categoryField.setText(HomeScreenController.selectedBook.getCategory()) ;
            copiesAvailableField.setText(HomeScreenController.selectedBook.getCopiesAvailable()) ;

            root.setStyle("-fx-background-color: #2e2e2e") ;
            titleLbl.setId("title_label_style") ;
            authorLbl.setId("author_label_style") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    public void reserveBook(ActionEvent event)
    {
        // first check if the user has not exceeded the limit of reserving books
        if (Integer.parseInt(Main.userAccount.getNumOfBooksBorrowed()) < 5)
        {
            // create a BookRecord using the Book the user selected
            BookRecord bookRecord = new BookRecord(HomeScreenController.selectedBook, Main.userAccount.getId(), "MEMBER");
            bookRecord.setDateOfReservation(LocalDate.now().toString());

            if (dataBaseController.checkIfReserved(Main.userAccount.getId(), bookRecord.getBook()))
            {
                messageBox.show("You have already reserved this book.", "Attention!");
            }
            else if (dataBaseController.checkIfIssued(Main.userAccount.getId(), bookRecord.getBook()))
            {
                messageBox.show("You have already borrowed this book.", "Attention!");
            }
            else if (dataBaseController.reserveBook(bookRecord))
            {
                HomeScreenController.table.setItems(dataBaseController.getAllBooks()) ;        // update the table that's displayed on the home screen
                this.copiesAvailableLbl.setText("Copies Available: " + HomeScreenController.selectedBook.getCopiesAvailable()) ;
                messageBox.show("The book was successfully reserved.", "Reservation Complete!") ;
            }
            else
            {
                messageBox.show("Failed to reserve book. Try again later.", "ERROR");
            }
        }
        else
        {
            messageBox.show("You have reached your book limit. Please return a book in order to borrow  " + HomeScreenController.selectedBook.getTitle() + " by " + HomeScreenController.selectedBook.getAuthor(), "ATTENTION!") ;
        }
    }
}
