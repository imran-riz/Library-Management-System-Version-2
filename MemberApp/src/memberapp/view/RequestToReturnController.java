package memberapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.* ;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane ;

import memberapp.* ;
import memberapp.model.BookRecord;
import memberapp.resources.DataBaseController;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestToReturnController implements Initializable
{
    public AnchorPane anchorPane ;
    public TableView<BookRecord> tableView ;
    public TableColumn<BookRecord, String> refNumCol, bookIDCol, dateCol, titleCol, authorCol ;
    public Label header ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            DataBaseController dataBaseController = new DataBaseController();

            refNumCol.setCellValueFactory(new PropertyValueFactory<>("refNum")) ;
            bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID")) ;
            dateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfRequestToReturn")) ;
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title")) ;
            authorCol.setCellValueFactory(new PropertyValueFactory<>("author")) ;

            this.tableView.setItems(dataBaseController.getBooksRequestedToReturn(Main.userAccount.getId())) ;

            header.setId("sub_header_style") ;
            anchorPane.setId("center_anchor_pane") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }
}
