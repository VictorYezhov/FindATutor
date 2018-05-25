package fatproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
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

import fatproject.Helpers.AppointmentObservable;
import fatproject.IncomingForms.QuestionTopicAndPrice;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.entity.Appointment;
import fatproject.findatutor.R;
import fatproject.fragments.PopupWindowForContractDeleting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Victor on 17.05.2018.
 */

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MyViewHolder> implements AppointmentObservable{


    private Typeface fontForTopicWords, fontForAnotherSymbols;
    private Context context;
    private Activity activity;


    private List<Appointment> appointments;

    public ContractsAdapter(List<Appointment> appointments, Typeface font1, Typeface font2, Context context, Activity activity) {
        this.appointments = appointments;
        this.fontForTopicWords = font1;
        this.fontForAnotherSymbols = font2;
        this.context = context;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topic,topicWord,date,price,dateAndPriceWord,personRight,personLeft,dangerZoneWord;
        public ImageButton buttonForPersonWhoGetsKnowledge, buttonForPersonWhoSharesKnowledge;
        public BootstrapButton changeDate, deleteContractButton;
        public LottieAnimationView animationView;


        public MyViewHolder(View view) {
            super(view);

            buttonForPersonWhoSharesKnowledge = view.findViewById(R.id.buttonForPersonWhoSharesKnowledge);
            buttonForPersonWhoGetsKnowledge = view.findViewById(R.id.buttonForPersonWhoGetsKnowledge);
            dangerZoneWord = view.findViewById(R.id.dangerZoneWord);
            deleteContractButton = view.findViewById(R.id.deleteContractButton);
            animationView = view.findViewById(R.id.animation_view);
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

    public class DateAndTimeChangeListener implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

        private  int year, monthOfYear, dayOfMonth;
        private int currentAppointentCounter;


        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Date dateTime = new Date(appointments.get(currentAppointentCounter).getTimeFor().getTime());
            this.year = year;
            this.monthOfYear = monthOfYear;
            this.dayOfMonth = dayOfMonth;
            Log.d("year", String.valueOf(year));
            dateTime.setYear(year);
            dateTime.setMonth(monthOfYear);
            dateTime.setDate(dayOfMonth);
            appointments.get(currentAppointentCounter).setTimeFor(new Timestamp(year - 1900, monthOfYear, dayOfMonth,
                    dateTime.getHours(), dateTime.getMinutes(), 0,0));

            TimePickerDialog tpd = TimePickerDialog.newInstance(this, true);
            tpd.setAccentColor(MainAplication.getContext().getResources().getColor(R.color.blue));
            tpd.show(FragmentDispatcher.getNormalManager(), "TimePickerDialog");

        }

        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

            appointments.get(currentAppointentCounter).setTimeFor(new Timestamp(year-1900, monthOfYear, dayOfMonth,
                    hourOfDay, minute, 0,0));
            notifyDataSetChanged();
            MainAplication.getServerRequests().updateAppointment(appointments.get(currentAppointentCounter)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //FragmentDispatcher.launchFragment(Contracts.class);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }

        public void setTarget(int position){
            currentAppointentCounter = position;
        }
    }


    public void updateAppointment(Appointment appointment){
        for(int i = 0; i < appointments.size(); i++){
            if(appointments.get(i).getId().equals(appointment.getId())){
//                appointments.remove(appointments.get(i));
                appointments.get(i).setAcceeptedByEmployer(appointment.isAcceeptedByEmployer());
                appointments.get(i).setAcceptedByEmployee(appointment.isAcceptedByEmployee());
                appointments.get(i).setSuccessForEmployee(appointment.isSuccessForEmployee());
                appointments.get(i).setSuccessForEmployer(appointment.isSuccessForEmployer());
                appointments.get(i).setTimeFor(appointment.getTimeFor());

            }
        }
//        appointments.add(0, appointment);
        System.err.println("here");
        notifyDataSetChanged();
    }


    @Override
    public ContractsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_single, parent, false);

        return new ContractsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContractsAdapter.MyViewHolder holder, int position) {
        //Log.d("rendering", String.valueOf(position));
        Appointment appointment = appointments.get(position);

        holder.dateAndPriceWord.setTypeface(fontForTopicWords);
        holder.topicWord.setTypeface(fontForTopicWords);
        holder.dangerZoneWord.setTypeface(fontForTopicWords);
        holder.topic.setTypeface(fontForAnotherSymbols);
        holder.price.setTypeface(fontForAnotherSymbols);
        holder.date.setTypeface(fontForAnotherSymbols);


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date oldFormatedDate = null;
        try {
            oldFormatedDate = formatter.parse(appointment.getTimeFor().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").
                format(oldFormatedDate));


        if(appointment.isAcceeptedByEmployer()){
            holder.buttonForPersonWhoGetsKnowledge.setImageResource(R.drawable.success_b);
            holder.buttonForPersonWhoGetsKnowledge.setEnabled(false);
        } else{
            holder.buttonForPersonWhoGetsKnowledge.setImageResource(R.drawable.unsuccess_b);
        }

        if(appointment.isAcceptedByEmployee()){
            holder.buttonForPersonWhoSharesKnowledge.setImageResource(R.drawable.success_b);
            holder.buttonForPersonWhoSharesKnowledge.setEnabled(false);
        }else{
            holder.buttonForPersonWhoSharesKnowledge.setImageResource(R.drawable.unsuccess_b);

        }


        if(appointment.getEmployerId().equals(MainAplication.getCurrentUser().getId())){
            getNameOfYourPartner(appointment.getEmployeeId(), holder.personRight);
            holder.personLeft.setText("You");
            holder.buttonForPersonWhoGetsKnowledge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFlashBarOnShortClickListener(context, activity);
                }
            });
            holder.buttonForPersonWhoGetsKnowledge.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.buttonForPersonWhoGetsKnowledge.setEnabled(false);
                    holder.buttonForPersonWhoGetsKnowledge.setVisibility(View.INVISIBLE);
                    holder.animationView.playAnimation();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            holder.animationView.setVisibility(View.INVISIBLE);
                            holder.buttonForPersonWhoGetsKnowledge.setVisibility(View.VISIBLE);
                            holder.buttonForPersonWhoGetsKnowledge.setImageResource(R.drawable.success_b);
                            changeAcceptingOfPersonOnServerSide(appointment.getId(), appointment.getEmployerId(), true, appointment.getEmployeeId());
                        }
                    }, holder.animationView.getDuration());
                    return false;
                }
            });

        }else {
            holder.changeDate.setVisibility(View.INVISIBLE);
            getNameOfYourPartner(appointment.getEmployerId(), holder.personLeft);
            holder.personRight.setText("You");
            holder.buttonForPersonWhoSharesKnowledge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFlashBarOnShortClickListener(context, activity);
                }
            });
            holder.buttonForPersonWhoSharesKnowledge.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.buttonForPersonWhoSharesKnowledge.setEnabled(false);
                    holder.buttonForPersonWhoSharesKnowledge.setVisibility(View.INVISIBLE);
                    holder.animationView.playAnimation();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            holder.animationView.setVisibility(View.INVISIBLE);
                            holder.buttonForPersonWhoSharesKnowledge.setVisibility(View.VISIBLE);
                            holder.buttonForPersonWhoSharesKnowledge.setImageResource(R.drawable.success_b);
                            changeAcceptingOfPersonOnServerSide(appointment.getId(), appointment.getEmployeeId(), true, appointment.getEmployerId());
                        }
                    }, holder.animationView.getDuration());
                    return false;
                }
            });

        }

        getTopicAndPriceOfQuestion(appointment.getQuestionId(), holder); //Change topic and price of contract.

        holder.changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DateAndTimeChangeListener l = new DateAndTimeChangeListener();
                l.setTarget(position);
                DatePickerDialog dpd = DatePickerDialog.newInstance(l, now);
                dpd.setAccentColor(MainAplication.getContext().getResources().getColor(R.color.blue));
                dpd.show(FragmentDispatcher.getNormalManager(), "DatePickerDialog");
            }
        });

        holder.deleteContractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupWindowForContractDeleting popupWindow = new PopupWindowForContractDeleting();
