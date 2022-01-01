import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        ArrayList<Crash> crashes = Utils.readFromFile("./Large_Passenger_Plane_Crashes_1933_to_2009.csv");

        Menu menu = new Menu(crashes, Utils.getHeaders());
        menu.start();

        // String[] specifiedFields = {"date" , "time", "aboard","survivors", "route"};
        // ArrayList<Crash> sortedCrashes = ReportManager.sortByFieldName(crashes, "aboard","desc");
        // ReportManager.listCrashes(sortedCrashes,0,crashes.size()-1, specifiedFields);
        
    }
}