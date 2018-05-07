package fatproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fatproject.entity.OneMessage;
import fatproject.findatutor.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<OneMessage> messages;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.oneMessage);
        }
    }


    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_text, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        OneMessage oneMessage = messages.get(position);
        holder.text.setText(oneMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
