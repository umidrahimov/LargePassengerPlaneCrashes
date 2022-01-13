import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Menu {

    private static Scanner scan = new Scanner(System.in);
    private List<Crash> originaList;
    private List<Crash> crashes;
    private String[] headers;
    private String[] specifiedFields;
    private Integer reportSequence = 1;
    
    public Menu(List<Crash> crashes, String[] headers) {
        this.originaList = crashes;
        this.crashes = new ArrayList<Crash>(originaList);
        this.headers = headers;
        this.specifiedFields = Arrays.copyOf(headers, headers.length);
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
        System.out.println("\t 5 - to filter entities");
        System.out.println("\t 6 - export to file");
        System.out.println("\t 7 - reset filters");
        System.out.println("\t 0 - to exit");

        int selection = getIntInput(1, 2, 3, 4, 5, 6, 7, 0);

        switch (selection) {
            case 1:
                listEntities();
                break;
            case 2:
                sortEntities();
                break;
            case 3:
                searchEntities();
                break;
            case 4:
                listCoulmnNames();
                break;
            case 5:
                filterEntities();
                break;
            case 6:
                exportToFile();
                break;
            case 7:
                resetFilters();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    private void exportToFile() {
        if(crashes == null || crashes.size() == 0){
            System.out.println("No elements to export. Reset filters and try again.");
            return;
        }

        if (Utils.writeToFile("./exportedReport" + reportSequence++ + ".csv", crashes, specifiedFields))
            System.out.println("Report exported successfully");
        else
            System.out.println("Failed to export report");
    }

    private void resetFilters() {
        this.crashes = new ArrayList<Crash>(originaList);
        this.specifiedFields = Arrays.copyOf(headers, headers.length);
        mainMenu();
    }

    void filterEntities() {
        System.out.println("\t 1 - Set filter conditions");
        System.out.println("\t 0 - Back");

        int selection = getIntInput(1, 0);
        switch (selection) {
            case 1:
                Filter filter = getFilterConditions();
                crashes = ReportManager.filterByValue(crashes, filter);
                System.out.printf("%d crashe(s) are found. \n", crashes.size());
                break;
            case 0:
                mainMenu();
                break;
        }
    }

    Filter getFilterConditions() {
        System.out.println("Please enter field name:");
        while (true) {
            try {
                String input = scan.nextLine().toLowerCase().trim();
                if (input.isEmpty()) {
                    System.out.println("Empty input is detected.");
                    continue;
                }
                if (!Arrays.stream(headers).anyMatch(input::equals)) {
                    System.out.println("No such column exists. Try again: ");
                    continue;
                }
                Field field = Crash.class.getDeclaredField(input);
                Class fieldType = field.getType();
                Filter filter = null;
                if (fieldType.equals(String.class)) {
                    filter = getStringFilter(input);
                } else if (fieldType.equals(int.class) || fieldType.equals(double.class)) {
                    filter = getNumberFilter(input);
                } else if (fieldType.equals(LocalTime.class)) {
                    filter = getTimeFilter(input);
                } else if (fieldType.equals(LocalDate.class)) {
                    filter = getDateFilter(input);
                }
                return filter;
            } catch (NoSuchFieldException e) {
                System.out.println("Selected column does not exist");
            } catch (SecurityException e) {
                System.out.println("You have no access to selected column. Please contact administrator");
            }
        }
    }

    private Filter getDateFilter(String column) {
        printDateSubMenu();

        int selection = getIntInput(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        String searchValue = null;
        FilterCondition condition = null;
        switch (selection) {
            case 1:
                searchValue = getDateValue();
                condition = FilterCondition.equals;
                break;
            case 2:
                searchValue = getDateValue();
                condition = FilterCondition.greaterThan;
                break;
            case 3:
                searchValue = getDateValue();
                condition = FilterCondition.lessThan;
                break;
            case 4:
                searchValue = getDateValue();
                condition = FilterCondition.greaterOrEqualTo;
                break;
            case 5:
                searchValue = getDateValue();
                condition = FilterCondition.lessOrEqualTo;
                break;
            case 6:
                searchValue = getDateValues();
                String[] dates = searchValue.split(" ");
                condition = FilterCondition.between;
                return new Filter(column, LocalDate.class, dates[0].trim(), dates[1].trim(), condition);
            case 7:
                condition = FilterCondition.Null;
                break;
            case 8:
            //TODO: prompt to enter year, month, day
                searchValue = String.valueOf(getIntInput(IntStream.rangeClosed(1900, 2022).toArray()));
                condition = FilterCondition.inSpecificYear;
                break;
            case 9:
            searchValue = String.valueOf(getIntInput(IntStream.rangeClosed(1, 12).toArray()));
            condition = FilterCondition.inSpecificMonth;
                break;
            case 10:
            searchValue = String.valueOf(getIntInput(IntStream.rangeClosed(1, 31).toArray()));
            condition = FilterCondition.inSpecificDay;
            break;
        }
        return new Filter(column, LocalDate.class, searchValue, condition);
    }

    private String getDateValues() {
        System.out.println("Please enter date intervals to search (E.g: 1992-10-26 1996-12-16):");

        while (true) {
            String input = scan.nextLine().toLowerCase().trim();

            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }

            String[] dates = input.split(" ");

            if (dates.length>2){
                System.out.println("Only 2 date values are expected. E.g: 1992-10-26 1996-12-16");
                continue;
            }

            for (String date : dates) {
                if (!Utils.tryParseDate(date.trim())) {
                    System.out.printf("Date vaue is expected instead of %s.\n", date);
                    continue;
                }
            }

            return input;
        }    
    }

    private String getDateValue() {
        System.out.println("Please enter value to search:");
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }
            if (!Utils.tryParseDate(input)) {
                System.out.println("Date vaue is expexted.");
                continue;
            }
            return input;
        }    
    }

    private void printDateSubMenu() {
        System.out.println("\t 1 - Equals");
        System.out.println("\t 2 - Greater than");
        System.out.println("\t 3 - Less than");
        System.out.println("\t 4 - Greater than or equal to");
        System.out.println("\t 5 - Less than or equal to");
        System.out.println("\t 6 - Between");
        System.out.println("\t 7 - Null");
        System.out.println("\t 8 - In a specific year");
        System.out.println("\t 9 - In a specific month");
        System.out.println("\t 10 - In a specific day");
    }

    Filter getStringFilter(String column) {
        printStringSubMenu();

        int selection = getIntInput(1, 2, 3, 4, 0);
        String searchValue = null;
        FilterCondition condition = null;
        switch (selection) {
            case 1:
                searchValue = getStringValue();
                condition = FilterCondition.startsWith;
                break;
            case 2:
                searchValue = getStringValue();
                condition = FilterCondition.endsWith;
                break;
            case 3:
                searchValue = getStringValue();
                condition = FilterCondition.contains;
                break;
            case 4:
                condition = FilterCondition.Null;
                break;
            case 0:
                mainMenu();
                break;
        }
        return new Filter(column, String.class, searchValue, condition);
    }

    private void printStringSubMenu() {
        System.out.println("\t 1 - Starts with");
        System.out.println("\t 2 - Ends with");
        System.out.println("\t 3 - Contains");
        System.out.println("\t 4 - Null");
        System.out.println("\t 0 - Back");
    }

    Filter getNumberFilter(String column){
        printNumbericSubMenu();

        int selection = getIntInput(1, 2, 3, 4, 5, 6, 7, 0);
        String searchValue = null;
        FilterCondition condition = null;
        switch (selection) {
            case 1:
                searchValue = getNumbericValue();
                condition = FilterCondition.equals;
                break;
            case 2:
                searchValue = getNumbericValue();
                condition = FilterCondition.greaterThan;
                break;
            case 3:
                searchValue = getNumbericValue();
                condition = FilterCondition.lessThan;
                break;
            case 4:
                searchValue = getNumbericValue();
                condition = FilterCondition.greaterOrEqualTo;
                break;
            case 5:
                searchValue = getNumbericValue();
                condition = FilterCondition.lessOrEqualTo;
                break;
            case 6:
                searchValue = getNumbericValues();
                String[] numbers = searchValue.split(" ");
                condition = FilterCondition.between;
                return new Filter(column, Number.class, numbers[0], numbers[1], condition);
            case 7:
                condition = FilterCondition.Null;
            break;
            case 0:
                mainMenu();
            break;
            }
        return new Filter(column, Number.class, searchValue, condition);
    }

    private void printNumbericSubMenu() {
        System.out.println("\t 1 - Equals");
        System.out.println("\t 2 - Greater than");
        System.out.println("\t 3 - Less than");
        System.out.println("\t 4 - Greater than or equal to");
        System.out.println("\t 5 - Less than or equal to");
        System.out.println("\t 6 - Between");
        System.out.println("\t 7 - Null");
        System.out.println("\t 0 - Back");
    }

    Filter getTimeFilter(String column){
        printNumbericSubMenu();

        int selection = getIntInput(1, 2, 3, 4, 5, 6, 7, 0);
        String searchValue = null;
        FilterCondition condition = null;
        switch (selection) {
            case 1:
                searchValue = getTimeValue();
                condition = FilterCondition.equals;
                break;
            case 2:
                searchValue = getTimeValue();
                condition = FilterCondition.greaterThan;
                break;
            case 3:
                searchValue = getTimeValue();
                condition = FilterCondition.lessThan;
                break;
            case 4:
                searchValue = getTimeValue();
                condition = FilterCondition.greaterOrEqualTo;
                break;
            case 5:
                searchValue = getTimeValue();
                condition = FilterCondition.lessOrEqualTo;
                break;
            case 6:
                searchValue = getTimeValues();
                String[] times = searchValue.split(" ");
                condition = FilterCondition.between;
                return new Filter(column, LocalTime.class, times[0], times[1], condition);
            case 7:
                condition = FilterCondition.Null;
            break;
            case 0:
                mainMenu();
            break;
            }
        return new Filter(column, LocalTime.class, searchValue, condition);
    }

    private String getTimeValues() {
        System.out.println("Please enter time intervals to search (E.g: 11:30 13:00):");

        while (true) {
            String input = scan.nextLine().toLowerCase().trim();

            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }

            String[] times = input.split(" ");

            if (times.length>2){
                System.out.println("Only 2 time values are expected. E.g: 11:30 13:00");
                continue;
            }

            for (String time : times) {
                if (!Utils.tryParseTime(time.trim())) {
                    System.out.printf("Time vaue is expected instead of %s.\n", time);
                    continue;
                }
            }

            return input;
        }  
    }

    private String getTimeValue() {
        System.out.println("Please enter value to search:");
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }
            if (!Utils.tryParseTime(input)) {
                System.out.println("Time vaue is expexted.");
                continue;
            }
            return input;
        }    
    }

    String getStringValue() {
        System.out.println("Please enter value to search:");
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }
            return input;
        }
    }

    String getNumbericValue() {
        System.out.println("Please enter value to search:");
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }
            if (!Utils.tryParseNumber(input)) {
                System.out.println("Numeric vaue is expexted.");
                continue;
            }
            return input;
        }
    }

    private String getNumbericValues() {
        System.out.println("Please enter interval to search (E.g: 5 11):");
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
            if (input.isEmpty()) {
                System.out.println("Empty input is detected.");
                continue;
            }

            String[] numbers = input.split(" ");

            if (numbers.length>2){
                System.out.println("Only 2 numeric values are expected. E.g: 5 11");
                continue;
            }

            for (String number : numbers) {
                if (!Utils.tryParseNumber(number.trim())) {
                    System.out.printf("Numeric vaue is expected instead of %s.\n", number);
                    continue;
                }
            }

            return input;
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

    void listEntities() {
        System.out.println("\t 1 - List all the fields of each entity");
        System.out.println("\t 2 - List only the selected fields of each entity");
        System.out.println("\t 3 - List entities based on the range of rows");
        System.out.println("\t 0 - Back");

        int selection = getIntInput(1, 2, 3, 0);
        switch (selection) {
            case 1:
                specifiedFields = Arrays.copyOf(headers, headers.length);
                ReportManager.listCrashes(crashes);
                break;
            case 2:
                specifiedFields = getFieldNames();
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

    int getIntInput(int... allowedNumbers){
        while (true) {
            String input = scan.nextLine().trim();
            if(!Utils.tryParseInt(input)){
                System.out.println("Integer value is expected. Try again:");
                continue;
            }
            int userInput = Integer.parseInt(input);
            if (!IntStream.of(allowedNumbers).anyMatch(x -> x == userInput)){
                System.out.printf("Only numbers %s are expected\n", Arrays.toString(allowedNumbers));
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
            String[] ranges = input.split("\\W+");
            if(ranges.length!=2){
                System.out.println("2 values for the range are expected. E.g: 5 100. Try again:");
                continue;
            }
            for (int i = 0; i < ranges.length; i++) {
                ranges[i] = ranges[i].trim();
                if(!Utils.tryParseInt(ranges[i])){
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
                crashes = ReportManager.sortByFieldName(crashes, sort.getColumn(), sort.getOrder());
                System.out.println("List is sorted.");
                break;
            case 0:
                mainMenu();
                break;
        }
    }

    Sort getSortConditions(){
        System.out.println("Please enter sort conditions. E.g: {fieldname} {ASC|DESC}"); 
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
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
            if (!Arrays.stream(headers).anyMatch(column::equals)) {
                System.out.println("No such column exists. Try again: ");
                continue;
            }
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

    void searchEntities(){
        System.out.println("\t 1 - Set search conditions");
        System.out.println("\t 0 - Back");

        int selection = getIntInput(1, 0);
        switch (selection) {
            case 1:
                Search search = getSearchConditions();
                crashes = ReportManager.searchByFieldName(crashes, search.getColumn(), search.getValue());
                System.out.printf("%d crashe(s) are found.\n", crashes.size());
                break;
            case 0:
                mainMenu();
                break;
        }
    }

    Search getSearchConditions() {
        System.out.println("Please enter search conditions. E.g: {fieldname} {value}"); 
        while (true) {
            String input = scan.nextLine().toLowerCase().trim();
            if(input.isEmpty()){
                System.out.println("Empty input is detected.");
                continue;
            }
            String[] params = input.split(" ");
            if (params.length < 2) {
                System.out.println("No less than 2 inputs are expected for searching. E.g: {fieldname} {value}");
                continue;
            }
            String column = params[0].trim();
            if (!Arrays.stream(headers).anyMatch(column::equals)) {
                System.out.println("No such column exists. Try again: ");
                continue;
            }
            String value = params[1].trim();
            if(params.length>2){
                value = input.substring(column.length()).trim();
            }
            Search search = new Search(column, value);
            return search;
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
}
