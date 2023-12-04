import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;
public class Admin extends Account implements Serializable {	//Class for Admin Accounts

    public Admin(String username, String password, String email) {//Constructor for initialization
        super(username, password, email);//USe constructor of superclass Account to initialize
    }

    public void adminMenu() {//FUnction to display the menu for admins:
        System.out.println("Admin Menu:");
        int option;
        Scanner scanner = new Scanner(System.in);

        do {	//Options for the admin:
            System.out.println("1- Create a customer account");
            System.out.println("2- Remove a customer account");
            System.out.println("3- Add an Admin");
            System.out.println("4- View inventory");
            System.out.println("5- Add a book");
            System.out.println("6- Remove a book");
            System.out.println("7- Restock a book");
            System.out.println("8- Send an email notification to all clients");
            System.out.println("9- Logout");

            option = scanner.nextInt();

            AccountManager administrator = new AccountManager();	//Object of the AccountManager class
            LibraryManagement lib_admin = new LibraryManagement();	//Object of the LibraryManagement class
	    
            switch (option) {
                case 1:
                    administrator.register();   // Create a customer account
                    break;
                case 2:
                    administrator.removeAccount(1);    // Remove a customer account
                    break;
                case 3:
                    administrator.registerAdmin(1);    // Register another admin
                    break;
                case 4:
                    lib_admin.viewInventory(1);    // View the books in the library
                    break;
                case 5:
                    lib_admin.addBook(1);    // Add a book to the library
                    break;
                case 6:
                    lib_admin.removeBook(1);    // Remove a book from the library
                    break;
                case 7:
                    lib_admin.restockBook(1);    // Restock a book in the library
                    break;
                case 8:	//Send a broadcast message to all customers
    		    String em_password;
                    String message;
                    String subject;
                    ArrayList<Client> broadcastList;

                    System.out.println("Enter your Email Password");
                    em_password = scanner.next();
//Password for email account authentication. For the purpose of testing, I have the dummy account siddharthmurali786@gmail.com set up with dummy password ppxjxbmvjdzxpnna. This can be used for testing
                    // Consume the newline character, since we used next() and not nextLine()
                    scanner.nextLine();

                    SimpleEmailNotifier notifier = new SimpleEmailNotifier(this.getEmail(), em_password);//Initialize an email notification with the admin email and password

    		    System.out.println("Enter your Notification Subject:");//Email subject
		    subject = scanner.nextLine();
	
		    System.out.println("Enter your Notification Message:");//Email body
		    message = scanner.nextLine();
	
		    broadcastList = administrator.getClientList(1);//Obtain list of clients

		    notifier.sendNotification(subject, message, broadcastList);//Send email notifications
		    break;
                case 9:
                    System.out.println("Logged out as Admin");    // Log out
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (option != 9);
    }
}

