import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;
    //md5 hash
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {

        //set user's Name
        this.firstName = firstName;
        this.lastName = lastName;

        //hashing pin with MD5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

    }

}
