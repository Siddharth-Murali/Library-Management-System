import java.io.Serializable;
import java.util.ArrayList;
class Book implements Serializable {//Class for books
    private String title;
    private String author;
    private String genre;
    private String description;
    private float rating;
    private int stock;
    private ArrayList<String> reviews;

    public Book(String title, String author, String genre, String description, int stock, float rating) {//Construtor to initialize values
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.stock = stock;
        this.rating = rating;
        this.reviews = new ArrayList<>();
    }
    //Getters:
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public int getStock() {
        return stock;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }
    //Function to add a review to a book:    
    public void addReview(String review){
        reviews.add(review);
    }
    //Setter for rating
    public void setRating(float rating){
       this.rating = rating;
    }
    
    

}

