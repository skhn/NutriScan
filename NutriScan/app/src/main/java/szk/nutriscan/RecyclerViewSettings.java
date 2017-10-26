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
public class RecyclerViewSettings extends RecyclerView.Adapter<RecyclerViewSettings.ViewHolder> {

    private ArrayList<String> names;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public RecyclerViewSettings.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cv = (CardView)
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cardview_settings, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(RecyclerViewSettings.ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView textName = (TextView) cardView.findViewById(R.id.cardview_settings_text);
        textName.setText(names.get(position));

    }

    public RecyclerViewSettings(ArrayList<String> names) {
        this.names = names;
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
