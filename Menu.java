import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Menu {

    private static Scanner scan = new Scanner(System.in);
    private List<Crash> crashes;
    private String[] headers;
    
    public Menu(List<Crash> crashes, String[] headers) {
        this.crashes = crashes;
        this.headers = headers;
    }

    void start(){
        System.out.println("Welcome to *** Large Passenger Plane Crashes 1933-2009 ***");
        while(true)
        mainMenu();
    }

    void mainMenu(){
        System.out.println("\t 1 - to list all entities");
        System.out.println("\t 2 - to sort all entities");
        System.out.println("\t 3 - to search entities");
        System.out.println("\t 4 - to list column names");
        System.out.println("\t 0 - to exit");

        int selection = getIntInput(1, 2, 3, 4, 0);

        switch (selection) {
            case 1:
                listEntities();
            break;
            case 2:
                sortEntities();
            break;
            case 3:
                
            break;
            case 4:
                listCoulmnNames();
            break;
            case 5:
            
            break;
            case 0:
                System.exit(0);
            break;
            }

    }

    void listCoulmnNames(){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < headers.length - 1; i++) {
            sb.append(headers);
            sb.append(", ");
        }
        sb.append(headers[headers.length-1]);
        sb.append(" ]");
        System.out.println(Arrays.toString(headers));
    }

    void listEntities(){
        System.out.println("\t 1 - List all the fields of each entity");
        System.out.println("\t 2 - List only the selected fields of each entity");
        System.out.println("\t 3 - List entities based on the range of rows");
        System.out.println("\t 0 - Back");

        int selection = getIntInput(1, 2, 3, 0);
        switch (selection) {
            case 1:
                ReportManager.listCrashes(crashes);
            break;
            case 2:
                String[] specifiedFields = getFieldNames();
                ReportManager.listCrashes(crashes, specifiedFields);
            break;
            case 3:
                Range range = getRowsRange();
                ReportManager.listCrashes(crashes, range.getStart(), range.getEnd());
            break;
            case 0:
                mainMenu();
            break;
            }
    }

    static int getIntInput(int... allowedNumbers){
        while (true) {
            String input = scan.nextLine();
            if(!tryParseInt(input)){
                System.out.println("Integer value is expected. Try again:");
                continue;
            }
            int userInput = Integer.parseInt(input);
            if (!IntStream.of(allowedNumbers).anyMatch(x -> x == userInput)){
                System.out.printf("Only numbers %s are expected\n", allowedNumbers.toString());
                continue;
            }
            return userInput;
        }
    }

    Range getRowsRange(){
        System.out.println("Please enter range of rows separated by space. E.g: 5 100");    
        while (true) {
            String input = scan.nextLine();
            if(input.isEmpty()){
                System.out.println("Empty input is detected.");
                continue;
            }
            String[] ranges = input.split(" ");
            if(ranges.length!=2){
                System.out.println("2 values for the range are expected. E.g: 5 100. Try again:");
                continue;
            }
            for (int i = 0; i < ranges.length; i++) {
                ranges[i] = ranges[i].trim();
                if(!tryParseInt(ranges[i])){
                    System.out.println("Integer values are expected for the range. Try again:");
                    continue;
                }
            }
            int start = Integer.parseInt(ranges[0]);
            if(start<0){
                System.out.println("Start range cannot be less than 0. Default value 0 will be taken.");
                start = 0;
            }
            int end = Integer.parseInt(ranges[1]);
            int max = crashes.size() - 1;
            if (end > max) {
                System.out.printf("End range cannot be greater than %d. Default value %d will be taken.\n",
                max, max);
                end = max;
            }
            Range range = new Range(start, end);
            return range;
        }
    }

    void sortEntities(){
        System.out.println("\t 1 - Set sort conditions");
        System.out.println("\t 0 - Back");

        int selection = getIntInput(1, 0);
        switch (selection) {
            case 1:
                Sort sort = getSortConditions();
                List<Crash> sortedList = ReportManager.sortByFieldName(crashes, sort.getColumn(), sort.getOrder());
                ReportManager.listCrashes(sortedList);
            break;
            case 0:
                mainMenu();
            break;
            }
    }

    Sort getSortConditions(){
        System.out.println("Please enter sort conditions. E.g: {fieldname} {ASC|DESC}"); 
        while (true) {
            String input = scan.nextLine().toLowerCase();
            if(input.isEmpty()){
                System.out.println("Empty input is detected.");
                continue;
            }
            String[] params = input.split(" ");
            if(params.length>2){
                System.out.println("No more than 2 values are expected for sorting. E.g: {fieldname} {ASC|DESC}");
                continue;
            }
            String column = params[0].trim();
            String order = "asc";
            if(params.length>1){
                String temp = params[1].trim();
                if(temp.equals("desc"))
                order = temp;
            }
            Sort sort = new Sort(column, order);
            return sort;
        }
    }

    String[] getFieldNames(){
        System.out.println("Please enter field names separated by comma: ");       
        while (true) {
            String input = scan.nextLine();
            if(input.isEmpty()){
                System.out.println("Empty input is detected.");
                continue;
            }
            String[] fields = input.toLowerCase().split(",");
            List<String> trueFields = new ArrayList<String>();
            for (int i = 0; i < fields.length; i++) {
                fields[i] = fields[i].trim();
                if(Arrays.stream(headers).anyMatch(fields[i]::equals)){
                    trueFields.add(fields[i]);
                }
                else{
                    System.out.printf("No existing column with name %s\n",fields[i]); 
                }
            }
            if(trueFields.size()==0){
                System.out.println("At least 1 correct column name should be specified. Try again:");
                continue;
            }
            return trueFields.stream().toArray(String[]::new);
        }
    }

    private static boolean tryParseInt(String value) {  
        try {  
            Integer.parseInt(value);  
            return true;  
         } catch (NumberFormatException e) {  
            return false;  
         }  
   }
}
