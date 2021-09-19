# Library-Management-System-Version-2

A system that allows to maintain a record of the library. This is an updated system of the version 1 that I had made earlier.

### The levels of access
In this system there are 3 levels of access:
1. Member
2. Librarian
3. Administrator

Members are allowed to reserve and return books. Once a book is reserved they will have to wait until it's issued by a librarian. During this waiting period, members are allowed 
to cancel their reservation if they choose to. Once a book has been issued, the member can return it once done. Members can view the books that they have reserved, been issued and
the one's they have submitted for return.

Librarians control the flow of books. They issue books that have been reserved by members. They can add books to the system, remove books and edit the info of books. They can see 
the books been issued to members, reserved by members and requested to return. A librarian also has access to all the book records.

Administrators can add accounts (member or librarian) to the system. They also have access to the details of all the accounts and books (not book records) in the system.


Further features to be implemented:
1. Admins being allowed to remove an account in the system.
2. Acquiring a PDF file of the details of books, book records and accounts.
3. Issuing fines if a book is not returned by the due date.
4. A way to make changes to the settings (like the number of max books that can be borrowed) without direcetly accessing the database.



### Format of the database

The database has 5 tables:
1. tbl_members (**MemberID**, FirstName, LastName, Username, Password, Salt, DateOfBirth, Email, Phone, Address, DateCreated)
2. tbl_librarians (**LibrarianID**, FirstName, LastName, Username, Password, Salt, Email, Phone, DateOfBirth, NIC, Address, DateCreated)
3. tbl_admins (**AdminID**, FirstName, LastName, Username, Password, Salt, Email, Phone, DateOfBirth, NIC, Address, DateCreated)
4. tbl_books (**BookID**, Title, Author, ISBN, Publisher, Category, TotalCopies, CopiesAvailable)
5. tbl_bookrecords (**RefNum**, AccountID, AccountType, BookID, ReservedDate, CancellationDate, IssuedDate, DueDate, ReturnSentOnDate, ReturnedDate)

Note: 
- The primary key for each table is in bold. 
- The field AccountID is not a foreign key of tbl_members, although it can be.



### Snapshots







