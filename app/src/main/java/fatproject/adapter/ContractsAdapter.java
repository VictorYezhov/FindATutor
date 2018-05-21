package fatproject.adapter;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fatproject.IncomingForms.QuestionTopicAndPrice;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.entity.Appointment;
import fatproject.entity.Job;
import fatproject.findatutor.R;
import fatproject.fragments.Contracts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Victor on 17.05.2018.
 */

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MyViewHolder> {


    private Typeface fontForTopicWords, fontForAnotherSymbols;


    private List<Appointment> appointments;

    public ContractsAdapter(List<Appointment> appointments, Typeface font1, Typeface font2) {
        this.appointments = appointments;
        this.fontForTopicWords = font1;
        this.fontForAnotherSymbols = font2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topic,topicWord,date,price,dateAndPriceWord,personRight,personLeft;
        public ImageButton buttonForPersonWhoGetsKnowledge;
        public ImageButton buttonForPersonWhoSharesKnowledge;
        public BootstrapButton changeDate;
        public DateAndTimeChangeListener listener;


        public MyViewHolder(View view) {
            super(view);

            buttonForPersonWhoGetsKnowledge = view.findViewById(R.id.buttonForPersonWhoGetsKnowledge);
            buttonForPersonWhoSharesKnowledge = view.findViewById(R.id.buttonForPersonWhoSharesKnowledge);
            dateAndPriceWord = view.findViewById(R.id.dateAndPriceWord);
            personLeft = view.findViewById(R.id.personLeft);
            personRight = view.findViewById(R.id.personRight);
            changeDate = view.findViewById(R.id.changeDate);
            topicWord = view.findViewById(R.id.Topic_word_of_question_in_contract);
            topic = view.findViewById(R.id.Topic_of_question_in_contract);
            price = view.findViewById(R.id.priceInContractItem);
            date = view.findViewById(R.id.dateInContractItem);

        }

    }


    public class DateAndTimeChangeListener implements  TimePickerDialog.OnTimeSetListener {

        public MyViewHolder holder;
        public int appointmentId;
        private int year, month, day;

        public DateAndTimeChangeListener(MyViewHolder holder, int appointmentId) {
            this.holder = holder;
            this.appointmentId = appointmentId;
        }


        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {


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
        DateAndTimeChangeListener listener = new DateAndTimeChangeListener(holder, position);
        holder.listener = listener;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(appointment.getTimeFor().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").
                format(oldFormatedDate));



        if(appointment.getEmployerId().equals(MainAplication.getCurrentUser().getId())){
            getNameOfYourPartner(appointment.getEmployeeId(), holder.personRight);
            holder.personLeft.setText("You");
            holder.buttonForPersonWhoGetsKnowledge.setClickable(true);
            holder.buttonForPersonWhoGetsKnowledge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.buttonForPersonWhoGetsKnowledge.setImageResource(R.drawable.white_check);
                }
            });
        }else {
            getNameOfYourPartner(appointment.getEmployerId(), holder.personLeft);
            holder.personRight.setText("You");
            holder.buttonForPersonWhoSharesKnowledge.setClickable(true);
            holder.buttonForPersonWhoSharesKnowledge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.buttonForPersonWhoSharesKnowledge.setImageResource(R.drawable.white_check);
                }
            });
        }

        getTopicAndPriceOfQuestion(appointment.getQuestionId(), holder); //Change topic and price of contract.

        holder.changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd =  DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                Log.d("Time", String.valueOf(hourOfDay)+ " "+ String.valueOf(minute)+ " "+String.valueOf(second));

                                String min = String.valueOf(minute);
                                if(min.equals("0")){
                                    min = min+"0";
                                }
                                holder.date.setText(year+"-"+monthOfYear+"-"+dayOfMonth+" "+hourOfDay+":"+min);
                                appointments.get(position).setTimeFor(new Timestamp(year, monthOfYear, dayOfMonth,
                                        hourOfDay, minute, 0,0));
                                Log.d("test", "setting time for "+position);
                            }
                        }, true);
                        tpd.setAccentColor(MainAplication.getContext().getResources().getColor(R.color.blue));
                        tpd.show(FragmentDispatcher.getNormalManager(), "TimePickerDialog");
                        Log.d("Date", String.valueOf(year)+ " "+ String.valueOf(monthOfYear)+ " "+String.valueOf(dayOfMonth));
                        Date dateTime = new Date(appointments.get(position).getTimeFor().getTime());

                        appointments.get(position).setTimeFor(new Timestamp(year, monthOfYear, dayOfMonth,
                                dateTime.getHours(), dateTime.getMinutes(), 0,0));
                    }
                });

                dpd.setAccentColor(MainAplication.getContext().getResources().getColor(R.color.blue));
                dpd.show(FragmentDispatcher.getNormalManager(), "DatePickerDialog");


            }
        });



    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }


    public void getTopicAndPriceOfQuestion(Long question_id, ContractsAdapter.MyViewHolder holder){
        MainAplication.getServerRequests().getTopicAndPriceOfQuestion(question_id).enqueue(new Callback<QuestionTopicAndPrice>() {
            @Override
            public void onResponse(Call<QuestionTopicAndPrice> call, Response<QuestionTopicAndPrice> response) {
                holder.topic.setText(response.body().getTopic());
                holder.price.setText(response.body().getPrice().toString());
            }

            @Override
            public void onFailure(Call<QuestionTopicAndPrice> call, Throwable t) {

            }
        });
    }

    public void  getNameOfYourPartner(Long user_id, TextView textView){
        MainAplication.getServerRequests().getNameOfYourPartner(user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                textView.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
