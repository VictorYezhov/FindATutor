package fatproject.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fatproject.IncomingForms.CommentForm;
import fatproject.findatutor.R;

/**
 * Created by Komarenski Max on 21.03.2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<CommentForm> commentFormList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTime;
        public TextView textComment;
        public TextView price;
        public TextView name;
        //public ImageView userImage;


        public MyViewHolder(View view) {
            super(view);

            dateTime = (TextView) view.findViewById(R.id.timeOfComment);
            textComment = (TextView) view.findViewById(R.id.textOfComment);
            price = (TextView) view.findViewById(R.id.priceOfComment);
            name = (TextView) view.findViewById(R.id.nameOfUserThatLeavedComment);
            //userImage = view.findViewById(R.id.imageOfUserInComment);
        }
    }

    public CommentAdapter(List<CommentForm> commentFormList) {
        this.commentFormList = commentFormList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentForm commentForm = commentFormList.get(position);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(commentForm.getImage(), 0, commentForm.getImage().length);
        String nameAndSureName = commentForm.getUserName() + " " + commentForm.getUserSurname();

        holder.dateTime.setText(commentForm.getComment().getDateTime().toString());
        holder.textComment.setText(commentForm.getComment().getTextComment());
        holder.price.setText(commentForm.getComment().getPrice());
        holder.name.setText(nameAndSureName);
        //holder.userImage.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return commentFormList.size();
    }

}
