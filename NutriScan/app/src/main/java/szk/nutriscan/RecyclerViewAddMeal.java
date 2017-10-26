package szk.nutriscan;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by szk on 8/2/2017.
 */

//Adapter for recycler view
public class RecyclerViewAddMeal extends RecyclerView.Adapter<RecyclerViewAddMeal.ViewHolder> {

    private ArrayList<String> names;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public RecyclerViewAddMeal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView)
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cardview_addmeal, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAddMeal.ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView textName = (TextView) cardView.findViewById(R.id.cardview_addmeal_text);
        textName.setText(names.get(position));

    }

    public RecyclerViewAddMeal(ArrayList<String> names) {
        this.names = new ArrayList<>(names);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
