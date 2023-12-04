import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class AuthorComparator implements BookComparator {//Class implementing the Book Comparator interface to sort an array list of books by the names of the author
    public ArrayList<Book> sort(ArrayList<Book> books) {
        Collections.sort(books, Comparator.comparing(Book::getAuthor));
        return books;
    }
}

