package fatproject.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.List;

import fatproject.entity.Appointment;
import fatproject.entity.Job;
import fatproject.findatutor.R;

/**
 * Created by Victor on 17.05.2018.
 */

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MyViewHolder> {
    TextView topic, topicWord, date, price, dateAndPriceWord;
    BootstrapButton changeDate;
    Typeface fontForTopicWords, fontForAnotherSymbols;
    ImageButton buttonForPersonWhoGetsKnowledge;
    ImageButton buttonForPersonWhoSharesKnowledge;

    private List<Appointment> appointments;

    public ContractsAdapter(List<Appointment> appointments, Typeface font1, Typeface font2) {
        this.appointments = appointments;
        this.fontForTopicWords = font1;
        this.fontForAnotherSymbols = font2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topic,topicWord,date,price,dateAndPriceWord;
        public ImageButton buttonForPersonWhoGetsKnowledge;
        public ImageButton buttonForPersonWhoSharesKnowledge;

        public MyViewHolder(View view) {
            super(view);

            buttonForPersonWhoGetsKnowledge = view.findViewById(R.id.buttonForPersonWhoGetsKnowledge);
            buttonForPersonWhoSharesKnowledge = view.findViewById(R.id.buttonForPersonWhoSharesKnowledge);
            dateAndPriceWord = view.findViewById(R.id.dateAndPriceWord);
            changeDate = view.findViewById(R.id.changeDate);
            topicWord = view.findViewById(R.id.Topic_word_of_question_in_contract);
            topic = view.findViewById(R.id.Topic_of_question_in_contract);
            price = view.findViewById(R.id.priceInContractItem);
            date = view.findViewById(R.id.dateInContractItem);


        }


    }

    @Override
    public ContractsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_single, parent, false);

        return new ContractsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContractsAdapter.MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.dateAndPriceWord.setTypeface(fontForTopicWords);
        holder.topicWord.setTypeface(fontForTopicWords);
        holder.topic.setTypeface(fontForAnotherSymbols);
        holder.price.setTypeface(fontForAnotherSymbols);
        holder.date.setTypeface(fontForAnotherSymbols);

        holder.buttonForPersonWhoGetsKnowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buttonForPersonWhoGetsKnowledge.setImageResource(R.mipmap.ic_not_signed);
            }
        });


        //buttonForPersonWhoGetsKnowledge.setImageResource(R.mipmap.ic_not_signed);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }



}
