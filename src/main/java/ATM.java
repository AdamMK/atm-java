import java.awt.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        //init
        Scanner sc = new Scanner(System.in);

        //Bank
        Bank theBank = new Bank("Bank of Earth");

        //add user, which will create saving account
        User aUser = theBank.addUser("John", "Doe", "1234");

        //add checking account
        Account newAccount = new Account("Current", aUser, theBank);
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

    /**
     *
     * @param theBank
     * @param sc
     * @return
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc ){

        String userID;
        String pin;
        User authUser;

        Console console = System.console();

        //prompt user for ID/Pin until correct
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            pin = new String(console.readPassword("Enter pin: "));
            //System.out.printf("Enter pin: ");
            //pin = sc.nextLine();

            //try to get user object corresponding to Id/pin
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. Please try again.");
            }
            //loop until login ok
        } while(authUser == null);

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {

        //print a summary of users account
        theUser.printAccountsSummary();

        //init
        int choice;

        //user menu
        do {
            System.out.printf("Welcome %s, choose option.\n", theUser.getFirstName());
            System.out.println("  1) Show transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while(choice < 1 || choice > 5);

        //process choice
        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                System.exit(0);
        }

        //redisplay menu unless user quits
        //not wrapping all thing in loop but recursively call this function
        if(choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    /**
     *
     * @param theUser
     * @param sc
     */
    public static void showTransactionHistory(User theUser, Scanner sc) {

        int theAcct;

        //which account to look
        do{
            System.out.printf("Enter the number (1-%d) of the account" +
                "of which transactions you want to see: ", theUser.numAccount());
            theAcct = sc.nextInt()-1;
            if(theAcct < 0 || theAcct >= theUser.numAccount()) {
                System.out.println("Invalid Account. Please try again.");
            }
        }while (theAcct < 0 || theAcct >= theUser.numAccount());

        //print transaction
        theUser.printAcctTransactionHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner sc) {

        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //trans from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                "to transfer from: ", theUser.numAccount());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(fromAcct);

        //get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                "to transfer to: ", theUser.numAccount());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccount());

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max £%.02f): £", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greather than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greather than balance of £%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        //finally do the transfer
        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
            "Transfer to account %s", theUser.getAcctUUID(toAcct)
        ));

        theUser.addAcctTransaction(toAcct, amount, String.format(
            "Transfer from account %s", theUser.getAcctUUID(fromAcct)
        ));
    }

    public static void withdrawFunds(User theUser, Scanner sc) {

        //inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //widthdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                "to withdraw from: ", theUser.numAccount());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(fromAcct);

        if(acctBal == 0) {
            System.out.println("You don't have any money in this account");
            return;
        }

        //get the amount to widthdraw

        do {
            System.out.printf("Enter the amount to withdraw (max £%.02f): £", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greather than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greather than balance of £%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        //get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        //do withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);

    }

    public static void depositFunds(User theUser, Scanner sc) {

        //inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //trans from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                "to deposit in: ", theUser.numAccount());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()) {
                System.out.println("Invalid Account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(toAcct);

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max £%.02f): £", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while (amount < 0 );


        sc.nextLine();

        //get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        //do withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

}
