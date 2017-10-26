package szk.nutriscan;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment {


    public AddMealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addMealLayout = inflater.inflate(R.layout.fragment_add_meal, container, false);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        RecyclerAddMealFragment recyclerAddMealFragment = new RecyclerAddMealFragment();
        ft.replace(R.id.listfrag_addmeal_nutritions, recyclerAddMealFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


        return addMealLayout;
    }

}
