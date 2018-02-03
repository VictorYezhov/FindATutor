package fatproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by Victor on 30.01.2018.
 */

public class SpinnerAdapter extends ArrayAdapter<CharSequence> {

    public SpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public int getCount() {
        int count = super.getCount();

        return count>0 ? count-1 : count ;
    }

}
