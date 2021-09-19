package librarianapp.resources ;


public class Encrypter
{
    private String salt, textToEncrypt, encryptedText ;

    /**
     * @param textToEncrypt the text to be encrypted
     * @param salt the salt to be used when encrypting the text to be encrypted
     * @return the encrypted text
     */
    public String encrypt(String textToEncrypt, String salt)
    {
        this.textToEncrypt = textToEncrypt ;
        this.salt = salt ;

        this.encryptedText = encrypt() ;

        return encryptedText;
    }


    /**
     * The salt used to encrypt this text will be generated randomly and can be accessed using the getSalt() method
     * @param s the text to be encrypted
     * @return encrypted text
     */
    public String encrypt(String s)
    {
        this.salt = generateSalt() ;
        this.textToEncrypt = s ;

        this.encryptedText = encrypt() ;

        return encryptedText ;
    }


    /**
     * Encrypts the values held in the private variables 'textToEncrypt' and 'salt'.
     * @return the encrypted piece of text.
     */
    private String encrypt()
    {
        int asciiCode ;
        double n ;
        StringBuilder encryptedStringBuilder = new StringBuilder() ;
        StringBuilder tempStringBuilder = new StringBuilder() ;

        tempStringBuilder.append(this.textToEncrypt) ;
        tempStringBuilder.append(this.salt) ;


        for(int index = 0 ; index < tempStringBuilder.length() ; index++)
        {
            asciiCode = tempStringBuilder.charAt(index) ;

            if(asciiCode >= 97 && asciiCode <= 122)         // if it's a lowercase letter
            {
                n = asciiCode + 16 ;

                if(n > 122) n = 97 + (n - 122) ;
            }
            else if(asciiCode >= 65 && asciiCode <= 90)     // if it's an uppercase letter
            {
                n = asciiCode + 8 ;

                if(n > 90)  n = 65 + (n - 90) ;
            }
            else                                            // if it's a number
            {
                n = asciiCode + 4 ;

                if(n > 57)  n = 48 + (n - 57) ;
            }

            encryptedStringBuilder.append((char) n) ;
        }

        return encryptedStringBuilder.toString() ;
    }

    public String getSalt()
    {
        return this.salt ;
    }


    public String getEncryptedText()
    {
        return this.encryptedText;
    }

    public String getTextToEncrypt()
    {
        return this.textToEncrypt ;
    }

    /**
     * @return a randomly generated 15 character string.
     */
    private String generateSalt()
    {
        StringBuilder stringBuilder = new StringBuilder() ;
        char x ;
        int n ;
        int lengthOfSalt = 15 ;

        while(stringBuilder.length() < lengthOfSalt)
        {
            n = (int) (Math.random() * 5000);

            if (n % 5 == 0 && n % 3 == 0)       // then it's a lowercase letter
            {
                x = (char) (int) (Math.random() * (122 - 97) + 97) ;
                stringBuilder.append(x) ;
            }
            else if (n % 5 == 0)                // then it's an uppercase letter
            {
                x = (char) (int) (Math.random() * (90 - 65) + 65) ;
                stringBuilder.append(x) ;
            }
            else if(n % 3 == 0)                 // then it's a digit
            {
                x = (char) (int)(Math.random() * (57 - 48) + 48) ;
                stringBuilder.append(x) ;
            }
        }

        return stringBuilder.toString() ;
    }
}
