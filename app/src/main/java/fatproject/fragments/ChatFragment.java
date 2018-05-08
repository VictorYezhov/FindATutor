package fatproject.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBufferObserver;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.Helpers.MessageUpdateQueue;
import fatproject.activities.MainAplication;
import fatproject.adapter.MessagesAdapter;
import fatproject.entity.Contact;
import fatproject.entity.Message;
import fatproject.findatutor.R;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements DataBufferObserver {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MessagesAdapter messagesAdapter;
    private List<Message> messages;
    private MessageUpdateQueue updateQueue;
    private Long contactId;

    @BindView(R.id.messages)
    RecyclerView recyclerView;

    @BindView(R.id.btn_send)
    Button sendButton;

    @BindView(R.id.edit_message)
    EditText editText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        contactId = Paper.book().read("contactID");
        updateQueue =  MessageUpdateQueue.getInstance();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        messages = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);

        loadMessages();
        messagesAdapter = new MessagesAdapter(messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messagesAdapter);
        messagesAdapter.notifyDataSetChanged();
        updateQueue.addObserver(this);

        bindListeners();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        updateQueue.removeObserver(this);
        Log.d("CHAT FRAGMENT", "ON DETACH METHOD");
        mListener = null;
    }

    @Override
    public void onDataChanged() {
        if(updateQueue.contains(contactId.toString())){
            loadMessages();
            updateQueue.delete(contactId.toString());
            updateQueue.notifyObservers();
        }
    }

    @Override
    public void onDataRangeChanged(int i, int i1) {

    }

    @Override
    public void onDataRangeInserted(int i, int i1) {

    }

    @Override
    public void onDataRangeRemoved(int i, int i1) {

    }

    @Override
    public void onDataRangeMoved(int i, int i1, int i2) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadMessages(){
        messages.clear();
        List<Message> cashedMessages = Paper.book().read("messages"+contactId);
        if(cashedMessages!=null) {
            messages.addAll(cashedMessages);
            loadMessagesWithHelpOfCache();
        }else {
            loadAllMessages();
        }

    }

    private void loadMessagesWithHelpOfCache(){

        MainAplication.getServerRequests().loadMessages(contactId, MainAplication.getCurrentUser().getId()).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.body()!=null) {
                    messages.addAll(response.body());
                    Paper.book().write("messages"+contactId, messages);//TODO replace with saving in cache
                    messagesAdapter.notifyDataSetChanged();
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    private void loadAllMessages(){
        messages.clear();
        MainAplication.getServerRequests().loadAllMessages(contactId, MainAplication.getCurrentUser().getId()).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.body()!=null) {
                    messages.addAll(response.body());
                    Paper.book().write("messages"+contactId, messages);//TODO replace with saving in cache
                    messagesAdapter.notifyDataSetChanged();
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }



    private void bindListeners(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                String textMessage = editText.getText().toString();
                message.setContactId(contactId);
                message.setFrom(MainAplication.getCurrentUser().getId());
                message.setColor(0);
                message.setRead(false);
                message.setMessage(textMessage);
                message.setTimestamp(new Timestamp(System.currentTimeMillis()));
                messages.add(message);
                messagesAdapter.notifyDataSetChanged();
                List<Message> cashedMessages = Paper.book().read("messages"+contactId);
                cashedMessages.add(message);
                Paper.book().write("messages"+contactId, messages);


                MainAplication.getServerRequests().sendMessage(message).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("Message :", response.body());
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Message :", "sending fail");
                    }
                });

                editText.setText("");
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(updateQueue.contains(contactId.toString())){
            updateQueue.delete(contactId.toString());
            updateQueue.notifyObservers();
        }
        messages.clear();
        updateQueue.removeObserver(this);
    }
}
