package fatproject.fragments;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fatproject.Helpers.AppointmentObserver;
import fatproject.Helpers.ContractsQueue;
import fatproject.Helpers.ContractsQueueObserver;
import fatproject.activities.FragmentDispatcher;
import fatproject.activities.MainAplication;
import fatproject.adapter.ContractsAdapter;
import fatproject.dialogWindows.PopupWindowForContractDeleting;
import fatproject.entity.Appointment;
import fatproject.findatutor.R;
import fatproject.service.AppointmentScheduler;
import fatproject.service.Checker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contracts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contracts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contracts extends Fragment implements ContractsQueueObserver, AppointmentObserver{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ContractsQueue queue;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.contracts_rv)
    RecyclerView recyclerView;

    private ContractsAdapter contractsAdapter;

    private OnFragmentInteractionListener mListener;
    List<Appointment> appointments = new ArrayList<>();

    public Contracts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contracts.
     */
    // TODO: Rename and change types and number of parameters
    public static Contracts newInstance(String param1, String param2) {
        Contracts fragment = new Contracts();
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
        queue = ContractsQueue.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contracts, container, false);
        ButterKnife.bind(this, view);


        Typeface mainFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.otf");
        Typeface fontForMajorityOfWordAndNumbers = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NHaasGroteskTXPro55Rg.ttf");

        contractsAdapter = new ContractsAdapter(appointments, mainFont, fontForMajorityOfWordAndNumbers, this.getContext(), this.getActivity());
        contractsAdapter.registerObserver(this);
        contractsAdapter.setHasStableIds(true);
        recyclerView.setAdapter(contractsAdapter);
        recyclerView.setItemViewCacheSize(10);

        queue.addObserver(this);
        queue.clear();

        queue.changed();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(layoutManager);

        contractsAdapter.notifyDataSetChanged();

        getAllUserAppointment();

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
        mListener = null;
    }

    public void getAllUserAppointment(){
        MainAplication.getServerRequests().getAllUserAppointments(MainAplication.getCurrentUser().getId()).enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                appointments.clear();
                appointments.addAll(response.body());
                Checker.setAppointments(appointments);
                contractsAdapter.notifyDataSetChanged();
                AppointmentScheduler.reInit();
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateContracts() {
        String id = queue.pop();
        if(id != null)
            updateAppointment(Long.decode(id));
        queue.changed();

    }

    @Override
    public void dataChanged() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        queue.removeObserver(this);
    }

    @Override
    public void update(Long id) {
        PopupWindowForContractDeleting popup = new PopupWindowForContractDeleting();
        popup.initialise(id);
        popup.show(FragmentDispatcher.getFragmentManaget(), "popup");
        contractsAdapter.notifyDataSetChanged();
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

    public void updateAppointment(Long id){
        MainAplication.getServerRequests().updateAppointmentById(id).enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                contractsAdapter.updateAppointment(response.body());
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {

            }
        });
    }
}
