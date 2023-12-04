import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class GenreComparator implements BookComparator {//Class implementing the BookComparator interface that sorts the arraylist of books by their genres
    public ArrayList<Book> sort(ArrayList<Book> books) {
        Collections.sort(books, Comparator.comparing(Book::getGenre));
        return books;
    }
}

