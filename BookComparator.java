import java.util.ArrayList;

public interface BookComparator {//Interface implemented by various classes to sort an arraylist of books on the  basis of their attributes
    ArrayList<Book> sort(ArrayList<Book> books);
}

