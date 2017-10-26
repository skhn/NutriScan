package szk.nutriscan;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */


public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View homeLayout = inflater.inflate(R.layout.fragment_home, container, false);

        if(homeLayout != null) {
            String caloricBound = (DataClass.getCaloric() > 0) ? Integer.toString(DataClass.getCaloric()):"0";
            TextView calories = (TextView) homeLayout.findViewById(R.id.caloric_count);
            calories.setText(" / " + caloricBound  + " Calories");
            TextView current = (TextView) homeLayout.findViewById(R.id.caloric_limit);
            current.setText("200");

        }

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        RecyclerHomeFragment recyclerHomeFragment = new RecyclerHomeFragment();
        ft.replace(R.id.listfrag_nutritions, recyclerHomeFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


        return homeLayout;
    }

    //TODO : Need to update the fragments list when back button is pressed from another activity that leads to this one say settings after adding new nutrient

    /*
    private void updateList() {

        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                // user interface interactions and updates on screen

                    Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.listfrag_nutritions);
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.detach(currentFragment);
                    fragmentTransaction.attach(currentFragment);
                    fragmentTransaction.commit();

                // if you want to run this handler only once then delete below line
                mHandler.postDelayed(this, 100);

            }
        });

    }*/

}
