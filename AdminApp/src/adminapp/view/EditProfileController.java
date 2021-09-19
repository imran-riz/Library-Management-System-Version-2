package adminapp.view;

import adminapp.resources.DataBaseController;
import adminapp.resources.Encrypter;
import adminapp.Main;
import adminapp.model.Account;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class EditProfileController implements Initializable
{
    public AnchorPane root;
    public VBox vbox ;
    public HBox hbox1, hbox2, hbox3, hbox5, hbox6, hbox7 ;
    public Label header, firstNameLbl, lastNameLbl, usernameLbl, emailLbl, phoneLbl, addressLbl, nicLbl ;
    public TextField firstNameField, lastNameField, usernameField, emailField, phoneField, addressField, nicField ;
    public PasswordField passwordField ;
    public Button saveBtn, backBtn, changePswrdBtn ;

    private DataBaseController dataBaseController ;

    private final MessageBox messageBox = new MessageBox() ;
    private String prevFirstName, prevLastName, prevUsername, prevPassword, prevEmail, prevPhone, prevAddress, prevNic ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController() ;
            initializeCurrentDetails();

            this.root.setOnKeyPressed(keyEvent ->
            {
                if (keyEvent.getCode() == KeyCode.ENTER) saveChanges();
            });

            saveBtn.setStyle("-fx-background-color: #81b1eb ;" +
                    "-fx-text-fill: black ;");

            root.setId("root_style");
            header.setId("header_style");
            backBtn.setId("back_btn_style");
            changePswrdBtn.setId("back_btn_style");
            firstNameLbl.setId("instruction_style");
            lastNameLbl.setId("instruction_style");
            usernameLbl.setId("instruction_style");
            emailLbl.setId("instruction_style");
            phoneLbl.setId("instruction_style");
            nicLbl.setId("instruction_style");
            addressLbl.setId("instruction_style");
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
        prevPassword = Main.userAccount.getPassword() ;
        prevEmail = Main.userAccount.getEmail() ;
        prevPhone = Main.userAccount.getPhone() ;
        prevAddress = Main.userAccount.getAddress() ;
        prevNic = Main.userAccount.getNicNumber() ;

        firstNameField.setText(prevFirstName) ;
        lastNameField.setText(prevLastName) ;
        usernameField.setText(prevUsername) ;
        emailField.setText(prevEmail) ;
        phoneField.setText(prevPhone) ;
        nicField.setText(prevNic) ;
        addressField.setText(prevAddress) ;
    }


    public void goBack()
    {
        try
        {
            AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/HomeView_FXML.fxml"))) ;
            newRoot.setPrefWidth(root.getWidth()) ;
            newRoot.setPrefHeight(root.getHeight()) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/HomePageStyleSheet.css") ;

            Stage window = (Stage) this.backBtn.getScene().getWindow() ;
            window.setScene(scene) ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in LibrarianAccountsController.goBack() -> \n" + e) ;
            e.printStackTrace() ;
        }
    }


    public void changePassword()
    {
        try
        {
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminapp/view/ChangePassword_FXML.fxml"))) ;

            Scene scene = new Scene(newRoot) ;
            scene.getStylesheets().add("/adminapp/resources/css/InfoViewStyleSheet.css") ;

            Stage window = new Stage() ;
            window.setResizable(false) ;
            window.setScene(scene) ;
            window.initModality(Modality.APPLICATION_MODAL) ;
            window.showAndWait() ;
        }
        catch(Exception e)
        {
            e.printStackTrace() ;
        }
    }


    public void saveChanges()
    {
        String salt = "", encryptedPswrd = prevPassword ;

        if(!passwordField.getText().equals(prevPassword))
        {
            Encrypter encoder = new Encrypter() ;
            encryptedPswrd = encoder.encrypt(passwordField.getText()) ;
            salt = encoder.getSalt() ;

            System.out.println("\nPassword = " + encryptedPswrd + "\n") ;
            System.out.println("\nSalt = " + salt + "\n") ;
        }


        // create a temporary account using the details in the text fields and some details in the user's account
        Account tempAcc = new Account(Main.userAccount.getAcctType(), Main.userAccount.getId(), firstNameField.getText(), lastNameField.getText(), usernameField.getText(), encryptedPswrd, Main.userAccount.getDateCreated()) ;
        tempAcc.setNumOfBooksBorrowed(Main.userAccount.getNumOfBooksBorrowed()) ;
        tempAcc.setDateOfBirth(Main.userAccount.getDateOfBirth()) ;
        tempAcc.setEmail(emailField.getText()) ;
        tempAcc.setAddress(addressField.getText()) ;
        tempAcc.setPhone(phoneField.getText()) ;
        tempAcc.setNicNumber(nicField.getText()) ;
        tempAcc.setSalt(salt) ;

        // first check if there had been any changes made
        if(!firstNameField.getText().equals(prevFirstName) || !lastNameField.getText().equals(prevLastName) || !usernameField.getText().equals(prevUsername) || !passwordField.getText().equals(prevPassword) || !emailField.getText().equals(prevEmail) || !phoneField.getText().equals(prevPhone) || !addressField.getText().equals(prevAddress) || !nicField.getText().equals(prevNic))
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
                if(dataBaseController.checkUsername("ADMIN", Main.userAccount.getId(), usernameField.getText()))
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
