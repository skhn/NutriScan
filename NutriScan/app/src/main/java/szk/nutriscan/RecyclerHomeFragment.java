package szk.nutriscan;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerHomeFragment extends Fragment {

    ArrayList<String> columnNames;

    public RecyclerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerList = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_home
                , container, false);


//        String[] nutriNos = new String[DataClass.getData().size()];
//        String[] nutriNames = new String[DataClass.getData().size()];
//        for (int i = 0; i < nutriNames.length; i ++) {
//            nutriNames[i] = DataClass.getData().get(i).getName();
//            nutriNos[i] = DataClass.getData().get(i).getDailyVal().toString()+"%";
//        }



        ArrayList<String> nutriNameList = new ArrayList<>();

        try{

            SQLiteOpenHelper dbHelper = new Database(getContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor dbCursor = db.query("temp", null, null, null, null, null, null);
            columnNames = new ArrayList<>(Arrays.asList(dbCursor.getColumnNames()));
            dbCursor.close();
            db.close();

        } catch (SQLiteException e) {
            Log.i("ERROR", "ERROR IN RECYCLERHOMEFRAGMENT");
            e.printStackTrace();
        }

        if(columnNames != null) {
            for(String item : columnNames) {
                if(!item.equalsIgnoreCase("_id") && !item.equalsIgnoreCase("email") && !item.equalsIgnoreCase("calories") ) {
                    nutriNameList.add(item);
                }
            }
        } else {
            Log.i("ERROR", "ERROR IN RECYCLERHOMEFRAGMENT   -   columnNames array is null");
        }


        String[] nutrinos = new String[nutriNameList.size()];
        for(int i = 0; i < nutrinos.length; i ++) {
            nutrinos[i] = Integer.toString(0) + "%";
        }

        RecyclerViewHome adapter = new RecyclerViewHome( nutriNameList, nutrinos);
        recyclerList.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerList.setLayoutManager(layoutManager);
        return recyclerList;
    }

}
