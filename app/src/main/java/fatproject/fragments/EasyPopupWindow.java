package fatproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import fatproject.findatutor.R;

public class EasyPopupWindow extends AppCompatDialogFragment implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.easy_popup_window, null);

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onClick(View v) {

    }
}
