package fatproject.IncomingForms;

import java.util.ArrayList;
import java.util.List;

import fatproject.entity.Review;

public class ReviewsAndRating {

    private List<Review> reviews;
    private int rating;

    public ReviewsAndRating() {
        reviews = new ArrayList<>();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
