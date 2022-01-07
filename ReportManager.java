import java.util.List;
import java.util.stream.Collectors;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class ReportManager {
    static String[] getFieldNames(Crash crash){
		Field[] fields = crash.getClass().getDeclaredFields();
        String[] strFields = new String[16];

        for(int i = 0 ; i<16; i++){
            strFields[i] = fields[i].getName();
        }

        return strFields;
    }

    //UR: added method
    static void listCrashes(List<Crash> crashes){
        listCrashes(crashes, 0, crashes.size() - 1);
    }

    static void listCrashes(List<Crash> crashes, int start , int end){
        //UR: added check if list is empty or null
        if(crashes == null || crashes.isEmpty()){
            System.out.println("\nTotal number of records listed: 0");
            return;
        }
        
        for (int i = start; i <= end; i++) {
            System.out.println(crashes.get(i).toString());
        }

        System.out.println("\nTotal number of records listed: " + String.valueOf(end - start + 1));
    }

    //UR: added method
    static void listCrashes(List<Crash> crashes, String[] specifiedFields){
        listCrashes(crashes, 0, crashes.size()-1, specifiedFields);
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
                        //TODO: last index shouldn't have comma at the end
                        System.out.print(fieldNames[j] + ": " + crashes.get(i).fieldValueAsString(fieldNames[j]) + ", ");
                        break;
                    }
                }
            }
            System.out.println("]");
        }
        System.out.println("\nTotal number of records listed: " + String.valueOf(end - start + 1) + "\n");
    }

    //UR: rewrote sort method using reflection
    public static List<Crash> sortByFieldName(List<Crash> list, String fieldName, String order) {
        try {
            Field field = Crash.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            return list.stream().sorted((first, second) -> {
                try {
                    Object val1 = field.get(first);
                    Object val2 = field.get(second);
                    if (val1 == null)
                        return -1;
                    else if (val2 == null)
                        return 1;
                    if (val1 instanceof String && val2 instanceof String) {
                        if ("asc".equals(order))
                            return ((String) val1).compareTo((String) val2);
                        else
                            return ((String) val2).compareTo((String) val1);
                    }
                    if (val1 instanceof LocalDate && val2 instanceof LocalDate) {
                        if ("asc".equals(order))
                            return ((LocalDate) val1).compareTo((LocalDate) val2);
                        else
                            return ((LocalDate) val2).compareTo((LocalDate) val1);
                    } else if (val1 instanceof LocalTime && val2 instanceof LocalTime) {
                        if ("asc".equals(order))
                            return ((LocalTime) val1).compareTo((LocalTime) val2);
                        else
                            return ((LocalTime) val2).compareTo((LocalTime) val1);
                    } else {
                        Number num1 = (Number)val1;
                        Number num2 = (Number)val2;
                        if ("asc".equals(order))
                            return Float.valueOf(num1.floatValue()).compareTo(Float.valueOf(num2.floatValue()));
                        else
                            return Float.valueOf(num2.floatValue()).compareTo(Float.valueOf(num1.floatValue()));
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Error: " + e.getMessage());
                    return 0;
                }
            }).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return list;
        }
    }

    public static List<Crash> filterByValue(List<Crash> crashes, Filter filter) {
        return crashes.stream()
        .filter(crash -> checkFieldValue(crash, filter))
        .collect(Collectors.toList());
    }

    public static List<Crash> searchByFieldName(List<Crash> crashes, String column, String targetValue) {
        return crashes.stream()
        .filter(crash -> checkFieldValue(crash, column, targetValue))
        .collect(Collectors.toList());
    }

    private static boolean checkFieldValue(Crash crash, String column, String targetValue) {
        try {
            Field field = crash.getClass().getDeclaredField(column);    
            field.setAccessible(true);
            Object value = field.get(crash);

            if (targetValue == null || targetValue == "null" && value == null)
                return true;

            if (value instanceof String) {
                if (((String) value).toLowerCase().contains(targetValue))
                    return true;
                return false;
            }

            if(value == targetValue)
                return true;
            
            return false;
            } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
    }

    private static boolean checkFieldValue(Crash crash, Filter filter) {
        try {
            Field field = crash.getClass().getDeclaredField(filter.getColumn());
            field.setAccessible(true);
            Object value = field.get(crash);

            switch (filter.getFilterCondition()) {
                case startsWith:
                    return (value.toString()).toLowerCase().startsWith(filter.getValue()) ? true : false;
                case endsWith:
                    return (value.toString()).toLowerCase().endsWith(filter.getValue()) ? true : false;
                case contains:
                    return (value.toString()).toLowerCase().contains(filter.getValue()) ? true : false;
                case Null:
                    return value == null ? true : false;
                case equals:
                    if(filter.getFieldType() == LocalDate.class)
                        return (LocalDate.parse(value.toString())).equals(LocalDate.parse(filter.getValue())) ? true : false;
                    if(filter.getFieldType() == LocalTime.class)
                        return (LocalTime.parse(value.toString())).equals(LocalTime.parse(filter.getValue())) ? true : false;
                    if(filter.getFieldType() == Number.class)
                        return (Float.valueOf(value.toString())).equals(Float.valueOf(filter.getValue())) ? true : false;
                    break;
                case greaterThan:
                    if(filter.getFieldType() == LocalDate.class)
                        return (LocalDate.parse(value.toString())).isAfter(LocalDate.parse(filter.getValue())) ? true : false;
                    if(filter.getFieldType() == LocalTime.class)
                        return (LocalTime.parse(value.toString())).isAfter(LocalTime.parse(filter.getValue())) ? true : false;
                    if(filter.getFieldType() == Number.class)
                        return (Float.valueOf(value.toString())).compareTo(Float.valueOf(filter.getValue())) > 0 ? true : false;
                    break;
                case lessThan:
                    if (filter.getFieldType() == LocalDate.class)
                        return (LocalDate.parse(value.toString())).isBefore(LocalDate.parse(filter.getValue())) ? true : false;
                    if (filter.getFieldType() == LocalTime.class)
                        return (LocalTime.parse(value.toString())).isBefore(LocalTime.parse(filter.getValue())) ? true : false;
                    if (filter.getFieldType() == Number.class)
                        return (Float.valueOf(value.toString())).compareTo(Float.valueOf(filter.getValue())) < 0 ? true : false;
                    break;
                case greaterOrEqualTo:
                    if (filter.getFieldType() == LocalDate.class)
                        return (LocalDate.parse(value.toString())).isAfter(LocalDate.parse(filter.getValue()))
                                || (LocalDate.parse(value.toString())).equals(LocalDate.parse(filter.getValue())) ? true : false;
                    if (filter.getFieldType() == LocalTime.class)
                        return (LocalTime.parse(value.toString())).isAfter(LocalTime.parse(filter.getValue()))
                                || (LocalTime.parse(value.toString())).equals(LocalTime.parse(filter.getValue())) ? true : false;
                    if (filter.getFieldType() == Number.class)
                        return (Float.valueOf(value.toString())).compareTo(Float.valueOf(filter.getValue())) >= 0 ? true : false;
                    break;
                case lessOrEqualTo:
                    if (filter.getFieldType() == LocalDate.class)
                        return (LocalDate.parse(value.toString())).isBefore(LocalDate.parse(filter.getValue()))
                                || (LocalDate.parse(value.toString())).equals(LocalDate.parse(filter.getValue())) ? true : false;
                    if (filter.getFieldType() == LocalTime.class)
                        return (LocalTime.parse(value.toString())).isBefore(LocalTime.parse(filter.getValue()))
                                || (LocalTime.parse(value.toString())).equals(LocalTime.parse(filter.getValue())) ? true : false;
                    if (filter.getFieldType() == Number.class)
                        return (Float.valueOf(value.toString())).compareTo(Float.valueOf(filter.getValue())) <= 0 ? true : false;
                    break;
                case between:
                    if (filter.getFieldType() == LocalDate.class)
                        return (LocalDate.parse(value.toString())).isAfter(LocalDate.parse(filter.getValue()))
                                && (LocalDate.parse(value.toString())).isBefore(LocalDate.parse(filter.getLastValue())) ? true : false;
                    if (filter.getFieldType() == LocalTime.class)
                        return (LocalTime.parse(value.toString())).isAfter(LocalTime.parse(filter.getValue()))
                                && (LocalTime.parse(value.toString())).isBefore(LocalTime.parse(filter.getLastValue())) ? true : false;
                    if (filter.getFieldType() == Number.class)
                        return (Float.valueOf(value.toString())).compareTo(Float.valueOf(filter.getValue())) <= 0 ? true
                                : false;
                    break;
                case inSpecificYear:
                    return (LocalDate.parse(value.toString()).getYear()) == (Integer.parseInt(filter.getValue())) ? true : false;
                case inSpecificMonth:
                    return (LocalDate.parse(value.toString()).getMonthValue()) == (Integer.parseInt(filter.getValue())) ? true : false;
                case inSpecificDay:
                    return (LocalDate.parse(value.toString()).getDayOfMonth()) == (Integer.parseInt(filter.getValue())) ? true : false;
                default:
                    break;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
    }
}
