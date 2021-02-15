import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }

    public Transaction(double amount, String memo, Account inAccount){

        //call to two-arg constructor first
        this(amount, inAccount);

        //set the memo
        this.memo = memo;
    }

}
