package szk.nutriscan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends Activity implements RefreshList {


    @Override
    public void refreshListMethod() {

        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.listfrag_settings_nutritions);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setTitle(R.string.settings_actionBar);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        RecyclerSettingsFragment recyclerSettingsFragment = new RecyclerSettingsFragment();
        ft.replace(R.id.listfrag_settings_nutritions, recyclerSettingsFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

//        LoginSignupActivity.sharedPreferences = this.getSharedPreferences("package szk.nutriscan", Context.MODE_PRIVATE);
//        LoginSignupActivity.sharedPreferences.edit().putInt("DBV", LoginSignupActivity.sharedPreferences.getInt("DBV", 1)).apply();


        try{

            SQLiteOpenHelper databaseHelper = new Database(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor dbCursor = db.query("temp", null, null, null, null, null, null);

            ArrayList<String> temp = new ArrayList<>(Arrays.asList(dbCursor.getColumnNames()));

            for(String item : temp) {
                if(!item.equalsIgnoreCase("_id") && !item.equalsIgnoreCase("email") && !item.equalsIgnoreCase("calories") ) {
                    Database.setNutrition(item);
                }
            }

            dbCursor.close();
            db.close();

        } catch (SQLiteException e) {
            Log.i("ERROR", "ERROR IN SETTINGSACTIVITY");
            e.printStackTrace();
        }


        Log.i("sp onCreate settings:", Integer.toString(LoginSignupActivity.sharedPreferences.getInt("DBV", 1)));

    }

    public void caloricSetMethod (View v) {
        EditText editText = (EditText) findViewById(R.id.settings_caloric);
        if(Integer.parseInt(editText.getText().toString()) > 0)
        DataClass.setCaloric(Integer.parseInt(editText.getText().toString()));
    }

    public void addItemMethod (View v) {
        EditText editText = (EditText) findViewById(R.id.settings_enter);
        if(editText.getText().toString().length() > 0) {

            if(!Database.getNutrition().contains(editText.getText().toString())) {
                Database.setNutrition(editText.getText().toString());
                Database.setDbVersion(Database.getDbVersion() + 1);

                LoginSignupActivity.sharedPreferences.edit().remove("DBV").apply();
                LoginSignupActivity.sharedPreferences.edit().putInt("DBV", Database.getDbVersion()).apply();
                Log.i("sp addmethod settings:", Integer.toString(LoginSignupActivity.sharedPreferences.getInt("DBV", 1)));
                Log.i("sp dbv from dbc:", Integer.toString(LoginSignupActivity.sharedPreferences.getInt("DBV", 1)));
            }
            //DataClass.setData(new DataClass(editText.getText().toString(), 0));
        }
        editText.setText("");

        //LOG-----------------------------------
       if(Database.getNutrition().size() > 0 ) {for(String x: Database.getNutrition()) {
            Log.i("NUTRITION ARRAYLIST:", x);}} else Log.i("NUTRITION ARRAYLIST:", "IS EMPTY");
            Log.i("DBVERSION: ", Integer.toString(Database.getDbVersion()));
        //LOG-----------------------------------

        refreshListMethod();

    }


    // TODO: Needs work. test after database implementation
    public void deleteMethod(View view) {

       final TextView textView = (TextView) findViewById(R.id.cardview_settings_text);

            new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you Sure?")
                            .setMessage("This record and its history will be permanently deleted")
                            .setPositiveButton("Yes", null /*new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DataClass.removeData(textView.getText().toString());
                                }
                            }*/)
                            .setNegativeButton("No", null)
                            .show();

        refreshListMethod();
        }

    }

