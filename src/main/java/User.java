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
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //UUID
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<>();

        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);

    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),
                this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName(){
        return this.firstName;
    }

    //print summaries of all accounts for this user
    public void printAccountsSummary() {

        System.out.printf("\n\n%s accounts summary\n", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s\n", a+1,
                this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccount(){
        return this.accounts.size();
    }

    public void printAcctTransactionHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int accIdx) {
        return this.accounts.get(accIdx).getBalance();
    }

    //get UUID for particular account
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }

}
