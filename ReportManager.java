import java.util.List;
import java.lang.reflect.Field;

public abstract class ReportManager {
    static String[] getFieldNames(Crash crash){
        Class ftClass = crash.getClass();
		Field[] fields = ftClass.getDeclaredFields();
        String[] strFields = new String[16];

        for(int i = 0 ; i<16; i++){
            strFields[i] = fields[i].getName();
        }

        return strFields;
    }

    static void listCrashes(List<Crash> crashes, int start , int end){
        for (int i = start; i<= end ; i++) {
            System.out.println(crashes.get(i).toString());
        }
    }

    static void listCrashes(List<Crash> crashes, int start , int end, String[] specifiedFields){
        String[] fieldNames = getFieldNames(crashes.get(0));

        
        for (int i = start; i<= end ; i++) {
            System.out.print("[");
            for(int j = 0; j<16 ; j++){
                for(String specifiedField: specifiedFields){
                    if(fieldNames[j]==specifiedField){
                        System.out.print(fieldNames[j] + ": " + crashes.get(i).filedValueAsString(fieldNames[j]) + ", ");
                    }
                }
            }
            System.out.println("]");
        }
    }
}
