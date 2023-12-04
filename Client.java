import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
public class Client extends Account implements Serializable {//Class for clients

    private double balance;//Client account balance
    private ArrayList<Book> cart;//Cart for borrowing books
    private ArrayList<Book> hand;//Books already borrowed and on-hand
    private Date dueDate;//Due date for borrowed books
    public Client(String username, String password, String email, double prevBalance, ArrayList<Book> prevHand) {//Constructor to initialize values
        super(username, password, email);
        this.cart = new ArrayList<>();
        setBalance(prevBalance);
        this.hand = prevHand;
    }
    
    public void clientMenu() {//Menu for clients:
        System.out.println("Client Menu:");
        int option;
        Scanner scanner = new Scanner(System.in);
        LibraryManagement libAssist = new LibraryManagement();
        do {//Client options
            System.out.println("1- View Library");
            System.out.println("2- Borrow a Book");
            System.out.println("3- View and checkout book cart");
            System.out.println("4- View balance");
            System.out.println("5- Add balance");
            System.out.println("6- Rate Book");
            System.out.println("7- Review Book");
            System.out.println("8- Return Book");
            System.out.println("9- Log Out");

            option = scanner.nextInt();

            switch (option) {
                case 1://Ask the client how they would like the library to be sorted
                     int sort_op = 0;//Default
                     System.out.println("Please choose a parameter for ordering the books:");
                     System.out.println("1 - By Title");
                     System.out.println("2 - By Author");
                     System.out.println("3 - By Genre");
                     System.out.println("4 - By Rating");
                     System.out.println("5 - By the Number of Reviews");
                     sort_op = scanner.nextInt();
                     libAssist.chooseSort(sort_op);//Sort and show books
                     break;
                case 2://Borrow a book
                    if(hand.size() != 0){// If you already have books in hand, you can't borrow more until they are returned
                        System.out.println("Return borrowed Books first!");
                        break;
                    }
                    cart.add(libAssist.bookSelect());   // Select a book to borrow and add it to the cart
                    break;
                case 3:// If you already have books in hand, you can't borrow more until they are returned
                    if(hand.size() != 0){
                        System.out.println("Return borrowed Books first!");
                        break;
                    }
                    System.out.println("***View and Checkout Cart***");
                    System.out.println("Your balance: " + balance);
                    float total_price = 0;
                    for (Book book : cart) {    // Display books in cart
                        System.out.println("Title:" + book.getTitle());
                        System.out.println("Author:" + book.getAuthor());
                        System.out.println("Genre:" + book.getGenre());
                        System.out.println("Description:"+book.getDescription());
                        System.out.println("Rating: " + book.getRating());
                        System.out.println("Price: $0.25");
                        total_price += 0.25;	//Standard price for borrowing a book
                    }
                    System.out.println("Total: $" + total_price);    // Display total price
                    System.out.println("Checkout (Y/N) :");    // Ask if the customer wants to check out
                    String check_out = scanner.next();
                    if (total_price <= balance) {    // Check if balance is sufficient
                        balance -= total_price;    // Update Balance
                        for(Book bk : cart){
                            setDueDateForBook(bk);//Set due dates for the books
                            hand.add(bk);//Add books to books in hand
                            cart.remove(bk);//REmove book from cart
                        }
                        System.out.println("Thank you for visiting the IUPUI Library!");
                        System.out.println("Your New Balance: $" + balance);
                    } else {
                        System.out.println("Your Current Balance is insufficient!");
                    }
                    break;
                case 4://Print current balance
                    System.out.println("Your Current Balance: " + balance);    // Display Balance
                    break;
                case 5://Update balance
		    double update;
		    double card;
		    int cvv;
		    String date_of_expiry;
		    System.out.println("Your Current Balance: " + balance);    // Display Balance
		    System.out.println("Please enter the amount you would like to recharge your balance with:");
		    update = scanner.nextDouble();//Recharge amount
    
		    System.out.println("Enter Card Number for payment to be made:");
		    card = scanner.nextDouble();//Card number
	    
		    System.out.println("Enter Date of Expiry (MM/YY):");//Date of expiry
		    date_of_expiry = scanner.next();
	            //Date format: MM/YY:
		    String[] dateParts = date_of_expiry.split("/");
		    int month = Integer.parseInt(dateParts[0]);
		    int year = Integer.parseInt(dateParts[1]);
    
		    // Check if the entered date has already passed
		    Calendar currentCalendar = Calendar.getInstance();
		    Calendar expiryCalendar = Calendar.getInstance();
		    expiryCalendar.set(Calendar.MONTH, month - 1);  // Month is 0-based in Calendar
		    expiryCalendar.set(Calendar.YEAR, 2000 + year);  // Assuming the year is in the format YY
    
		    if (currentCalendar.after(expiryCalendar)) {
		        System.out.println("Invalid card. The entered date of expiry has already passed.");
		    } else {
		        // Proceed with the transaction
		        System.out.println("Enter CVV:");
		        cvv = scanner.nextInt();
		        balance += update;
		        System.out.println("Your Updated Balance: " + balance);
		    }
		    break;
                case 6://Rate a book
        		float your_rating, new_rating;
        		String bookTitle;
        		scanner.nextLine(); // Consume the newline character
        		System.out.println("***Rate A Book***");
	        	System.out.println("Enter the Title of the Book you want to Rate");
	        	bookTitle = scanner.nextLine();
	        	Book b = libAssist.findMatchingBook(bookTitle);//Find book matching the title
	        	if (b != null) {//If the book is found,
	        	    System.out.println("Enter your rating of the book from 1 to 5:");
	        	    your_rating = scanner.nextFloat();
	        	    new_rating = (b.getRating() + your_rating) / 2;//Average the new rating with the existing rating
	        	    b.setRating(new_rating);
	        	} else {
	        	    System.out.println("Book not found!");
	        	}
	        	break;

	    	case 7://Add a review to a book
	        	scanner.nextLine();
	        	String review;
	        	String revbookTitle;
	        	System.out.println("***Add A Review***");
	        	System.out.println("Enter the Title of the Book you want to Review");
	        	revbookTitle = scanner.nextLine();
	        	Book revb = libAssist.findMatchingBook(revbookTitle);//Find book matching the title
	        	if (revb != null) {//If the book is found,
	        	    System.out.println("Enter your review of the book :");
	        	    review = scanner.nextLine();
	        	    revb.addReview(review);//Add the review
	        	} else {
	        	    System.out.println("Book not found!");
	        	}
	        	break;
           	case 8://Return a book 
		    double latefee;
		    scanner.nextLine(); // Consume the newline character
		    String retBookTitle;
    		    System.out.println("***Return Book***");
		    System.out.println("Enter the Book to be returned:");
		    retBookTitle = scanner.nextLine();
    
    		    Book bookToRemove = null;

    		    for (Book ret_b : hand) {//Check borrowed books for the book to be returned
        		if (ret_b.getTitle().equals(retBookTitle)) {
            			bookToRemove = ret_b;
            			break;  //exit the loop after finding the book
        		}
    		    }

		    if (bookToRemove != null) {//If the book is found on hand,
			        libAssist.bookReturn(retBookTitle);//Return the book
			        hand.remove(bookToRemove);//REmove from books on hand
			        System.out.println(retBookTitle + " successfully returned!");
			        latefee = calculateLateFee();//Calculate a late fee. 0.00 if returned on time
			        balance -= latefee;//Deduct late fee from balance
			        System.out.println("Late Fee of "+latefee+" deductued from your balance!");
		    } else {
			        System.out.println("Book not found in your borrowed books!");
		    }
		    break;
           	case 9://Log out
                    	System.out.println("Logged out as Client");    // Log Out
                    	break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (option != 9);
    }
    private void setDueDateForBook(Book book) {
        // Set the due date for the borrowed book
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        dueDate = calendar.getTime();

        // Add the book to the user's cart with due date
        cart.add(book);
        System.out.println("Book '" + book.getTitle() + "' has been borrowed. Due date: " + dueDate);
    }
    private double calculateLateFee() {//Calculate the late fee
        Date currentDate = new Date();
        long daysLate = Math.max(0, (currentDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000));//Calculate the number of days the book was kept beyond the due date
        double lateFeeRate = 0.12; //Late fee per day
        return daysLate * lateFeeRate;
    }
    //Setters and Getters:
    public double getBalance() {
     return balance;
    }
    public void setBalance(double balance) {
     this.balance = balance;
    }
    public ArrayList<Book> getHand(){
      return hand;
    }
}

