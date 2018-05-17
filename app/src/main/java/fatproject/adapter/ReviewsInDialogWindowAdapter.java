package fatproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fatproject.activities.MainAplication;
import fatproject.entity.Review;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsInDialogWindowAdapter extends RecyclerView.Adapter<ReviewsInDialogWindowAdapter.MyViewHolder>{

    private List<Review> reviews;
    private String name;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView author, reviewText;

        public MyViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.authorName);
            reviewText = itemView.findViewById(R.id.reviewText);
        }
    }

    public ReviewsInDialogWindowAdapter(List<Review> reviews) {this.reviews = reviews;}

    @NonNull
    @Override
    public ReviewsInDialogWindowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_in_dialog_window, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsInDialogWindowAdapter.MyViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.reviewText.setText(review.getReview());
        holder.author.setText(getNameOfPersonWhoLeftReview(review.getFrom()));

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public String getNameOfPersonWhoLeftReview(Long id){
        MainAplication.getServerRequests().getNameOfPersonWhoLeftComment(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                name = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        return name;
    }

}
