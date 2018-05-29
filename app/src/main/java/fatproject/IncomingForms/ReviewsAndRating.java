package fatproject.IncomingForms;

import java.util.ArrayList;
import java.util.List;

import fatproject.entity.Review;

public class ReviewsAndRating {

    private List<Review> reviews;
    private float rating;

    public ReviewsAndRating() {
        reviews = new ArrayList<>();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
