import java.util.List;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;

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

    //UR
    static void listCrashes(List<Crash> crashes){
        listCrashes(crashes, 0, crashes.size());
    }

    static void listCrashes(List<Crash> crashes, int start , int end){
        for (int i = start; i < end; i++) {
            System.out.println(crashes.get(i).toString());
        }

        System.out.println("\nTotal number of records listed: " + String.valueOf(end - start + 1));
    }

    //UR
    static void listCrashes(List<Crash> crashes, String[] specifiedFields){
        listCrashes(crashes, 0, crashes.size(), specifiedFields);
    }
    
    static void listCrashes(List<Crash> crashes, int start , int end, String[] specifiedFields){
        String[] fieldNames = getFieldNames(crashes.get(0));

        for (int i = start; i < end ; i++) {
            System.out.print("[");
            for(int j = 0; j<16 ; j++){
                for(String specifiedField: specifiedFields){
                    //UR: compare by value, not reference
                    if(fieldNames[j].equals(specifiedField)){
                    //if(fieldNames[j]==specifiedField){
                        System.out.print(fieldNames[j] + ": " + crashes.get(i).fieldValueAsString(fieldNames[j]) + ", ");
                        break;
                    }
                }
            }
            System.out.println("]");
        }
        System.out.println("\nTotal number of records listed: " + String.valueOf(end - start + 1) + "\n");
    }

    static List<Crash> sortByFieldName(List<Crash> crashes, String fieldName, String order) {
        //UR: Menu is designed in the way that order will never be empty or null
        if (order == "" || order == " ") { // by default not specified orders are in ascending mode
            order = "asc";
        }

        if (fieldName == "date") {
            if(order == "asc")
                crashes.sort(Comparator.comparing(f -> f.getLocalDate(), Comparator.nullsFirst(Comparator.naturalOrder())));
            else if(order == "desc")
                crashes.sort(Collections.reverseOrder(Comparator.comparing(f -> f.getLocalDate(), Comparator.nullsFirst(Comparator.naturalOrder()))));
        }

        else if (fieldName == "time") {
            if(order == "asc")
                crashes.sort(Comparator.comparing(f -> f.getTime(), Comparator.nullsFirst(Comparator.naturalOrder())));
            else if(order == "desc")
                crashes.sort(Collections.reverseOrder(Comparator.comparing(f -> f.getTime(), Comparator.nullsFirst(Comparator.naturalOrder()))));
         }
        else if (fieldName == "location"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getLocation().compareTo(f2.getLocation());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getLocation().compareTo(f2.getLocation());}));
        }
        else if (fieldName == "operator"){
            if(order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getOperator().compareTo(f2.getOperator());});
            else if(order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getOperator().compareTo(f2.getOperator());})); 
        }
        else if (fieldName == "flight"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getFlight().compareTo(f2.getFlight());});
            else if(order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getFlight().compareTo(f2.getFlight());}));
        }
        else if (fieldName == "route"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getRoute().compareTo(f2.getRoute());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getRoute().compareTo(f2.getRoute());}));
        }
        else if (fieldName == "type"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getType().compareTo(f2.getType());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getType().compareTo(f2.getType());}));
        }
        else if (fieldName == "registration"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getRegistration().compareTo(f2.getRegistration());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getRegistration().compareTo(f2.getRegistration());}));
        }
        else if (fieldName == "cn_In"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getCn_In().compareTo(f2.getCn_In());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getCn_In().compareTo(f2.getCn_In());}));
        }
        else if (fieldName == "aboard"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return Integer.valueOf(f1.getAboard()).compareTo(f2.getAboard());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return Integer.valueOf(f1.getAboard()).compareTo(f2.getAboard());}));
        }
        else if (fieldName == "fatalities"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getRegistration().compareTo(f2.getRegistration());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getRegistration().compareTo(f2.getRegistration());}));
        }
        else if (fieldName == "ground"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return Integer.valueOf(f1.getGround()).compareTo(f2.getGround());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return Integer.valueOf(f1.getGround()).compareTo(f2.getGround());}));
        }
        else if (fieldName == "survivors"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return Integer.valueOf(f1.getSurvivors()).compareTo(f2.getSurvivors());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return Integer.valueOf(f1.getSurvivors()).compareTo(f2.getSurvivors());}));
        }
        else if(fieldName == "survivalRate"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return Double.valueOf(f1.getSurvivalRate()).compareTo(f2.getSurvivalRate());}); 
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return Double.valueOf(f1.getSurvivalRate()).compareTo(f2.getSurvivalRate());}));
        }
        else if (fieldName == "summary"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getSummary().compareTo(f2.getSummary());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getSummary().compareTo(f2.getSummary());}));
        }
        else if (fieldName == "clustId"){
            if (order == "asc")
                Collections.sort(crashes, (f1, f2)->{return f1.getClustID().compareTo(f2.getClustID());});
            else if (order == "desc")
                Collections.sort(crashes, Collections.reverseOrder((f1, f2)->{return f1.getClustID().compareTo(f2.getClustID());}));
        }
        else{
            System.out.println("No such field exists!");
        }

        return crashes;
    }
}
