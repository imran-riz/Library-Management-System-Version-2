package librarianapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import librarianapp.model.* ;
import librarianapp.resources.DataBaseController;


public class BookViewController implements Initializable
{
    public Pane root ;
    public Label header, bookIDLbl, titleLbl, authorLbl, isbnLbl, publisherLbl, categoryLbl, totalCopiesLbl, copiesAvailableLbl ;
    public TextField bookIDField, titleField, authorField, isbnField, publisherField, categoryField, totalCopiesField, copiesAvailableField ;
    public Button editBtn ;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;
    private final ConfirmationBox confirmationBox = new ConfirmationBox() ;

    private Boolean editing ;
    public String prevBookID, prevTitle, prevAuthor, prevIsbn, prevPublisher, prevCategory, prevTotalCopies, prevCopiesAvailable ;



    public BookViewController() { }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            Book theBook = HomeScreenController.selectedBook;

            this.prevBookID = theBook.getBookID() ;
            this.prevTitle = theBook.getTitle() ;
            this.prevAuthor = theBook.getAuthor() ;
            this.prevIsbn = theBook.getIsbn() ;
            this.prevPublisher = theBook.getPublisher() ;
            this.prevCategory = theBook.getCategory() ;
            this.prevTotalCopies = theBook.getTotalCopies() ;
            this.prevCopiesAvailable = theBook.getCopiesAvailable() ;

            editing = false ;

            initializeTextFields() ;

            header.setId("header_style") ;
            root.setStyle("-fx-background-color: #2eac68");
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeTextFields()
    {
        // initialize the text fields with the data held in the Book object
        this.bookIDField.setText(this.prevBookID) ;
        this.titleField.setText(this.prevTitle) ;
        this.authorField.setText(this.prevAuthor) ;
        this.isbnField.setText(this.prevIsbn) ;
        this.publisherField.setText(this.prevPublisher) ;
        this.categoryField.setText(this.prevCategory) ;
        this.totalCopiesField.setText(this.prevTotalCopies);
        this.copiesAvailableField.setText(this.prevCopiesAvailable) ;

        // make the field uneditable
        this.bookIDField.setEditable(false) ;
        this.titleField.setEditable(false) ;
        this.authorField.setEditable(false) ;
        this.isbnField.setEditable(false) ;
        this.publisherField.setEditable(false) ;
        this.categoryField.setEditable(false) ;
        this.totalCopiesField.setEditable(false) ;
        this.copiesAvailableField.setEditable(false) ;
    }


    public void editOrSave()
    {
        if(editing && confirmationBox.show("Save changes made?", "SAVE CHANGES", "Yes", "No"))
        {
            // check if the entered book id has no duplicate in the database
            if(this.bookIDField.getText().equals(prevBookID) && dataBaseController.checkBookID(bookIDField.getText()))
            {
                messageBox.show("Entered book ID already exists, please enter another book ID.", "ERROR") ;
            }
            else
            {
                // make the text fields uneditable
                this.bookIDField.setEditable(false) ;
                this.titleField.setEditable(false) ;
                this.authorField.setEditable(false) ;
                this.isbnField.setEditable(false) ;
                this.publisherField.setEditable(false) ;
                this.categoryField.setEditable(false) ;
                this.totalCopiesField.setEditable(false) ;
                this.copiesAvailableField.setEditable(false) ;

                editBtn.setText("EDIT") ;
                editing = false ;
            }
        }
        else
        {
            // make the text fields editable
            this.bookIDField.setEditable(true) ;
            this.titleField.setEditable(true) ;
            this.authorField.setEditable(true) ;
            this.isbnField.setEditable(true) ;
            this.publisherField.setEditable(true) ;
            this.categoryField.setEditable(true) ;
            this.totalCopiesField.setEditable(false) ;
            this.copiesAvailableField.setEditable(true) ;

            editBtn.setText("SAVE") ;
            editing = true ;
        }
    }
}