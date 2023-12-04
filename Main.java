import java.util.Scanner;

public class Main {//Main function

    public static void main(String[] args) {
        int option;
        Scanner scanner = new Scanner(System.in);
        //Initial screen:
        System.out.println("********* Welcome to the IUPUI Library *********");
        System.out.println("1 - Login as Client");
        System.out.println("2 - Register New Client");
        System.out.println("3 - Login as Admin");
        System.out.println("4 - Register New Admin");
        option = scanner.nextInt(); // User input of option

        AccountManager accountManager = new AccountManager();

        if (option == 1) {
            accountManager.clientLogin(); // Login as client
        } else if (option == 2) {
            accountManager.register(); // Register
        } else if (option == 3) {
            accountManager.adminLogin(); // Login as Admin
        }
        else if(option == 4){
            accountManager.registerAdmin(0);//Register an admin. Only allowed if no other admins have been registered
        }
        else{
            System.out.println("Invalid Option!");
        }
    }
}

