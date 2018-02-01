package fatproject.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fatproject.findatutor.R;

/**
 * Created by Victor on 31.01.2018.
 */

public class myRecycleViewAdapter extends RecyclerView.Adapter<myRecycleViewAdapter.ViewHolder> {


    private String[] mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public myRecycleViewAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public myRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_answer_questions, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TextView txtView = (TextView) v.findViewById(R.id.item_in_list);

        ViewHolder vh = new ViewHolder(txtView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }



}
