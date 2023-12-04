import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagement {//Class managing the library

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Book> inventory = new ArrayList<>();

    public LibraryManagement() {//Constructor loads previously stored books
        loadInventory();
    }

    public void addBook(int admin_privileges) {//Function to add a book. ONly available for admins
        if (admin_privileges == 1) {
            System.out.println("***Add a Book***");
            String bookTitle;
            int bookStock;
            float bookRating;
            String bookAuthor;
            String bookGenre;
            String bookDesc;
            System.out.println("Enter new book title:");
            bookTitle = scanner.nextLine();
            while (doesBookExist(bookTitle)) {//Check if the book already exists in the library
                System.out.println("Error: Book already exists. Try Again");
                bookTitle = scanner.nextLine();
            }
            //Enter book details:
            System.out.println("Enter Author's Name:");
            bookAuthor = scanner.nextLine();
            System.out.println("Enter Book Genre:");
            bookGenre = scanner.nextLine();
            System.out.println("Enter Book Description:");
            bookDesc = scanner.nextLine();
            System.out.println("Enter stock:");
            bookStock = scanner.nextInt();
            System.out.print("Enter rating: ");
            bookRating = scanner.nextFloat();
            Book newBook = new Book(bookTitle, bookAuthor, bookGenre, bookDesc, bookStock, bookRating);//Initialize the book
            inventory.add(newBook);//Add the book to the library
            saveInventory();//Save the library
            System.out.println("Success: book has been added to the library");
        } else {
            System.out.println("Error: Only an admin can add a book");
        }
    }

    private boolean doesBookExist(String bookTitle) {//Function to check if the book already exists
        for (Book book : inventory) {
            if (book.getTitle().equals(bookTitle)) {
                return true;
            }
        }
        return false;
    }

    public Book findMatchingBook(String bookTitle) {//Find a match for the title of a book in the library
        for (Book book : inventory) {
            if (book.getTitle().equals(bookTitle)) {
                return book;//Return the book which matches
            }
        }
        return null;//If no book matches
    }

    private void loadInventory() {//Function to load the previously stored library from file
        try {
            FileInputStream fileInput = new FileInputStream("Library_Inventory.ser");
            ObjectInputStream in = new ObjectInputStream(fileInput);
            inventory = (ArrayList<Book>) in.readObject();//Store in the inventory arrayList
            in.close();
            fileInput.close();
        } catch (IOException | ClassNotFoundException ex) {
            // Handle exceptions
        }
    }

    private void saveInventory() {//Function to save the inventory to file using serialization
        try {
            FileOutputStream fileOutput = new FileOutputStream("Library_Inventory.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOutput);
            out.writeObject(inventory);
            out.close();
            fileOutput.close();
            loadInventory();
        } catch (IOException ex) {
            // Handle exceptions
        }
    }

    public void viewInventory(int adminprivilege) {//Function to view all the books of the library accessible only to the admin
        if (adminprivilege == 1) {
            System.out.println("***View Inventory***");
            for (Book book : inventory) {
                System.out.println("Title:" + book.getTitle());
                System.out.println("Author:" + book.getAuthor());
                System.out.println("Genre:" + book.getGenre());
            	System.out.println("Description:"+book.getDescription());
            	System.out.println("Stock: " + book.getStock());
            	System.out.println("Rating: " + book.getRating());
            	ArrayList<String> bookReviews = book.getReviews();
            	for(String rev : bookReviews){
            	    System.out.println(rev);
            	}
            }
        } else {
            System.out.println("Error: Only an admin can view the inventory");
        }
    }

    public void removeBook(int adminprivilege) {//Function allowing an admin to remove a book
        if (adminprivilege != 1) {
            System.out.println("Error: A Book can only be removed by an Admin.");
            return;
        } else {
            System.out.println("***Remove Book***");
            String remBookTitle;
            System.out.println("Enter book title: ");
            remBookTitle = scanner.nextLine();
            while (!doesBookExist(remBookTitle)) {//Check if the book to be removed exists in the library
                System.out.println("Error: This book does not exist");
                System.out.println("Enter book title: ");
                remBookTitle = scanner.next();
            }
            for (Book book : inventory) {//Iterate through the library until the book is found
                if (book.getTitle().equals(remBookTitle)) {
                    inventory.remove(book);//Remove the book
                    saveInventory();//Save changes to the library
                    System.out.println("Success: book has been removed");
                    return;
                }
            }
        }
    }

    public void restockBook(int adminprivilege) {//Function allowing an admin to restock a book
        if (adminprivilege != 1) {
            System.out.println("Error: A Book can only be restocked by an Admin.");
            return;
        } else {
            System.out.println("***Restock Book***");
            String resBookTitle;
            System.out.println("Enter book title: ");
            resBookTitle = scanner.nextLine();
            while (!doesBookExist(resBookTitle)) {//Check if book exists in the library
                System.out.println("Error: This book does not exist");
                System.out.println("Enter book title: ");
                resBookTitle = scanner.next();
            }
            Book b = findMatchingBook(resBookTitle);//Match the book to a book in the library
            int curr_stock = b.getStock();
            float curr_rating = b.getRating();
            System.out.println("Current Stock: " + curr_stock);
            System.out.println("Enter new Stock:");
            int newstock = scanner.nextInt();//New stock
            //Update the library:
            Book new_b = new Book(b.getTitle(), b.getAuthor(), b.getGenre(), b.getDescription(), newstock, b.getRating());
            inventory.remove(b);
            inventory.add(new_b);
            saveInventory();
        }
    }

    public Book bookSelect() {//Function to select a book to borrow
        System.out.println("***Borrow A Book From The Library***");
        System.out.println("Enter the book you want to borrow");
        String checkout_btitle = scanner.nextLine();
        Book checkout_book = findMatchingBook(checkout_btitle);//Find a match for the book title in the library
        if(checkout_book == null){
            System.out.println("No such book in the library");
            return null;
        }
        if (checkout_book.getStock()< 1) {//If the stock of the book is low,
            System.out.println("The book you have requested is currently unavailable. Please choose a different book!");
            bookSelect();
        }
        Book cart_book = new Book(checkout_book.getTitle(), checkout_book.getAuthor(), checkout_book.getGenre(), checkout_book.getDescription(), 1, checkout_book.getRating());//1 copy of the book is selected
        int new_stock = checkout_book.getStock() - 1;
        //Update shelf stock
        Book shelf_book = new Book(checkout_book.getTitle(), checkout_book.getAuthor(), checkout_book.getGenre(), checkout_book.getDescription(), new_stock, checkout_book.getRating());
        inventory.remove(checkout_book);
        inventory.add(shelf_book);
        saveInventory();
        return cart_book;
    }
    public void bookReturn(String ret_book){//Function to return a book
    	Book checkin_book = findMatchingBook(ret_book);//Find a match for the book being returned
    	int up_stock = checkin_book.getStock() + 1;//Increment the stock of the book
    	//Update shelf stock:
    	Book retBook = new Book(checkin_book.getTitle(), checkin_book.getAuthor(), checkin_book.getGenre(), checkin_book.getDescription(), up_stock, checkin_book.getRating());
    	inventory.remove(checkin_book);
    	inventory.add(retBook);
    	saveInventory();
    }
    public ArrayList<Book> sortBooks(BookComparator comparator) {//Function to sort and display the library
        ArrayList<Book> sortedBooks = comparator.sort(inventory);//Sort the library
        for (Book book : sortedBooks) {//Display the library:
            System.out.println("Title:" + book.getTitle());
            System.out.println("Author:" + book.getAuthor());
            System.out.println("Genre:" + book.getGenre());
            System.out.println("Description:"+book.getDescription());
            System.out.println("Stock: " + book.getStock());
            System.out.println("Rating: " + book.getRating());
            ArrayList<String> bookReviews = book.getReviews();
            for(String rev : bookReviews){
                System.out.println(rev);
            }
        }
        return sortedBooks;//Return sorted arraylist of books
    }
    public void chooseSort(int option){//Choose sorting method
        BookComparator bookComparator;
        int key = 1;
        switch(option) {
        	case 1: 
        	     bookComparator = new TitleComparator();//Sort by title
                     break;
                case 2:
                     bookComparator = new AuthorComparator();//Sort by author
                     break;
                case 3:
                     bookComparator = new GenreComparator();//Sort by genre
                     break;
                case 4:
                      bookComparator = new RatingComparator();//Sort by rating
                      break;
                case 5:
                      bookComparator = new ReviewsComparator();//Sort by number of reviews
                      break;
                default:
                      bookComparator = null;
                      key *=0;//No sorting:
                      System.out.println("Invalid sorting parameter. Books will be displayed in their current order.");
                      for (Book book : inventory) {
                	System.out.println("Title:" + book.getTitle());
                	System.out.println("Author:" + book.getAuthor());
                	System.out.println("Genre:" + book.getGenre());
            		System.out.println("Description:"+book.getDescription());
            		System.out.println("Stock: " + book.getStock());
            		System.out.println("Rating: " + book.getRating());
            		ArrayList<String> bookReviews = book.getReviews();
            		for(String rev : bookReviews){
            		    System.out.println(rev);
            		}
            	      }
	}
	if(key==1){
		sortBooks(bookComparator);//Sort the books
	}
    }        
}

