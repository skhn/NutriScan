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
public class RecyclerViewHome extends RecyclerView.Adapter<RecyclerViewHome.ViewHolder> {

    private ArrayList<String> names;
    private String[] dailyVals;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public RecyclerViewHome.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView)
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_home, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHome.ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView textName = (TextView) cardView.findViewById(R.id.cardview_home_text);
        textName.setText(names.get(position));
        TextView textNos = (TextView) cardView.findViewById(R.id.cardview_home_Nos);
        textNos.setText(dailyVals[position]);

    }

    public RecyclerViewHome(ArrayList<String> names, String[] dailyVals) {
        this.names = new ArrayList<>(names);
        this.dailyVals = dailyVals;
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
