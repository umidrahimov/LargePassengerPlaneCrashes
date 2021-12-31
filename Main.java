import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        ArrayList<Crash> crashes = Utils.readFromFile("./Large_Passenger_Plane_Crashes_1933_to_2009.csv");

        String[] specifiedFields = {"date" , "time", "aboard","survivors"};
        ReportManager.listCrashes(crashes,0,crashes.size()-1, specifiedFields);
        //ReportManager.listCrashes(crashes,0,crashes.size()-1);
    }
}