package fatproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapBadge;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fatproject.Helpers.CommentObservable;
import fatproject.IncomingForms.CommentForm;
import fatproject.activities.MainAplication;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by Komarenski Max on 21.03.2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> implements CommentObservable {

    private List<CommentForm> commentFormList;
    private Long userId;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTime, textComment, price, name;
        public BootstrapButton acceptJobButton;
        //public ImageView userImage;


        public MyViewHolder(View view) {
            super(view);

            dateTime = (TextView) view.findViewById(R.id.timeOfComment);
            textComment = (TextView) view.findViewById(R.id.textOfComment);
            price = (TextView) view.findViewById(R.id.priceOfComment);
            name = (TextView) view.findViewById(R.id.nameOfUserThatLeavedComment);
            acceptJobButton = view.findViewById(R.id.acceptJobButton);
            //userImage = view.findViewById(R.id.imageOfUserInComment);
        }
    }

    public CommentAdapter(List<CommentForm> commentFormList, Long userId) {
        this.commentFormList = commentFormList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_comment, parent, false);

        System.out.println("Comment adapter created");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentForm commentForm = commentFormList.get(position);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(commentForm.getImage(), 0, commentForm.getImage().length);
        String nameAndSureName = commentForm.getUserName() + " " + commentForm.getUserSurname();


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(commentForm.getComment().getDateTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.dateTime.setText(new SimpleDateFormat("dd-MM HH:mm").format(oldFormatedDate));
        holder.textComment.setText(commentForm.getComment().getTextComment());
        String price_str = String.valueOf(commentForm.getComment().getPrice()) + "$";
        holder.price.setText(price_str);
        holder.name.setText(nameAndSureName);
        //holder.userImage.setImageBitmap(bitmap);
        if(!MainAplication.getCurrentUser().getId().equals(userId)){
            holder.acceptJobButton.setVisibility(View.INVISIBLE);
        }

        holder.acceptJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyObservers(commentForm.getUserName(), commentForm.getUserSurname(), commentForm.getComment().getUserId(), commentForm.getComment().getQuestion().getId());
            }
        });



    }

    @Override
    public int getItemCount() {
        return commentFormList.size();
    }

    public void addNewComment(CommentForm cf){
        commentFormList.add(cf);
    }

}
