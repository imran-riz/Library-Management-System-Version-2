package librarianapp.model ;

public class Book
{
    private String bookID, title, author, isbn, publisher, category, totalCopies, copiesAvailable ;
    private Account account ;

    public Book(String bookID, String title, String author, String isbn, String publisher, String category, String totalCopies, String copiesAvailable) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.category = category;
        this.totalCopies = totalCopies;
        this.copiesAvailable = copiesAvailable;
    }


    public String getBookID()
    {
        return bookID;
    }

    public void setBookID(String bookID)
    {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getCopiesAvailable()
    {
        return copiesAvailable;
    }

    public void setCopiesAvailable(String numOfCopies)
    {
        this.copiesAvailable = numOfCopies;
    }

    public String getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(String totalCopies)
    {
        this.totalCopies = totalCopies;
    }
}
