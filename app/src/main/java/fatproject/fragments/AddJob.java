package fatproject.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.activities.MainActivity;
import fatproject.activities.MainAplication;
import fatproject.entity.Job;
import fatproject.entity.Type;
import fatproject.findatutor.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJob extends Fragment {

    @BindView(R.id.addJobButton)
    Button addJobButton;

    @BindView(R.id.placeOfJobInputLayout)
    TextInputLayout placeOfJobInputLayout;

    @BindView(R.id.editTextJob)
    EditText editTextJob;

    private static final String EMPTY_STRING = "";
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_job, container, false);
        ButterKnife.bind(this, view);

        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextJob.getText().toString().equals(EMPTY_STRING)) {
                    placeOfJobInputLayout.setError("Enter place of the job");
                }else {
                    sendNewJob(new Job(editTextJob.getText().toString(), Type.JOB));
                    Toast.makeText(getActivity().getApplicationContext(), editTextJob.getText().toString()+" was added.", Toast.LENGTH_LONG).show();
                    editTextJob.setText(EMPTY_STRING);
                }
            }
        });
        return view;
    }

    public void sendNewJob(Job job){
        MainAplication.getServerRequests().updateJobs(job, MainAplication.getCurrentUser().getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.err.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
