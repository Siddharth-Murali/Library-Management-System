import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class RatingComparator implements BookComparator {//Class implementing the Book Comparator interface to sort an arraylist of books by rating
    public ArrayList<Book> sort(ArrayList<Book> books) {
        Collections.sort(books, Comparator.comparing(Book::getRating).reversed());
        return books;
    }
}

