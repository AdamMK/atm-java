import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        //init
        Scanner sc = new Scanner(System.in);

        //Bank
        Bank theBank = new Bank("Bank");

        //add user, which will create saving account
        User aUser = theBank.addUser("John", "Doe", "1234");

        //add checking account
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            //stay in the login prompt until successful login
            //passing scanner for input
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //until user quits
            //passing scanner for input
            ATM.printUserMenu(curUser, sc);
        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc ){

        String userID;
        String pin;
        User authUser;

        //prompt user for ID/Pin until correct
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID:");
        } while ()
    }
}