//                popupWindow.show(FragmentDispatcher.getFragmentManaget(), "popup");
                notifyObservers();
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    @Override
    public long getItemId(int position) {
        return appointments.get(position).getId();
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
                String name = response.body();
                name = name.replace('@',' ');
                textView.setText(name);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void changeAcceptingOfPersonOnServerSide(Long contract_id, Long person_id, boolean accepting, Long another_person_id){
        MainAplication.getServerRequests().changeAcceptingOnServerSide(contract_id, person_id, accepting, another_person_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void showFlashBarOnShortClickListener(Context c, Activity a){
                Flashbar flashbar = new Flashbar.Builder(a)
                        .gravity(Flashbar.Gravity.BOTTOM)
                        .title("Make Long Pressing")
                        .message("To prove that you are willing to meet, please make long pressing.")
                        .messageColor(Color.WHITE)
                        .titleColor(Color.WHITE)
                        .backgroundColorRes(R.color.blue)
                        .dismissOnTapOutside()
                        .enterAnimation(FlashAnim.with(c)
                                .animateBar()
                                .duration(400)
                                .slideFromLeft()
                                .alpha()
                                .overshoot())
                        .exitAnimation(FlashAnim.with(c)
                                .animateBar()
                                .duration(450)
                                .slideFromRight()
                                .accelerateDecelerate())
                        .build();

                    flashbar.show();

            }
}
