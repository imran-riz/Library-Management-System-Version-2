package librarianapp.view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Modality;
import javafx.stage.Stage;
import librarianapp.resources.DataBaseController;
import librarianapp.Main;
import librarianapp.model.Account;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle ;


public class EditProfileController implements Initializable
{
    public AnchorPane root ;
    public VBox vbox ;
    public HBox hbox0, hbox1, hbox2, hbox3, hbox4, hbox6, hbox7, hbox8, hbox9, hbox10, hbox11 ;
    public Label header, librarianIDLbl, firstNameLbl, lastNameLbl, usernameLbl, emailLbl, phoneLbl, dobLbl, nicLbl, addressLbl, dateCreatedLbl ;
    public TextField librarianIDField, firstNameField, lastNameField, usernameField, emailField, phoneField, dobField, nicField, addressField, dateCreatedField ;
    public Button editOrSaveBtn, changePswrdBtn ;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;

    private String prevUsername ;
    private boolean editing ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            initializeFields() ;
            editing = false ;

            header.setId("header_style") ;
            editOrSaveBtn.setId("search_edit_btn_style") ;
            librarianIDLbl.setId("instruction_style") ;
            firstNameLbl.setId("instruction_style") ;
            lastNameLbl.setId("instruction_style") ;
            usernameLbl.setId("instruction_style") ;
            emailLbl.setId("instruction_style") ;
            phoneLbl.setId("instruction_style") ;
            dobLbl.setId("instruction_style") ;
            nicLbl.setId("instruction_style") ;
            addressLbl.setId("instruction_style") ;
            dateCreatedLbl.setId("instruction_style") ;
            editOrSaveBtn.setId("search_edit_print_btn_style") ;
            changePswrdBtn.setId("change_password_btn_style") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    private void initializeFields()
    {
        prevUsername = Main.userAccount.getUsername() ;

        librarianIDField.setText(Main.userAccount.getId()) ;
        firstNameField.setText(Main.userAccount.getFirstName()) ;
        lastNameField.setText(Main.userAccount.getLastName()) ;
        usernameField.setText(prevUsername) ;
        emailField.setText(Main.userAccount.getEmail()) ;
        phoneField.setText(Main.userAccount.getPhone()) ;
        dobField.setText(Main.userAccount.getDateOfBirth());
        nicField.setText(Main.userAccount.getNicNumber()) ;
        addressField.setText(Main.userAccount.getAddress()) ;
        dateCreatedField.setText(Main.userAccount.getDateCreated()) ;

        // make all the fields uneditable
        librarianIDField.setEditable(false) ;
        firstNameField.setEditable(false) ;
        lastNameField.setEditable(false) ;
        usernameField.setEditable(false) ;
        emailField.setEditable(false) ;
        phoneField.setEditable(false) ;
        dobField.setEditable(false) ;
        nicField.setEditable(false) ;
        addressField.setEditable(false) ;
        dateCreatedField.setEditable(false) ;
    }


    public void changePassword()
    {
        try
        {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/librarianapp/view/ChangePassword_FXML.fxml"))) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/librarianapp/resources/css/InfoViewStyleSheet.css") ;

            Stage window = new Stage() ;
            window.setTitle("Change Your Password") ;
            window.setScene(scene) ;
            window.setResizable(false) ;
            window.initModality(Modality.APPLICATION_MODAL) ;
            window.showAndWait() ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    public void toggleState()
    {
        if(editing)
        {
            // first check if the username has been changed. If so check for duplicates in the database table
            if(!this.usernameField.getText().equals(prevUsername))
            {
                if(!dataBaseController.checkUsername("LIBRARIAN", this.librarianIDField.getText(), this.usernameField.getText()))
                    messageBox.show(" The username you entered already exists. Please enter another or restore your previous username ", "ERROR") ;
                else
                    saveChanges() ;
            }
            else
            {
                saveChanges() ;
            }
        }
        else
        {
            firstNameField.setEditable(true) ;
            lastNameField.setEditable(true) ;
            usernameField.setEditable(true) ;
            emailField.setEditable(true) ;
            phoneField.setEditable(true) ;
            nicField.setEditable(true) ;
            addressField.setEditable(true) ;

            editing = true ;
        }

        if(editing)
            editOrSaveBtn.setText("SAVE") ;
        else
            editOrSaveBtn.setText("EDIT") ;
    }


    private void saveChanges()
    {
        // create a temporary account using the details of userAccount and the details entered in the text fields
        Account tempAccount = new Account(Main.userAccount.getAcctType(), Main.userAccount.getId(), firstNameField.getText(), lastNameField.getText(), usernameField.getText(), Main.userAccount.getPassword(), Main.userAccount.getDateCreated()) ;
        tempAccount.setEmail(emailField.getText()) ;
        tempAccount.setPhone(phoneField.getText()) ;
        tempAccount.setDateOfBirth(dobField.getText()) ;
        tempAccount.setAddress(addressField.getText()) ;
        tempAccount.setNicNumber(nicField.getText()) ;

        if(dataBaseController.updateAcct(tempAccount))
        {
            Main.userAccount = tempAccount ;
            messageBox.show(" The changes made are saved! ", "SUCCESS") ;
            editing = false ;
        }
        else
        {
            messageBox.show(" Failed to save changes. Try again later. ", "ERROR") ;
        }
    }
}
