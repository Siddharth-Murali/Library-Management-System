import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class TitleComparator implements BookComparator { //Class implementing the Book Comparator interface to sort an arraylist of books by title
    public ArrayList<Book> sort(ArrayList<Book> books) {
        Collections.sort(books, Comparator.comparing(Book::getTitle));
        return books;
    }
}

