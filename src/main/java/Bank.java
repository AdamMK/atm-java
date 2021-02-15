import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;


    public String getNewUserUUID(){

        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //cont looping until unique ID
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //check to make sure it's unique
            nonUnique = false;
            for (User u: this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return uuid;
    }

    public String getNewAccountUUID(){
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        //continue looping until unique ID
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //check to make sure it's unique
            nonUnique = false;
            for (Account a: this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return uuid;
    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName, String lastName, String pin) {

        //create a new User object and add it to our list (using User constructor)
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //create savings account for the user and add to User and Bank account lists
        Account newAccount = new Account("Saving", newUser, this);
        //add to holder and bank list
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    //login for bank
    
}
