package adminapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianAccountViewController extends HomeViewController implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public VBox vbox ;
    public HBox hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9 ;
    public Label idLbl, firstNameLbl, lastNameLbl, usernameLbl, emailLbl, dobLbl, phoneLbl, nicLbl, addressLbl, dateCreatedLbl ;
    public TextField idField, firstNameField, lastNameField, usernameField, emailField, dobField, phoneField, nicField, addressField, dateCreatedField ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        idField.setText(LibrarianAccountsController.selectedAccount.getId()) ;
        firstNameField.setText(LibrarianAccountsController.selectedAccount.getFirstName()) ;
        lastNameField.setText(LibrarianAccountsController.selectedAccount.getLastName()) ;
        usernameField.setText(LibrarianAccountsController.selectedAccount.getUsername()) ;
        emailField.setText(LibrarianAccountsController.selectedAccount.getEmail()) ;
        dobField.setText(LibrarianAccountsController.selectedAccount.getDateOfBirth()) ;
        phoneField.setText(LibrarianAccountsController.selectedAccount.getPhone()) ;
        nicField.setText(LibrarianAccountsController.selectedAccount.getNicNumber()) ;
        addressField.setText(LibrarianAccountsController.selectedAccount.getAddress()) ;
        dateCreatedField.setText(LibrarianAccountsController.selectedAccount.getDateCreated()) ;

        header.setId("header_style") ;
        root.setId("root_style") ;
    }
}
