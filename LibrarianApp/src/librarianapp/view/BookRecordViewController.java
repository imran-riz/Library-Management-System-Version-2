package librarianapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import librarianapp.resources.DataBaseController;

import java.net.URL;
import java.util.ResourceBundle;

public class BookRecordViewController implements Initializable
{
    public AnchorPane root ;
    public Pane pane ;
    public Label header, refNumLbl, bookIDLbl, titleLbl, accountIDLbl, nameLbl, reservedOnLbl, cancelledOnLbl, issuedOnLbl, requestSentOnLbl, returnedOnLbl ;
    public TextField refNumField, bookIDField, titleField, accountIDField, nameField, reservedOnField, cancelledOnField, issuedOnField, requestSentOnField, returnedOnField ;

    public BookRecordViewController() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            DataBaseController dataBaseController = new DataBaseController();

            String name = dataBaseController.getFromAccount(AllRecordsController.selectedBookRecord.getAccountID(), AllRecordsController.selectedBookRecord.getAccountType(), "FirstName") ;
            name = name.concat(" " + dataBaseController.getFromAccount(AllRecordsController.selectedBookRecord.getAccountID(), AllRecordsController.selectedBookRecord.getAccountType(), "LastName")) ;

            refNumField.setText(AllRecordsController.selectedBookRecord.getRefNum()) ;
            bookIDField.setText(AllRecordsController.selectedBookRecord.getBookID()) ;
            titleField.setText(AllRecordsController.selectedBookRecord.getTitle()) ;
            accountIDField.setText(AllRecordsController.selectedBookRecord.getAccountID()) ;
            nameField.setText(name) ;
            reservedOnField.setText(AllRecordsController.selectedBookRecord.getDateOfReservation()) ;
            cancelledOnField.setText(AllRecordsController.selectedBookRecord.getDateOfCancellation()) ;
            issuedOnField.setText(AllRecordsController.selectedBookRecord.getDateOfIssue()) ;
            requestSentOnField.setText(AllRecordsController.selectedBookRecord.getDateOfRequestToReturn()) ;
            returnedOnField.setText(AllRecordsController.selectedBookRecord.getDateOfReturn()) ;

            root.setStyle("-fx-background-color: #2eac68");
            header.setId("header_style") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }
}
