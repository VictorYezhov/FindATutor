package fatproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import fatproject.findatutor.R;

public class EasyPopupWindow extends AppCompatDialogFragment implements View.OnClickListener {

    private String messageForUser;
    private String namePerson1;
    private String namePerson2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.easy_popup_window, null);

        messageForUser = messageForUser.replace("%3$s", namePerson1);
        messageForUser = messageForUser.replace("%4$s", namePerson2);
        TextView m = view.findViewById(R.id.textOfMessage);
        m.setText(messageForUser);
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onClick(View v) {

    }

    public void initialiseMessage(String text, String text1, String text2){
        messageForUser = text;
        namePerson1 = text1;
        namePerson2 = text2;
    }


}
