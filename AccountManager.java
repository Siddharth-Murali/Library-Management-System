import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class AccountManager implements Serializable{

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Client> clientAccounts = new ArrayList<>();	//Arraylist storing the client accounts as an arraylist
    private ArrayList<Admin> adminAccounts = new ArrayList<>();		//Arraylist storing the admin accounts as an arraylist
    public AccountManager() {		//Constructor first loads the saved client and admin accounts
        loadClientAccounts();
        loadAdminAccounts();
    }

    public void register() {	//Function to register a new client
        System.out.println("New User Registration:");
        String currUsername;
        String currPassword;
        String currEmail;
        System.out.println("Enter Username:");
        currUsername = scanner.next();//Input of username

        while (isUsernameTaken(currUsername)) {	//Check if username is already in use
            System.out.println("Username Already Exists! Try Again");
            currUsername = scanner.next();
        }

        System.out.println("Enter Password:");//Input of Password
        currPassword = scanner.next();
        System.out.println("Enter email ID:");//Input of email ID
        currEmail = scanner.next();
        ArrayList <Book> newList = new ArrayList<>();//Initialize an emty arraylist for new users. This arraylist represents the books currently on hand (borrowed) by a user
        Client newAccount = new Client(currUsername, currPassword, currEmail, 0.0, newList);	//No previous Balance for a new user, so initialize balance to 0
        clientAccounts.add(newAccount);//Add the new account to the client accounts list
        saveClientAccounts();//Save the client accounts list
        System.out.println("Registration successful!");
    }

    public void clientLogin() {//Function for a previously registered user to log-in
        System.out.println("Log-In");
        System.out.println("Enter your Username:");//Scan username
        String username = scanner.next();
        System.out.println("Enter your Password:");//Scan Password
        String password = scanner.next();

        Client matchingAccount = findMatchingAccount(username, password);//Check for an existing client account with these credentials

        if (matchingAccount != null) {//If the account is found,
            System.out.println("Login successful!");
            //Get previously stored data on the user and pass it into the client constructor. Previously borrowed books and previous balance are used.
            Client new_client = new Client(username, password, matchingAccount.getEmail(), matchingAccount.getBalance(), matchingAccount.getHand());
            new_client.clientMenu();//On successful log-in, open the client menu
            clientAccounts.remove(matchingAccount);//Since you've made changes to the account, remove the original from the array list
            clientAccounts.add(new_client);//Add the modified account to the arraylist
            saveClientAccounts();//Save the arraylist
        } else {
            System.out.println("Invalid credentials. Login failed.");//If no existing account credentials match the ones entered, the log-in fails
        }
    }

    public void adminLogin() {//FUnction for an administrator to log-in to their account
        System.out.println("Admin Log-In");//Obtain the username, password and email ID to check credentials
        System.out.println("Enter your Username:");
        String username = scanner.next();
        System.out.println("Enter your Password:");
        String password = scanner.next();
        System.out.println("Enter your Email ID:");
        String email = scanner.next();
        Account adminMatch = findMatchingAdmin(username, password);//Check existing admin accounts with credentials

        if (adminMatch != null) {//If a match is found, the log-in is successful
            System.out.println("Admin Login successful!");
            // Pass username, password and email into the admin constructor
            Admin new_admin = new Admin(username, password, email);
            new_admin.adminMenu();
        } else {
            System.out.println("Invalid credentials. Login failed.");//If no match is found for the credentials, log-in fails
        }
    }

    private boolean isUsernameTaken(String username) {	//Function to check if a username is already taken
        for (Client account : clientAccounts) {//Check client accounts for username
            if (account.getUsername().equals(username)) {
                return true;//If username is found
            }
        }

        for (Admin adminAccount : adminAccounts) {//Check admin account for username
            if (adminAccount.getUsername().equals(username)) {
                return true;//If username is found
            }
        }

        return false;
    }

    private Client findMatchingAccount(String username, String password) {
        for (Client account : clientAccounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;//If username is found
            }
        }
        return null;
    }

    private Admin findMatchingAdmin(String username, String password) {	//Given credentials, check the list of pre-existing admins for a match
        for (Admin account : adminAccounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;//If a match is found, return the matching account
            }
        }
        return null;//If no match is found, no account is returned
    }

    private void loadClientAccounts() {//Function to load client accounts from previously serialized file
	    try {
	        FileInputStream fileInput = new FileInputStream("Client_Accounts.ser");
	        ObjectInputStream in = new ObjectInputStream(fileInput);
	        clientAccounts = (ArrayList<Client>) in.readObject();	//Client accounts are stored in the clientAccounts arrayList
	        in.close();
	        fileInput.close();
	    } catch (IOException | ClassNotFoundException ex) {
	        // Handle exceptions
	    }
    }


    private void saveClientAccounts() {//Function to save client accounts to file using serialization
        try {
            FileOutputStream fileOutput = new FileOutputStream("Client_Accounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOutput);
            out.writeObject(clientAccounts);
            out.close();
            fileOutput.close();
        } catch (IOException ex) {
            // Handle exceptions
        }
    }

    public void removeAccount(int adminprivilege) {	//Function to remove a client account if adminprivilege is granted (Can only be granted in Admin class)
        if (adminprivilege != 1) {//If this function is not accessed bt an admin:
            System.out.println("An Account can only be removed by an Admin. Inactive Accounts will be removed automatically after 27 days of inactivity.");
            return;
        } else {//If accessed by an admin:
            System.out.println("***Remove Member Account***");
            String remUsername;//Username of account to be removed
            System.out.println("Enter username: ");
            remUsername = scanner.next();
            while (!isUsernameTaken(remUsername)) {//If the username can't be found amongst existing accounts
                System.out.println("Error: This username does not exist");
                System.out.println("Enter username: ");
                remUsername = scanner.next();
            }
            for (Client account : clientAccounts) { //Search through client accounts until a match is found
                if (account.getUsername().equals(remUsername)) {
                    clientAccounts.remove(account);//Remove the account
                    System.out.println("Success: customer account has been removed");
                    return;
                }
            }
        }
    }

    public void registerAdmin(int adminprivilege) {	//Function which allows one admin to add another or allows for the first admin to register an account
        if (adminAccounts.size() == 0) {//If there are no other admins,
            System.out.println("Initial Admin registration");
            String adminUsername;
            String adminPassword;
	    String adminEmail;
            System.out.println("Enter Admin Username:");
            adminUsername = scanner.next();
	    //Check if username is taken
            while (isUsernameTaken(adminUsername)) {
                System.out.println("Username Already Exists! Try Again");
                adminUsername = scanner.next();
            }
            //Enter password and email ID:
            System.out.println("Enter Admin Password:");
            adminPassword = scanner.next();
            System.out.println("Enter Admin Email ID:");
            adminEmail = scanner.next();
	    Admin newAdminAccount = new Admin(adminUsername, adminPassword, adminEmail); //Use constructor to initialize account
            adminAccounts.add(newAdminAccount);//Add account to Admin Accounts
            saveAdminAccounts(); // Save admin accounts after registration
            System.out.println("Admin Registration successful!");
        } else if (adminprivilege != 1) {//If not during the initial admin registration and not authorized with adminprivilege,
            System.out.println("An Admin can only be added by another Admin.");
            return;
        } else {//If admins already exist, then they can add more admins:
            System.out.println("Admin Registration:");
            String adminUsername;
            String adminPassword;
            String adminEmail;
            System.out.println("Enter Admin Username:");
            adminUsername = scanner.next();
	    //Check if username is taken
            while (isUsernameTaken(adminUsername)) {
                System.out.println("Username Already Exists! Try Again");
                adminUsername = scanner.next();
            }
	    //Enter password and email ID:
            System.out.println("Enter Admin Password:");
            adminPassword = scanner.next();
            System.out.println("Enter Admin Email ID:");
            adminEmail = scanner.next();

            Admin newAdminAccount = new Admin(adminUsername, adminPassword, adminEmail); //Use constructor to initialize account
            adminAccounts.add(newAdminAccount); //Add account to Admin Accounts
            saveAdminAccounts(); // Save admin accounts after registration
            System.out.println("Admin Registration successful!");
        }
    }

    private void loadAdminAccounts() {//Function to load pre-existing admin accounts
        try {
            FileInputStream fileInput = new FileInputStream("Admin_Accounts.ser");
            ObjectInputStream in = new ObjectInputStream(fileInput);
            adminAccounts = (ArrayList<Admin>) in.readObject();	//Read from the file into the adminAccounts ArrayList
            in.close();
            fileInput.close();
        } catch (IOException | ClassNotFoundException ex) {
            // Handle exceptions
        }
    }

    private void saveAdminAccounts() { //Function to save admin accounts to file using serialization
        try {
            FileOutputStream fileOutput = new FileOutputStream("Admin_Accounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOutput);
            out.writeObject(adminAccounts);
            out.close();
            fileOutput.close();
        } catch (IOException ex) {
            // Handle exceptions
        }
    }
    public void clientSaveRequest(Client c){	//FUnction to acknowledge a request from a client to save changes made to that client account
    	Client c_old = findMatchingAccount(c.getUsername(),c.getPassword());//Match the account to a pre-existing account
    	clientAccounts.remove(c_old);//Remove the old account
    	clientAccounts.add(c);//Add the modified account
    	saveClientAccounts();//Save the clientAccounts
    }
    public ArrayList<Client> getClientList(int adminprivilege){//Function that returns the list of clients to an administrator if adminprivilege is granted
        if(adminprivilege == 1){
        	return clientAccounts;//Returns the arraylist of clientaccounts
        }
        else{
        	System.out.println("Access Denied");//Access denied if anyone else tries to access it
                return null;
        }
    }
}

