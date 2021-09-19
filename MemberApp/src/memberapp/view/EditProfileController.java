package memberapp.view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable ;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.* ;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.* ;
import javafx.stage.Modality;
import javafx.stage.Stage;
import memberapp.resources.DataBaseController;
import memberapp.Main;
import memberapp.model.Account;

import java.net.URL;
import java.util.ResourceBundle;


public class EditProfileController implements Initializable
{
    public AnchorPane anchorPane;
    public VBox vbox ;
    public HBox hbox0, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7 ;
    public Label header, firstNameLbl, lastNameLbl, usernameLbl, emailLbl, phoneLbl, addressLbl ;
    public TextField firstNameField, lastNameField, usernameField, emailField, phoneField, addressField ;
    public Button saveBtn, changePswrdBtn ;

    private DataBaseController dataBaseController ;
    private final MessageBox messageBox = new MessageBox() ;
    private String prevFirstName, prevLastName, prevUsername, prevEmail, prevPhone, prevAddress, prevDOB ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;

            initializeCurrentDetails() ;
            this.anchorPane.setOnKeyPressed(keyEvent ->
            {
                if(keyEvent.getCode() == KeyCode.ENTER)
                    saveChanges();
            });

            anchorPane.setId("center_anchor_pane") ;
            header.setId("sub_header_style") ;
            firstNameLbl.setId("instruction_style") ;
            lastNameLbl.setId("instruction_style") ;
            usernameLbl.setId("instruction_style") ;
            emailLbl.setId("instruction_style") ;
            phoneLbl.setId("instruction_style") ;
            addressLbl.setId("instruction_style") ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    // sets local variables and the TextFields using the details in Main.userAcct
    private void initializeCurrentDetails()
    {
        prevFirstName = Main.userAccount.getFirstName() ;
        prevLastName = Main.userAccount.getLastName() ;
        prevUsername = Main.userAccount.getUsername() ;
        prevEmail = Main.userAccount.getEmail() ;
        prevPhone = Main.userAccount.getPhone() ;
        prevAddress = Main.userAccount.getAddress() ;
        prevDOB = Main.userAccount.getDateOfBirth() ;

        firstNameField.setText(prevFirstName) ;
        lastNameField.setText(prevLastName) ;
        usernameField.setText(prevUsername) ;
        emailField.setText(prevEmail) ;
        phoneField.setText(prevPhone) ;
        addressField.setText(prevAddress) ;
    }


    public void changePassword()
    {
        try
        {
            Parent newRoot = FXMLLoader.load(getClass().getResource("/memberapp/view/ChangePassword_FXML.fxml")) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/memberapp/resources/css/InfoViewStyleSheet.css") ;

            Stage window = new Stage() ;
            window.setTitle("Change Your Password");
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


    public void saveChanges()
    {
        // create a temporary account using the deatils in the textfields and some details in the user's account
        Account tempAcc = new Account("MEMBER", Main.userAccount.getId(), firstNameField.getText(), lastNameField.getText(), usernameField.getText(), Main.userAccount.getPassword(), Main.userAccount.getDateCreated()) ;
        tempAcc.setDateOfBirth(prevDOB) ;
        tempAcc.setEmail(Main.userAccount.getEmail()) ;
        tempAcc.setNumOfBooksBorrowed(Main.userAccount.getNumOfBooksBorrowed()) ;
        tempAcc.setAddress(Main.userAccount.getAddress()) ;
        tempAcc.setPhone(Main.userAccount.getPhone()) ;

        // first check if there had been any changes made
        if(!firstNameField.getText().equals(prevFirstName) || !lastNameField.getText().equals(prevLastName) || !usernameField.getText().equals(prevUsername) || !emailField.getText().equals(prevEmail) || !phoneField.getText().equals(prevPhone) || !addressField.getText().equals(prevAddress))
        {
            // if the user has not changed his username
            if(usernameField.getText().equals(prevUsername))
            {
                if(dataBaseController.updateAcct(tempAcc))
                {
                    Main.userAccount = tempAcc ;
                    initializeCurrentDetails() ;
                    messageBox.show("Your profile was successfully updated!", "Profile Updated!");
                }
                else
                {
                    messageBox.show("Failed to update your profile. Please try again later.", "ERROR!");
                }
            }
            else
            {
                // if the user has changed his username, check if there is a similar one in the database
                if(dataBaseController.checkUsername("MEMBER", Main.userAccount.getId(), usernameField.getText()))
                {
                    // ask the user to type in another username as the entered username already exists
                    messageBox.show("The entered username already exists. Please select another username.", "Failed To Update Profile") ;
                }
                else
                {
                    if(dataBaseController.updateAcct(tempAcc))
                    {
                        Main.userAccount = tempAcc;
                        initializeCurrentDetails() ;
                        messageBox.show("Your profile was successfully updated!", "Profile Updated!") ;
                    }
                    else
                    {
                        messageBox.show("Failed to update your profile. Please try again later.", "ERROR!");
                    }
                }
            }
        }
    }
}
