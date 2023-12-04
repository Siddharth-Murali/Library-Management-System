import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReviewsComparator implements BookComparator {//Class implementing the Book Comparator interface to sort an arraylist of books by number of reviews
    @Override
    public ArrayList<Book> sort(ArrayList<Book> books) {
        Collections.sort(books, Comparator.comparingInt((Book book) -> book.getReviews().size()).reversed());
        return books;
    }
}

