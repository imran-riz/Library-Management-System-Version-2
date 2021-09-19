package adminapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class BookViewController implements Initializable
{
    public AnchorPane root ;
    public Label bookIDLbl, titleLbl, authorLbl, isbnLbl, publisherLbl, categoryLbl, copiesAvailableLbl, totalCopiesLbl ;
    public TextField bookIDField, isbnField, publisherField, categoryField, copiesAvailableField, totalCopiesField ;
    public VBox vbox ;


    public BookViewController() { }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        titleLbl.setText(BooksPageController.selectedBook.getTitle()) ;
        authorLbl.setText("By " + BooksPageController.selectedBook.getAuthor()) ;

        bookIDField.setText(BooksPageController.selectedBook.getBookID()) ;
        isbnField.setText(BooksPageController.selectedBook.getIsbn()) ;
        publisherField.setText(BooksPageController.selectedBook.getPublisher()) ;
        categoryField.setText(BooksPageController.selectedBook.getCategory()) ;
        copiesAvailableField.setText(BooksPageController.selectedBook.getCopiesAvailable()) ;
        totalCopiesField.setText(BooksPageController.selectedBook.getTotalCopies()) ;

        root.setId("root_style") ;
        titleLbl.setId("title_style") ;
        authorLbl.setId("author_style") ;
    }
}
