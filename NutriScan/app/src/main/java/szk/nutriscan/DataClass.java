package szk.nutriscan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szk on 7/20/2017.
 */
public class DataClass {

    private String name;
    private Integer dailyVal;
    private static int caloric = -1;

    private static String pw = "qwertyuiop";
    private static String em = "slzkhn@gmail.com";

    public DataClass(String name, Integer dailyVal) {
        this.name = name;
        this.dailyVal = dailyVal;
    }

    private static List<DataClass> data = new ArrayList<>();


   // private static DataClass[] data = {new DataClass("Fat", 25), new DataClass("Carb", 45), new DataClass("Protein", 75) };


    public static int getCaloric() {
        return caloric;
    }

    public static void setCaloric(int caloric) {
        DataClass.caloric = caloric;
    }

    public String getName() {
        return name;
    }

    public Integer getDailyVal() {
        return dailyVal;
    }

    public static String getPw() {
        return pw;
    }

    public static String getEm() {
        return em;
    }

    public static List<DataClass> getData() {
        return data;
    }

    public static void setData(DataClass obj) {
        DataClass.data.add(obj);
    }

    public static void removeData(String name) {

        for (DataClass x : DataClass.data ) {
            if(x.getName().equals(name)) {
                DataClass.data.remove(DataClass.data.indexOf(x));
            }
        }
    }



}
