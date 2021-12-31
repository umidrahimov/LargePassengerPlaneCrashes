import java.util.List;

public abstract class ReportManager {
    static void PrintCrashes(List<Crash> crashes){
        for (Crash crash : crashes) {
            System.out.println(crash.toString());
        }
    }
}
