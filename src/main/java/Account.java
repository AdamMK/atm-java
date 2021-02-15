import java.util.ArrayList;

public class Account {

    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank){

        //set acc name and holder
        this.name = name;
        this.holder = holder;

        this.uuid = theBank.getNewAccountUUID();

        //init transactions
        this.transactions = new ArrayList<>();

    }

    public String getUUID() {
        return this.uuid;
    }
}
