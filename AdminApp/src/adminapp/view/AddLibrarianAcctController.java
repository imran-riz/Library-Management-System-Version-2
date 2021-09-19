package adminapp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

import adminapp.resources.DataBaseController;
import adminapp.model.Account;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddLibrarianAcctController implements Initializable
{
    public AnchorPane root;
    public VBox vbox ;
    public HBox hbox0, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7 ;
    public Label header, firstNameLbl, lastNameLbl, usernameLbl, dobLbl, nicLbl, phoneLbl, emailLbl, addressLbl ;
    public TextField firstNameField, lastNameField, usernameField, nicField, phoneField, emailField, addressField ;
    public DatePicker dobPicker ;
    public Button createBtn ;

    private DataBaseController dataBaseController ;
    private final MessageBox messageBox = new MessageBox() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            this.dataBaseController = new DataBaseController();

            dobPicker.setEditable(false);
            root.setId("root_style");
            header.setId("header_style");
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }


    public void create()
    {
        String id, firstName, lastName, password, dob ;


        if(firstNameField.getText().isBlank() || lastNameField.getText().isBlank() || usernameField.getText().isBlank() || nicField.getText().isBlank() || dobPicker.getValue() == null || phoneField.getText().isBlank() || emailField.getText().isBlank() || addressField.getText().isBlank() || dobPicker.getValue() == null)
        {
            messageBox.show("All the data must be entered!", "ERROR!") ;
        }
        else
        {
            id = generateID(firstNameField.getText(), lastNameField.getText()) ;
            firstName = firstNameField.getText() ;
            lastName = lastNameField.getText() ;
            password = generatePassword(firstName, lastName) ;
            dob = dobPicker.getValue().toString() ;

            Account account = new Account("LIBRARIAN", id, firstNameField.getText(), lastNameField.getText(), usernameField.getText(), password, LocalDate.now().toString()) ;
            account.setDateOfBirth(dob) ;
            account.setEmail(emailField.getText()) ;
            account.setPhone(phoneField.getText()) ;
            account.setAddress(addressField.getText()) ;
            account.setNicNumber(nicField.getText()) ;

            if(dataBaseController.addAccount(account))
                messageBox.show("The account was successfully created.", "SUCCESS") ;
            else
                messageBox.show("Failed to create the account. Try again later.", "ERROR") ;
        }
    }


    private String generateID(String firstName, String lastName)
    {
        String num ;
        String id = firstName.toUpperCase().substring(0,1).concat(lastName.toUpperCase().substring(0,1)) ;

        id = id.concat(generateLetter()) ;

        do
        {
            num = Integer.toString(generateRandomNum(111, 222)) ;
            id = id.concat(num) ;
        }
        while(dataBaseController.getFromAccount(id, "LIBRARIAN", "FirstName") != null) ;

        return id ;
    }


    private String generatePassword(String firstName, String lastName)
    {
        String p = "" ;
        String r = "" ;
        int n ;

        // generate a random number from 1000 to 9999
        n = generateRandomNum(1000, 9999) ;

        for(int i = 0 ; i < 5 ; i++)
            r = r.concat(generateLetter()) ;

        p = p.concat(firstName.substring(0, 1)).concat(lastName.substring(0, 1)).concat(r).concat(Integer.toString(n)) ;

        return  p ;
    }


    private String generateLetter()
    {
        String ltrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
        Random random = new Random() ;
        int n = random.nextInt(10) ;

        return ltrs.substring(n, n+1) ;
    }


    private Integer generateRandomNum(int min, int max)
    {
        Random random = new Random() ;
        return random.nextInt(max + 1 - min) + min;
    }
}
