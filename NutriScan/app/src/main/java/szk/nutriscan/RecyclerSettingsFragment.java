package szk.nutriscan;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerSettingsFragment extends Fragment {

    ArrayList<String> columnNames;

    public RecyclerSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerList = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_settings
                , container, false);



        ArrayList<String> nutriNameList = new ArrayList<>();

        try{

            SQLiteOpenHelper databaseHelper = new Database(getContext());
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor dbCursor = db.query("temp", null, null, null, null, null, null);
            columnNames = new ArrayList<>(Arrays.asList(dbCursor.getColumnNames()));
            dbCursor.close();
            db.close();

        } catch (SQLiteException e) {
            Log.i("ERROR", "ERROR IN RECYCLERSETTINGSFRAGMENT");
            e.printStackTrace();
        }

        if(columnNames != null) {
            for(String item : columnNames) {
                if(!item.equalsIgnoreCase("_id") && !item.equalsIgnoreCase("email") && !item.equalsIgnoreCase("calories") ) {
                    nutriNameList.add(item);
                }
            }
        } else {
            Log.i("ERROR", "ERROR IN RECYCLERSETTINGSFRAGMENT   -   columnNames array is null");
        }

        RecyclerViewSettings adapter = new RecyclerViewSettings(nutriNameList);
        recyclerList.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerList.setLayoutManager(layoutManager);
        return recyclerList;
    }



}

