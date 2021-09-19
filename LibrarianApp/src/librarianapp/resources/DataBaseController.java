package librarianapp.resources;

import javafx.collections.* ;

import java.io.File ;
import java.sql.* ;
import java.time.LocalDate ;
import java.util.Scanner;

import librarianapp.model.* ;


public class DataBaseController
{
    private final ObservableList<Book> bookList = FXCollections.observableArrayList() ;
    private final ObservableList<BookRecord> bookRecordList = FXCollections.observableArrayList() ;

    private final Connection connection ;
    private Statement statement ;


    public DataBaseController() throws Exception
    {
        String dbName = "dbLibrary_2" ;
        String url = "jdbc:mysql://localhost:3306/" ;
        String password = getPassword() ;

        Class.forName("com.mysql.cj.jdbc.Driver") ;
        connection = DriverManager.getConnection(url.concat(dbName), "root", password) ;
    }



    /**
     * @return the password for the database server
     */
    private String getPassword()
    {
        String password = "" ;

        try
        {
            Scanner scanner = new Scanner(new File("Stuff.txt")) ;
            password = scanner.nextLine() ;
        }
        catch (Exception e)
        {
            System.out.println("\nException in DataBaseController.getPassword() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return password ;

    }


    /**
     * @param accountType the type of account trying to sign in. There are 3, ADMIN. LIBRARIAN and MEMBER.
     * @param username the username assigned to the account.
     * @param password the account's password.
     * @return an Account object containing all the info of that account that was stored in the database.
     */
    public Account signInToAcct(String accountType, String username, String password)
    {
        Account acct = null ;
        String query = "", typeOfID = "" ;

        if(username.equals("NULL") || password.equals("NULL"))  return null ;

        if(accountType.equalsIgnoreCase("admin"))
        {
            query = "SELECT * FROM tbl_admins WHERE Username = '" + username + "' ;" ;
            typeOfID = "AdminID" ;
        }
        else if(accountType.equalsIgnoreCase("librarian"))
        {
            query = "SELECT * FROM tbl_librarians WHERE Username = '" + username + "' ;" ;
            typeOfID = "LibrarianID" ;
        }
        else if(accountType.equalsIgnoreCase("member"))
        {
            query = "SELECT * FROM tbl_members WHERE Username = '" + username + "' ;" ;
            typeOfID = "MemberID" ;
        }

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;

            if(resultSet.next())
            {
                String storedPswrd = resultSet.getString("Password") ;
                String storedUsername = resultSet.getString("Username") ;

                if(storedPswrd.equals(password) && storedUsername.equals(username))
                {
                    String acctID = resultSet.getString(typeOfID) ;
                    acct = this.getAccount(acctID, accountType) ;
                }
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.loginToAcct() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return acct ;
    }


    /**
     * This method will create a new row in the respective table, depending on the account type, in the database.
     * @param account the Account object with all the details to be added in the new row.
     * @return a boolean value indicating whether the task was successful or not
     */
    public Boolean addAccount(Account account)
    {
        String query = "";

        if(account.getAcctType().equalsIgnoreCase("member"))
            query = "INSERT INTO tbl_members SET MemberID = \"" + account.getId() + "\", Status = 'ACTIVE', FirstName = \"" + account.getFirstName() + "\", LastName = \"" + account.getLastName() + "\", Username = \"" + account.getUsername() + "\", Password = \"" + account.getPassword() + "\", DateOfBirth = \"" + account.getDateOfBirth() + "\", Email = \"" + account.getEmail() + "\", Phone = \"" + account.getPhone() + "\", Address = \"" + account.getAddress() + "\", DateCreated = '" + account.getDateCreated() + "';" ;
        else if(account.getAcctType().equalsIgnoreCase("librarian"))
            query = "INSERT INTO tbl_librarians SET LibrarianID  = '" + account.getId() + "', FirstName = \"" + account.getFirstName() + "\", LastName = \"" + account.getLastName() + "\", Username = \"" + account.getUsername() + "\", Password = \"" + account.getPassword() + "\", DateOfBirth = '" + account.getDateOfBirth() + "', Email = '" + account.getEmail() + "', Phone = '" + account.getPhone() + "', NIC = '" + account.getNicNumber() + "', Address = '" + account.getAddress() + "', DateCreated = '" + account.getDateCreated() + "';" ;


        try
        {
            statement = connection.createStatement() ;
            statement.execute(query) ;
            statement.close() ;

            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.addAccount() -> \n" + e) ;
        }

        return false ;
    }


    /**
     * Using the details in the parameter, a new row in books table in the database will be created.
     * @param book the Book object that has all the data of the new book to be added in the database.
     * @return a boolean value indicating whether the task was successful or not
     */
    public Boolean addBook(Book book)
    {
        String query = "INSERT INTO tbl_books SET BookID = \"" + book.getBookID() + "\", Title = \"" + book.getTitle() + "\", Author = \"" + book.getAuthor() + "\", ISBN = \"" + book.getIsbn() + "\", Publisher = \"" + book.getPublisher() + "\", Category = \"" + book.getPublisher() + "\", TotalCopies = " + book.getTotalCopies() + ", CopiesAvailable = " + book.getCopiesAvailable() + " ;" ;

        try
        {
            statement = connection.createStatement() ;
            statement.execute(query) ;
            statement.close() ;

            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.addBook() -> \n" + e) ;
        }

        return false ;
    }


    /**
     * This method will update the table that's got the details of the account passed in the parameter
     * @param account the account that's got the details to be updated
     * @return a boolean value indicating whether the task was successful or not
     */
    public Boolean updateAcct(Account account)
    {
        String query = "" ;

        if(account.getAcctType().equalsIgnoreCase("member"))
            query = "UPDATE tbl_members SET FirstName = \"" + account.getFirstName() + "\", LastName = \"" + account.getLastName() + "\", Username = \"" + account.getUsername() + "\", Password = \"" + account.getPassword() + "\", Salt = \"" + account.getSalt() + "\", DateOfBirth = \"" + account.getDateOfBirth() + "\", Email = \"" + account.getEmail() + "\", Phone = \"" + account.getPhone() + "\", Address = \"" + account.getAddress() + "\" WHERE MemberID = \"" + account.getId() + "\";" ;
        else if(account.getAcctType().equalsIgnoreCase("librarian"))
            query = "UPDATE tbl_librarians SET FirstName = \"" + account.getFirstName() + "\", LastName = \"" + account.getLastName() + "\", Username = \"" + account.getUsername() + "\", Password = \"" + "\", Salt = \"" + account.getSalt() + account.getPassword() + "\", Email = \"" + account.getEmail() + "\", Phone = \"" + account.getPhone() + "\", Address = \"" + account.getAddress() + "\", NIC = \"" + account.getNicNumber() + "\" WHERE LibrarianID = \"" + account.getId() + "\";" ;
        else if(account.getAcctType().equalsIgnoreCase("admin"))
            query = "UPDATE tbl_admins SET FirstName = \"" + account.getFirstName() + "\", LastName = \"" + account.getLastName() + "\", Username = \"" + account.getUsername() + "\", Password = \"" + account.getPassword() + "\", Salt = \"" + account.getSalt()  + "\", Email = \"" + account.getEmail() + "\", Phone = \"" + account.getPhone() + "\", Address = \"" + account.getAddress() + "\", NIC = \"" + account.getNicNumber() + "\" WHERE AdminID = \"" + account.getId() + "\";";


        try
        {
            statement = connection.createStatement() ;
            statement.execute(query) ;
            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.updateAcct() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return false ;
    }


    /**
     * This method is used to update the current details of a book identified by a particular book ID.
     * @param prevID the current ID of the book to be updated.
     * @param book a Book object that has the details of the book to be updated.
     * @return a boolean value indicating whether the process was successful or not.
     */
    public Boolean updateBook(String prevID, Book book)
    {
        String query = "UPDATE tbl_books SET BookID = \"" + book.getBookID() + "\"" + ", Title = \"" + book.getTitle() + "\", Author = \"" + book.getAuthor() + "\", ISBN = \"" + book.getIsbn() + "\", Publisher = \"" + book.getPublisher() + "\", Category = \"" + book.getPublisher() + "\", TotalCopies = " + book.getTotalCopies() + ", CopiesAvailable = " + book.getCopiesAvailable() + " WHERE BookID = \"" + book.getBookID() + "\" WHERE BookID = '" + prevID + "' ;" ;

        try
        {
            statement = connection.createStatement() ;
            return statement.execute(query) ;
        }
        catch (Exception e) {
            System.out.println("\nExceptions in DataBaseController.updateBook() -> \n" + e) ;
        }

        return false ;
    }


    /**
     * This method is used when a book is reserved.
     * It wil create a new record in the database and will decrement the number of available books for this particular book
     * in the table of books.
     * @param bookRecord an object with the details of the book to be reserved.
     * @return a boolean value indicating whether the process was successful or not.
     */
    public Boolean reserveBook(BookRecord bookRecord)
    {
        int copiesCount = Integer.parseInt(bookRecord.getBook().getCopiesAvailable()) - 1 ;
        String ref_num = this.generateRefNum() ;
        String query = "INSERT INTO tbl_bookrecords SET RefNum = \"" + ref_num + "\", AccountID = \"" + bookRecord.getAccountID() + "\", AccountType = \"" + bookRecord.getAccountType() + "\", BookID = \"" + bookRecord.getBook().getBookID() + "\", ReservedDate = '" + bookRecord.getDateOfReservation() + "';" ;
        String query2 = "UPDATE tbl_books SET CopiesAvailable = '" + copiesCount + "' WHERE BookID = '" + bookRecord.getBook().getBookID() + "' ;" ;

        try
        {
            statement = connection.createStatement() ;
            statement.execute(query) ;
            statement.execute(query2) ;

            bookRecord.getBook().setCopiesAvailable(Integer.toString(copiesCount)) ;

            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nException in DatabaseController.reserveBook() -> \n" + e) ;
        }

        return false ;
    }


    /**
     * This method is used when a book is issued to a person.
     * The date the book that was issued is and when it's due is set.
     * @param bookRecord an object with the details of the book being issued.
     * @return a boolean value indicating whether the process was successful or not.
     */
    public Boolean issueBook(BookRecord bookRecord)
    {
        String date = LocalDate.now().toString() ;
        String dueDate = LocalDate.now().plusMonths(1).toString() ;
        String query1 = "UPDATE tbl_bookrecords SET IssuedDate = '" + date + "', DueDate = '" + dueDate + "' WHERE RefNum = '" + bookRecord.getRefNum() + "' ;" ;

        try
        {
            statement = connection.createStatement() ;

            statement.execute(query1) ;

            bookRecord.setDateOfIssue(date) ;
            bookRecord.setDateDue(dueDate) ;

            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nException in DatabaseController.issueBook() -> \n" + e) ;
        }

        return false ;
    }


    /**
     * This method is used when a request to return a book is made.
     * It will set the date when the request was made.
     * @param bookRecord an object with the details of the book that's requested to return.
     * @return a boolean value indicating whether the process was successful or not.
     */
    public Boolean sendRequestToReturn(BookRecord bookRecord)
    {
        String date = LocalDate.now().toString() ;
        String query = "UPDATE tbl_bookrecords SET ReturnSentOnDate = '" + date + "' WHERE RefNum = '" + bookRecord.getRefNum() + "' ;" ;

        try
        {
            statement = connection.createStatement() ;

            statement.execute(query) ;

            bookRecord.setDateOfRequestToReturn(date) ;

            return  true ;
        }
        catch (Exception e)
        {
            System.out.println("\nException in DatabaseController.sendRequestToReturn() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return false ;
    }


    /**
     * This method is used when a book is returned.
     * It will set the date when the book was returned. It will also increment the number of copies available
     * of the book that's returned by 1. The number of books the account has borrowed is decreased by 1.
     * @param bookRecord the object with the details of the book that's to be returned.
     * @param account the object with the details of the account that is trying to return the book
     * @return a boolean value indicating whether the process was successful or not.
     */
    public Boolean returnBook(BookRecord bookRecord, Account account)
    {
        int copiesCount = Integer.parseInt(bookRecord.getBook().getCopiesAvailable()) + 1 ;

        String date = LocalDate.now().toString() ;
        String query1 = "UPDATE tbl_bookrecords SET ReturnedDate = '" + date + "' WHERE RefNum = '" + bookRecord.getRefNum() + "' ;" ;
        String query2 = "UPDATE tbl_books SET CopiesAvailable = '" + copiesCount + "' WHERE BookID = '" + bookRecord.getBookID() + "'; " ;


        try
        {
            statement = connection.createStatement() ;

            statement.execute(query1) ;
            statement.execute(query2) ;

            bookRecord.setDateOfReturn(date) ;
            bookRecord.getBook().setCopiesAvailable(Integer.toString(copiesCount)) ;

            account.setNumOfBooksBorrowed(Integer.toString(Integer.parseInt(account.getNumOfBooksBorrowed()) - 1)) ;

            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.returnBook() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return false ;
    }


    /**
     * This method is used to cancel the reservation of a book.
     * It will set the date when the reservation was cancelled.
     * @param bookRecord an object with the details of the reserved book to be cancelled.
     * @return a boolean value indicating whether the process was successful or not.
     */
    public Boolean cancelReservation(BookRecord bookRecord)
    {
        String query = "UPDATE tbl_bookrecords SET CancellationDate = \"" + LocalDate.now() + "\";" ;

        try
        {
            statement = connection.createStatement() ;

            statement.execute(query) ;

            bookRecord.setDateOfCancellation(LocalDate.now().toString()) ;

            return true ;
        }
        catch (Exception e)
        {
            System.out.println("\nException in DataBaseController.cancelReservation() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return false ;
    }


    /**
     * This method will check if there's another account that has got the same username in the database as that
     * received in the parameter.
     * @param accountType the type of account
     * @param id the id of the account
     * @param usernameToCompare the username to be checked
     * @return true if a match is found, else false.
     */
    public Boolean checkUsername(String accountType, String id, String usernameToCompare)
    {
        boolean unique = true ;
        String query = "" ;

        if(accountType.equalsIgnoreCase("admin"))
            query = "SELECT AdminID, Username FROM tbl_admins WHERE Username = \"" + usernameToCompare + "\" AND AdminID != \"" + id + "\" ;" ;
        else if(accountType.equalsIgnoreCase("librarian"))
            query = "SELECT LibrarianID, Username FROM tbl_librarians WHERE Username = \"" + usernameToCompare + "\" AND LibrarianID != \"" + id + "\" ;" ;
        else if(accountType.equalsIgnoreCase("member"))
            query = "SELECT MemberID, Username FROM tbl_members WHERE Username = \"" + usernameToCompare + "\" AND MemberID != \"" + id + "\" ;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;

            if(resultSet.next())    unique = false ;

            resultSet.close() ;
            statement.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.checkUsername() -> \n" + e) ;
            e.printStackTrace() ;
        }
        return unique ;
    }


    /**
     * This method checks if a book ID already exists in the database.
     * @param bookID the book ID to be checked.
     * @return true if there is one, else false.
     */
    public Boolean checkBookID(String bookID)
    {
        boolean found = false ;
        String query = "SELECT * FROM tbl_books WHERE BookID = '" + bookID + "';" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;

            if(resultSet.next())    found = true ;

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e) {
            System.out.println("\nExceptions in DataBaseController.checkBookID() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return found ;
    }


    /**
     * This method will check if the reference number passed in the parameter already exists in the database table.
     * @param refNumToCheck the reference number to check
     * @return true if a match is found, else false.
     */
    private Boolean checkForRefNum(String refNumToCheck)
    {
        boolean found = false ;
        String query = "SELECT * FROM tbl_bookrecords WHERE RefNum = '" + refNumToCheck + "' ;" ;
        try
        {
            statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;

            if(resultSet.next())    found = true ;

            resultSet.close() ;
            statement.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.checkForRefNum() -> \n" + e) ;
        }

        return found ;
    }


    /**
     * This method is used to check if a particular book has been reserved by a person.
     * @param personID the id of the person.
     * @param book a Book object containing the details of the book to check.
     * @return true if there's a find else false.
     */
    public Boolean checkIfReserved(String personID, Book book)
    {
        boolean found = false ;
        String query = "SELECT * FROM tbl_bookrecords WHERE BookID = '" + book.getBookID() + "' AND AccountID = '" + personID + "' AND ReservedDate IS NOT NULL;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            found = resultSet.next() ;

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.checkIfReserved() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return found ;
    }


    /**
     * This method is used to check if a particular book has been issued to a person.
     * @param personID the id of the person.
     * @param book a Book object containing the details of the book to check.
     * @return true if there's a find else false.
     */
    public Boolean checkIfIssued(String personID, Book book)
    {
        boolean found = false ;
        String query = "SELECT * FROM tbl_bookrecords WHERE BookID = '" + book.getBookID() + "' AND AccountID = '" + personID + "' AND IssuedDate IS NOT NULL;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            found = resultSet.next() ;

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.checkIfIssued() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return found ;
    }


    /**
     * Used to get the salt used encrypt the password of an account.
     * @param accountType the type of account
     * @param username the username of the account
     * @return the salt used for encryption.
     */
    public String getSalt(String accountType, String username)
    {
        String salt = "", query = "" ;

        if(accountType.equalsIgnoreCase("admin"))
            query = "SELECT Salt FROM tbl_admins WHERE Username = '" + username + "' ;" ;
        else if(accountType.equalsIgnoreCase("librarian"))
            query = "SELECT Salt FROM tbl_librarians WHERE Username = '" + username + "' ;" ;
        else if(accountType.equalsIgnoreCase("member"))
            query = "SELECT Salt FROM tbl_members WHERE Username = '" + username + "' ;" ;

        try
        {
            Statement statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;

            if(resultSet.next())    salt = resultSet.getString("Salt") ;

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }

        return salt ;
    }


    /**
     * This method is used to get the details of an account as an Account object.
     * @param id the id of the account.
     * @param type the type of account.
     * @return an Account object with details of the account held in the details.
     */
    public Account getAccount(String id, String type)
    {
        String query = "" ;
        Account acct = null ;

        type = type.toUpperCase() ;

        if(type.equalsIgnoreCase("member"))
            query = "SELECT * FROM tbl_members WHERE MemberID = \"" + id + "\" ;" ;
        else if(type.equalsIgnoreCase("librarian"))
            query = "SELECT * FROM tbl_librarians WHERE LibrarianID = \"" + id + "\" ;" ;
        else if(type.equalsIgnoreCase("admin"))
            query = "SELECT * FROM tbl_admins WHERE AdminID = \"" + id + "\" ;" ;

        try
        {
            statement = connection.createStatement() ;

            if(!query.equals(""))
            {
                ResultSet resultSet = statement.executeQuery(query);

                if(resultSet.next())
                {
                    acct = new Account(type, id, resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("DateCreated"));
                    acct.setEmail(resultSet.getString("Email"));
                    acct.setPhone(resultSet.getString("Phone"));
                    acct.setDateOfBirth(resultSet.getString("DateOfBirth")) ;
                    acct.setAddress(resultSet.getString("Address"));
                    acct.setDateCreated(resultSet.getString("DateCreated"));
                    acct.setSalt(resultSet.getString("Salt")) ;
                    acct.setAcctType(type) ;

                    if (type.equalsIgnoreCase("member"))
                    {
                        acct.setDateOfBirth(resultSet.getString("DateOfBirth"));
                        acct.setNumOfBooksBorrowed(getOnlyIssuedBookCount(id));
                    }
                    else
                    {
                        acct.setNicNumber(resultSet.getString("NIC"));
                    }
                }

                resultSet.close() ;
                statement.close() ;
            }

        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.getAccount() -> \n" + e + "\n" + e.getMessage()) ;
            e.printStackTrace() ;
        }

        return acct ;
    }


    /**
     * This method used is to get certain data in a field from a particular account.
     * @param accountID the ID of the account.
     * @param accountType the type of account.
     * @param fieldSought the field sought (Eg: FirstName, LastName, etc.)
     * @return the value held in the field.
     */
    public String getFromAccount(String accountID, String accountType, String fieldSought)
    {
        String data = null ;
        String query = "" ;

        if(accountType.equalsIgnoreCase("member"))
            query = "SELECT " + fieldSought + " FROM tbl_members WHERE MemberID = '" + accountID + "';" ;
        else if(accountType.equalsIgnoreCase("librarian"))
            query = "SELECT " + fieldSought + " FROM tbl_librarians WHERE LibrarianID = '" + accountID + "';" ;


        try
        {
            Statement statement = connection.createStatement() ;
            ResultSet resultSet = statement.executeQuery(query) ;

            if(resultSet.next())    data = resultSet.getString(fieldSought) ;

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.getFromAccount() -> " + e.getMessage()) ;
            e.printStackTrace() ;
        }

        return data ;
    }


    /**
     * This method is used to get all the accounts (either Member or Librarian only) that have the value passed in a field
     * @param accountType the type of the account.
     * @param fieldSought the field sought (Eg: FirstName, LastName, Username, etc.)
     * @param val the value in the field.
     * @return an ObservableList<Account> with all the matching Accounts.
     */
    public ObservableList<Account> getFromAllAccounts(String accountType, String fieldSought, String val)
    {
        ObservableList<Account> list = FXCollections.observableArrayList() ;
        String query = "" ;
        Account acct = null ;

        if(accountType.equalsIgnoreCase("member"))
            query = "SELECT * FROM tbl_members WHERE " + fieldSought + " LIKE \"" + val + "%\" ;" ;
        else if(accountType.equalsIgnoreCase("librarian"))
            query = "SELECT * FROM tbl_librarians WHERE " + fieldSought + " LIKE \"" + val + "%\" ;" ;

        try
        {
            Statement statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;

            while(resultSet.next())
            {
                if(accountType.equalsIgnoreCase("member"))
                {
                    acct = new Account("MEMBER", resultSet.getString("MemberID"), resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("DateCreated"));
                    acct.setNumOfBooksBorrowed(getOnlyIssuedBookCount(resultSet.getString("MemberID"))) ;
                    acct.setDateOfBirth(resultSet.getString("DateOfBirth")) ;
                }
                else if(accountType.equalsIgnoreCase("librarian"))
                {
                    acct = new Account("LIBRARIAN", resultSet.getString("LibrarianID"), resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("DateCreated"));
                    acct.setNicNumber(resultSet.getString("NIC")) ;
                    acct.setDateOfBirth(resultSet.getString("DateOfBirth")) ;
                }

                acct.setEmail(resultSet.getString("Email"));
                acct.setPhone(resultSet.getString("Phone"));
                acct.setAddress(resultSet.getString("Address"));

                list.add(acct) ;
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }

        return list ;
    }


    /**
     * This method will return all the member accounts in the database.
     * @return an ObservableList<Account> containing all the member accounts.
     */
    public ObservableList<Account> getAllMemberAccounts()
    {
        ObservableList<Account> list = FXCollections.observableArrayList() ;
        String query ;
        Account account ;

        query = "SELECT * FROM tbl_members;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next())
            {
                account = new Account("MEMBER", resultSet.getString("MemberID"), resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("DateCreated"));
                account.setEmail(resultSet.getString("Email"));
                account.setPhone(resultSet.getString("Phone"));
                account.setAddress(resultSet.getString("Address"));
                account.setDateOfBirth(resultSet.getString("DateOfBirth")) ;
                account.setNumOfBooksBorrowed(getOnlyIssuedBookCount(resultSet.getString("MemberID"))) ;

                list.add(account) ;
            }

            statement.close() ;
            resultSet.close() ;
        }
        catch (Exception e)
        {
            System.out.println("Exceptions in DataBaseController.getAllMemberAccounts() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return  list ;
    }


    /**
     * This method will return all the librarian accounts in the database.
     * @return an ObservableList<Account> containing all the librarian accounts.
     */
    public ObservableList<Account> getAllLibrarianAccounts()
    {
        ObservableList<Account> list = FXCollections.observableArrayList() ;
        String query ;
        Account account ;

        query = "SELECT * FROM tbl_librarians;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next())
            {
                account = new Account("LIBRARIAN", resultSet.getString("LibrarianID"), resultSet.getString("FirstName"), resultSet.getString("LastName"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("DateCreated"));
                account.setEmail(resultSet.getString("Email"));
                account.setPhone(resultSet.getString("Phone"));
                account.setAddress(resultSet.getString("Address")) ;
                account.setDateOfBirth(resultSet.getString("DateOfBirth")) ;
                account.setNicNumber(resultSet.getString("NIC")) ;

                list.add(account) ;
            }

            statement.close() ;
            resultSet.close() ;
        }
        catch (Exception e)
        {
            System.out.println("Exceptions in DataBaseController.getAllLibrarianAccounts() -> \n" + e) ;
            e.printStackTrace() ;
        }

        return  list ;
    }


    /**
     * This method is used to get all the books in the database.
     * @return an ObservableList<Book> containing all the books in the database.
     */
    public ObservableList<Book> getAllBooks()
    {
        String query, bookID, title, author, isbn, publisher, category, copiesAvailable, totalCopies ;
        Book book ;

        bookList.clear() ;
        query = "SELECT * FROM tbl_books;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next())
            {
                bookID = resultSet.getString("BookID") ;
                title = resultSet.getString("Title") ;
                author = resultSet.getString("Author") ;
                isbn = resultSet.getString("ISBN") ;
                publisher = resultSet.getString("Publisher") ;
                category = resultSet.getString("Category") ;
                totalCopies = resultSet.getString("TotalCopies") ;
                copiesAvailable = resultSet.getString("CopiesAvailable") ;

                book = new Book(bookID, title, author, isbn, publisher, category, totalCopies, copiesAvailable) ;

                bookList.add(book) ;
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            System.out.println("\nException at DataBaseController.getAllBooks() -> \n" + e + " -> \n" + e.getLocalizedMessage()) ;
            e.printStackTrace() ;
        }

        return bookList ;
    }


    /**
     * This method is used to get the details of a specific book from the database.
     * @param fieldName the field sought.
     * @param val the value in the field.
     * @return a Book object containing the details of the book found.
     */
    public Book getBookFromAllBooks(String fieldName, String val)
    {
        Book book = null ;
        String query, bookID, title, author, isbn, publisher, category, copiesAvailable, totalCopies ;

        query = "SELECT * FROM tbl_Books WHERE " + fieldName + " = \"" + val + "\";" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next())
            {
                bookID = resultSet.getString("BookID") ;
                title = resultSet.getString("Title") ;
                author = resultSet.getString("Author") ;
                isbn = resultSet.getString("ISBN") ;
                publisher = resultSet.getString("Publisher") ;
                category = resultSet.getString("Category") ;
                totalCopies = resultSet.getString("TotalCopies") ;
                copiesAvailable = resultSet.getString("CopiesAvailable") ;

                book = new Book(bookID, title, author, isbn, publisher, category, totalCopies, copiesAvailable) ;
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.getBook() -> " + e) ;
            e.printStackTrace();
        }

        return book ;
    }


    /**
     * This method is used to get all the books from the database that has the value in the field passed in the parameters.
     * @param fieldName the name of the field.
     * @param val the value in the field.
     * @return an ObservableList<Book> containing all the books found.
     */
    public ObservableList<Book> getFromBooks(String fieldName, String val)
    {
        Book book ;
        String query, bookID, title, author, isbn, publisher, category, copiesAvailable, totalCopies ;

        bookList.clear() ;

        query = "SELECT * FROM tbl_books WHERE " + fieldName + " LIKE \"%" + val + "%\" ;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next())
            {
                bookID = resultSet.getString("BookID") ;
                title = resultSet.getString("Title") ;
                author = resultSet.getString("Author") ;
                isbn = resultSet.getString("ISBN") ;
                publisher = resultSet.getString("Publisher") ;
                category = resultSet.getString("Category") ;
                totalCopies = resultSet.getString("TotalCopies") ;
                copiesAvailable = resultSet.getString("CopiesAvailable") ;

                book = new Book(bookID, title, author, isbn, publisher, category, totalCopies, copiesAvailable) ;

                bookList.add(book) ;
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.getFromBooks() -> \n" + e) ;
            e.printStackTrace();
        }
        return bookList ;
    }


    /**
     * This method is used to get all the BookRecords with a particular value in a specific field.
     * @param fieldName the name of the field to look for.
     * @param val the value in the field.
     * @return an ObservableList of BookRecords with all the records found.
     */
    public ObservableList<BookRecord> getFromBookRecords(String fieldName, String val)
    {
        Book book ;
        String query1, query2, refNum, bookID, acctID, acctType, reservedDate, cancellationDate, issuedDate, dueDate, returnSentOnDate, returnedDate ;
        ObservableList<BookRecord> list = FXCollections.observableArrayList() ;

        query1 = "SELECT * FROM tbl_bookrecords WHERE " + fieldName + " LIKE \"%" + val + "%\" ;" ;
        query2 = "SELECT * FROM tbl_bookrecords ;" ;

        try
        {
            ResultSet resultSet = null ;
            statement = connection.createStatement() ;

            if(fieldName.equalsIgnoreCase("title") || fieldName.equalsIgnoreCase("author"))
                resultSet = statement.executeQuery(query2) ;
            else
                resultSet = statement.executeQuery(query1) ;

            while(resultSet.next())
            {
                refNum = resultSet.getString("RefNum") ;
                bookID = resultSet.getString("BookID") ;
                acctID = resultSet.getString("AccountID") ;
                acctType = resultSet.getString("AccountType") ;
                reservedDate = resultSet.getString("ReservedDate") ;
                cancellationDate = resultSet.getString("CancellationDate") ;
                issuedDate = resultSet.getString("IssuedDate") ;
                dueDate = resultSet.getString("DueDate"); ;
                returnSentOnDate = resultSet.getString("ReturnSentOnDate") ;
                returnedDate = resultSet.getString("ReturnedDate") ;

                book = getBookFromAllBooks("BookID", bookID) ;
                BookRecord bookRecord = new BookRecord( book, refNum, acctID, acctType, reservedDate, cancellationDate, issuedDate, returnSentOnDate, returnedDate, dueDate) ;


                if(fieldName.equalsIgnoreCase("title"))
                {
                    if(book.getTitle().contains(val.toUpperCase()))
                        list.add(bookRecord);
                }
                else if(fieldName.equalsIgnoreCase("author"))
                {
                    if (book.getAuthor().contains(val.toUpperCase()))
                        list.add(bookRecord);
                }
                else
                {
                    list.add(bookRecord);
                }
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch (Exception e)
        {
            System.out.println("\nExceptions in DataBaseController.getFromBookRecords() -> \n" + e) ;
            e.printStackTrace();
        }
        return list ;
    }


    /**
     * This method returns the number of books issued to a person.
     * @param id the person's account id
     * @return the number of issued books as a String
     */
    public String getOnlyIssuedBookCount(String id)
    {
        String numCount = null ;
        String query ;

        query = "SELECT COUNT(BookID) AS BookCount FROM tbl_bookrecords WHERE (AccountID = \"" + id + "\" AND ReturnedDate IS NULL);" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            if(resultSet.next())
                numCount = resultSet.getString("BookCount");

            resultSet.close() ;
            statement.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nException in DataBaseController.getOnlyIssuedBookCount() ->\n" + e) ;
            e.printStackTrace();
        }

        return numCount ;
    }


    /**
     * @return an ObservableList of BookRecords containing all the details of the books from the book records table
     */
    public ObservableList<BookRecord> getAllBookRecords()
    {
        String query, refNum, bookID, accountID, accountType, reservedDate, cancellationDate, issuedDate,  dueDate, returnSentOnDate, returnedOnDate ;
        Book book ;
        BookRecord bookRecord ;
        ObservableList<BookRecord> tempList = FXCollections.observableArrayList() ;

        query = "SELECT * FROM tbl_bookrecords ORDER BY RefNum ;" ;

        try
        {
            statement = connection.createStatement() ;

            ResultSet resultSet = statement.executeQuery(query) ;
            while(resultSet.next())
            {
                refNum = resultSet.getString("RefNum") ;
                bookID = resultSet.getString("BookID") ;
                accountID = resultSet.getString("AccountID") ;
                accountType = resultSet.getString("AccountType") ;
                reservedDate = resultSet.getString("ReservedDate") ;
                cancellationDate = resultSet.getString("CancellationDate") ;
                issuedDate = resultSet.getString("IssuedDate") ;
                dueDate = resultSet.getString("DueDate") ;
                returnSentOnDate = resultSet.getString("ReturnSentOnDate") ;
                returnedOnDate = resultSet.getString("ReturnedDate") ;

                book = this.getBookFromAllBooks("BookID", bookID) ;     // get a book object with the book id specified in the parameters
                bookRecord = new BookRecord(book, refNum, accountID, accountType, reservedDate, cancellationDate, issuedDate, dueDate, returnSentOnDate, returnedOnDate) ;

                tempList.add(bookRecord) ;
            }

            resultSet.close() ;
            statement.close() ;
        }
        catch(Exception e)
        {
            System.out.println("\nException in DataBaseController.getAllBookRecords() ->\n" + e) ;
            e.printStackTrace() ;
        }

        return tempList ;
    }


    /**
     * @return an ObservableList of BookRecords containing all the reserved books.
     */
    public ObservableList<BookRecord> getAllReservedBooks()
    {
        ObservableList<BookRecord> tempList ;

        tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getDateOfReservation() != null && bookRecord.getDateOfIssue() == null && bookRecord.getDateOfCancellation() == null)
                bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * @return an ObservableList of BookRecords containing all the books that have been issued.
     */
    public ObservableList<BookRecord> getAllIssuedBooks()
    {
        ObservableList<BookRecord> tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getDateOfIssue() != null && bookRecord.getDateOfRequestToReturn() == null)     bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * @return an ObservableList of BookRecords containing all the books that have been requested to return.
     */
    public ObservableList<BookRecord> getAllBooksRequestedToReturn()
    {
        ObservableList<BookRecord> tempList ;

        tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getDateOfRequestToReturn() != null && bookRecord.getDateOfReturn() == null)   bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * @return an ObservableList of BookRecords containing all the books that have been returned
     */
    public ObservableList<BookRecord> getAllReturnedBooks()
    {
        ObservableList<BookRecord> tempList ;

        tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getDateOfReturn() != null)   bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * This method is used to get all the books reserved by a particular person.
     * @param id the account id of the person.
     * @return an ObservableList of BookRecords containing all the books a person has reserved.
     */
    public ObservableList<BookRecord> getReservedBooks(String id)
    {
        ObservableList<BookRecord> tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord: tempList)
        {
            if(bookRecord.getAccountID().equals(id) && bookRecord.getDateOfIssue() == null)
                bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * This method is used to get all books issued to a person.
     * @param id the account id of the person.
     * @return an ObservableList of BookRecords containing all the books issued to a person
     */
    public ObservableList<BookRecord> getIssuedBooks(String id)
    {
        ObservableList<BookRecord> tempList ;

        tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getAccountID().equals(id) && bookRecord.getDateOfIssue() != null)
                bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * This method is used to get all the books returned by a person.
     * @param id the account id of the person.
     * @return an ObservableList of BookRecords containing  all the books a person has returned.
     */
    public ObservableList<BookRecord> getReturnedBooks(String id)
    {
        ObservableList<BookRecord> tempList ;

        tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getAccountID().equals(id) && bookRecord.getDateOfReturn() != null)
                bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * This method is used to get all the books a person has requested to return.
     * @param id the account id of the person.
     * @return an ObservableList of BookRecords containing all the books a person has requested to return.
     */
    public ObservableList<BookRecord> getBooksRequestedToReturn(String id)
    {
        ObservableList<BookRecord> tempList ;

        tempList = this.getAllBookRecords() ;

        bookRecordList.clear() ;

        for(BookRecord bookRecord : tempList)
        {
            if(bookRecord.getAccountID().equals(id) && bookRecord.getDateOfRequestToReturn() != null)
                bookRecordList.add(bookRecord) ;
        }

        return bookRecordList ;
    }


    /**
     * @return a randomly generated reference number
     */
    private String generateRefNum()
    {
        String ref_num ;

        do
        {
            ref_num = generateRandomLtrs(4).concat(generateRandomNumbers(6)) ;
        }
        while(checkForRefNum(ref_num)) ;

        return ref_num ;
    }


    /**
     *
     * @param length the number of letters.
     * @return a group of random letters or a single letter.
     */
    private String generateRandomLtrs(int length)
    {
        String random = "" ;
        int num, counter ;

        counter = 1 ;
        do
        {
            num = (int)(Math.random() * 91) ;

            if(num >= 65 && num <= 90)
            {
                random = random.concat(Character.toString((char)num)) ;
                counter++ ;
            }
        }
        while((num < 65 || num > 90) || counter <= length);

        return random ;
    }


    /**
     * @param length the number of digits.
     * @return a random number.
     */
    private String generateRandomNumbers(int length)
    {
        String randomNum = "" ;
        int num, index ;

        for(index = 0 ; index < length ; index++)
        {
            num = (int)(Math.random() * 10) ;
            randomNum = randomNum.concat(Integer.toString(num)) ;
        }

        return randomNum ;
    }
}
