package adminapp.model ;

public class Account
{
    private String acctType;
    private String salt ;
    private String id, firstName, lastName, username, password, email ;
    private String numOfBooksBorrowed, dateOfBirth, phone, address, nicNumber, dateCreated ;


    public Account(String acctType, String id, String firstName, String lastName, String username, String password, String dateCreated)
    {
        this.acctType = acctType ;
        this.id = id ;
        this.firstName = firstName ;
        this.lastName = lastName ;
        this.username = username ;
        this.password = password ;
        this.dateCreated = dateCreated ;
    }

    public Account() {    }


    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getNicNumber()
    {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber)
    {
        this.nicNumber = nicNumber;
    }

    public String getNumOfBooksBorrowed()
    {
        return numOfBooksBorrowed;
    }

    public void setNumOfBooksBorrowed(String numOfBooksBorrowed)
    {
        this.numOfBooksBorrowed = numOfBooksBorrowed;
    }
}
