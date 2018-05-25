package fatproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import fatproject.findatutor.R;

public class PopupWindowForContractDeleting extends AppCompatDialogFragment implements View.OnClickListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.proved_window_of_deleting_contract, null);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Light.otf");
        TextView areYouSureWord = view.findViewById(R.id.areYouSureText);
        areYouSureWord.setTypeface(font);

        BootstrapButton yes = view.findViewById(R.id.yesButtonInContractDeletingWindow);
        BootstrapButton cancel = view.findViewById(R.id.canselButtonInContractDeletingWindow);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
            }
        });

        builder.setView(view);


        return builder.create();
    }

    @Override
    public void onClick(View v) {

    }
}
