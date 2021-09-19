package adminapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberAccountViewController implements Initializable
{
    public AnchorPane root ;
    public Label header ;
    public VBox vbox ;
    public HBox hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9 ;
    public Label idLbl, firstNameLbl, lastNameLbl, usernameLbl, emailLbl, dobLbl, phoneLbl, addressLbl, dateCreatedLbl ;
    public TextField idField, firstNameField, lastNameField, usernameField, emailField, dobField, phoneField, addressField, dateCreatedField ;

    public MemberAccountViewController()
    {    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        idField.setText(MemberAccountsController.selectedAccount.getId()) ;
        firstNameField.setText(MemberAccountsController.selectedAccount.getFirstName()) ;
        lastNameField.setText(MemberAccountsController.selectedAccount.getLastName()) ;
        usernameField.setText(MemberAccountsController.selectedAccount.getUsername()) ;
        emailField.setText(MemberAccountsController.selectedAccount.getEmail()) ;
        dobField.setText(MemberAccountsController.selectedAccount.getDateOfBirth()) ;
        phoneField.setText(MemberAccountsController.selectedAccount.getPhone()) ;
        addressField.setText(MemberAccountsController.selectedAccount.getAddress()) ;
        dateCreatedField.setText(MemberAccountsController.selectedAccount.getDateCreated()) ;

        header.setId("header_style") ;
        root.setId("root_style") ;
    }
}
