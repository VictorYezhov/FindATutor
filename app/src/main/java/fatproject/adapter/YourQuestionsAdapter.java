package fatproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fatproject.entity.Question;
import fatproject.findatutor.R;

public class YourQuestionsAdapter extends RecyclerView.Adapter<YourQuestionsAdapter.MyViewHolder> {
    private List<Question> questions;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, date;
        public RelativeLayout background;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.my_questions_title);
            date = itemView.findViewById(R.id.my_questions_date);
            background = itemView.findViewById(R.id.my_questions_background);
        }
    }

    public YourQuestionsAdapter(List<Question> questions){
        this.questions = questions;
    }

    @NonNull
    @Override
    public YourQuestionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_question_item_in_account, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YourQuestionsAdapter.MyViewHolder holder, int position) {
        Question q = questions.get(position);
        holder.title.setText(q.getTitle());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(q.getDateTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.date.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").
                format(oldFormatedDate));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
