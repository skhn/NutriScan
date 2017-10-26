package szk.nutriscan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.security.AccessController.getContext;
import static szk.nutriscan.LoginSignupActivity.sharedPreferences;

/**
 * Created by szk on 7/29/2017.
 */
public class Database extends SQLiteOpenHelper {

    private static ArrayList<String> nutrition = new ArrayList<>();

    private static final String DB_NAME = "storage";

    private static int DB_VERSION = sharedPreferences.getInt("DBV", 1);


    public static void setDbVersion(int dbVersion) {
        if(dbVersion > DB_VERSION)
        DB_VERSION = dbVersion;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static ArrayList<String> getNutrition() {
        return nutrition;
    }

    public static void setNutrition(String nutrition) {
        Database.nutrition.add(nutrition);
    }

    public static void setNutrition(ArrayList<String> nutrition) {
        Database.nutrition = nutrition;
    }

    Database (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

     public void insertIntoDataTable(SQLiteDatabase db,
                                            String date, String email,
                                            int calories, boolean insert, HashMap<String, Integer> columnNutrition) {

        ContentValues values = new ContentValues();
        values.put("DATE", date);
        values.put("EMAIL", email);
        values.put("CALORIES", calories);

        for(String nutrition: columnNutrition.keySet()) {
            if(this.nutrition.contains(nutrition)) {
                values.put(nutrition.toUpperCase(), columnNutrition.get(nutrition));
            }
        }

        db.insert("data", null, values);

    }


    public void insertIntoTempTable(SQLiteDatabase db, String email,
                                    int calories, boolean insert, HashMap<String, Integer> columnNutrition) {

        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CALORIES", calories);

        for(String nutrition: columnNutrition.keySet()) {
            if(this.nutrition.contains(nutrition)) {
                values.put(nutrition.toUpperCase(), columnNutrition.get(nutrition));
            }
        }

        db.insert("temp", null, values);

    }


    public void insertIntoAccountTable(SQLiteDatabase db,
                                                 String email,
                                              String password, boolean insert) {

        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("PASSWORD", password);
        db.insert("account", null, values);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // ___________________________________________________________CHECK THIS PART FOR CASE: WHAT IF DB_VERSION STARTS OFF MORE THAN -1
        updateDB(db, DB_VERSION, DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        updateDB(db, oldVersion, newVersion);

    }


    private void updateDB (SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion == 1) {

            String dataSql = "CREATE TABLE data (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date TEXT," +
                    "email TEXT," +
                    "calories INTEGER)";

            db.execSQL(dataSql);

            String accountSql = "CREATE TABLE account (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "email TEXT," +
                    "password TEXT," +
                    "setcalories INTEGER)";

            db.execSQL(accountSql);

            String tempSql = "CREATE TABLE temp (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "email TEXT," +
                    "calories INTEGER)";

            db.execSQL(tempSql);


        }

        if (newVersion > oldVersion) {

                addColumnDB(db,oldVersion);
            }
    }




    private void addColumnDB(SQLiteDatabase db, int oldVersion) {

        String dataSql = "ALTER TABLE data" +
                " ADD COLUMN " + nutrition.get(oldVersion - 1) + " INTEGER";

        Log.i("IN ADDCOLUMNDB METHOD:", nutrition.get(oldVersion - 1));
        db.execSQL(dataSql);

        String tempSql = "ALTER TABLE temp" +
                " ADD COLUMN " + nutrition.get(oldVersion - 1
        ) + " INTEGER";

        db.execSQL(tempSql);

    }



    //TODO: Removal of data from DB
    private void removeColumnDB(SQLiteDatabase db, int oldVersion, int newVersion, String columnName) {

        String dataSql = "ALTER TABLE data" +
                "DROP COLUMN " + columnName + " INTEGER";

        db.execSQL(dataSql);

        String tempSql = "ALTER TABLE temp" +
                "ADD COLUMN " + columnName + " INTEGER";

        db.execSQL(tempSql);

    }


}
