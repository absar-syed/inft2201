package syeda;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PasswordHasher
{
    /**
     * String that holds the password to be hashed
     */
    private String password;

    /**
     * getter for password variable
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password variable
     * @param password user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Constructor that sets the password variable with the user's password
     * @param password user's password
     */
    public PasswordHasher(String password) {
        setPassword(password);
    }

    /**
     * Method that hashes user's inputted password with SHA256
     * @return Hashed password
     */
    public String Hash() {

        String passwordToHash = getPassword();
        String generatedPassword = null;

        try
        {
            // Create MessageDigest instance for SHA256
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            sha256.update(passwordToHash.getBytes());

            // Get the hash's bytes
            byte[] bytes = sha256.digest();

            //bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;

    }

    public static void main(String[] args)
    {

       PasswordHasher testPassword = new PasswordHasher("password");

       System.out.println(testPassword.Hash());

    }
}