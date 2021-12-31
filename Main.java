import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        ArrayList<Crash> crashes = Utils.readFromFile("./Large_Passenger_Plane_Crashes_1933_to_2009.csv");
        ReportManager.PrintCrashes(crashes);
    }
}