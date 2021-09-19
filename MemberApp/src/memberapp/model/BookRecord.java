package memberapp.model;

public class BookRecord
{
    private Book book ;
    private String refNum, accountID, accountType ;
    private String dateOfReservation, dateOfCancellation, dateOfIssue, dateDue, dateOfRequestToReturn, dateOfReturn ;

    private String bookID, title, author ;

    public BookRecord(Book book, String refNum, String accountID, String accountType, String dateOfReservation, String dateOfCancellation, String dateOfIssue, String dateDue, String dateOfRequestToReturn, String dateOfReturn)
    {
        this.book = book;
        this.refNum = refNum ;
        this.accountID = accountID ;
        this.accountType = accountType ;
        this.dateOfReservation = dateOfReservation;
        this.dateOfCancellation = dateOfCancellation ;
        this.dateOfIssue = dateOfIssue;
        this.dateDue = dateDue;
        this.dateOfRequestToReturn = dateOfRequestToReturn;
        this.dateOfReturn = dateOfReturn;

        this.bookID = this.book.getBookID() ;
        this.title = this.book.getTitle() ;
        this.author = this.book.getAuthor() ;
    }

    public BookRecord(Book book, String refNum, String accountID, String accountType)
    {
        this.book = book ;
        this.refNum = refNum ;
        this.accountID = accountID ;
        this.accountType = accountType ;

        this.bookID = this.book.getBookID() ;
        this.title = this.book.getTitle() ;
        this.author = this.book.getAuthor() ;
    }

    public BookRecord(Book book, String accountID, String acctType) {
        this.book = book ;
        this.accountID = accountID ;
        this.accountType = acctType ;
    }

    public String getRefNum() { return refNum; }

    public void setRefNum(String refNum) { this.refNum = refNum; }

    public String getAccountID() {  return accountID; }

    public void setAccountID(String accountID) { this.accountID = accountID; }

    public String getAccountType() { return accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getDateOfReservation() {
        return dateOfReservation;
    }

    public String getDateOfCancellation() {
        return dateOfCancellation;
    }

    public void setDateOfCancellation(String dateOfCancellation) {
        this.dateOfCancellation = dateOfCancellation;
    }

    public void setDateOfReservation(String dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateOfRequestToReturn() {
        return dateOfRequestToReturn;
    }

    public void setDateOfRequestToReturn(String dateOfRequestToReturn)
    {
        this.dateOfRequestToReturn = dateOfRequestToReturn;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
