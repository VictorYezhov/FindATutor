package fatproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fatproject.entity.Question;
import fatproject.findatutor.R;

public class YourQuestionsAdapter extends RecyclerView.Adapter<YourQuestionsAdapter.MyViewHolder> {
    private List<Question> questions;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, date;
        //list of tags

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.my_questions_title);
            date = itemView.findViewById(R.id.my_questions_date);
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
        holder.date.setText(q.getDateTime().toString());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
