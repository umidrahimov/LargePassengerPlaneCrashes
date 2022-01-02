import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public final class Utils {

    private static String[] headers;

    public static String[] getHeaders() {
        return headers;
    }
    
    public static ArrayList<Crash> readFromFile(String path){

        File file = new File(path);
        ArrayList<Crash> list = new ArrayList<>();

        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine();    //Reads header of the file
            headers = processHeader(line);  //Processes header and stores in static variable

            while ((line = br.readLine()) != null){
                Crash temp = processLine(line);
                list.add(temp);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        catch(InvalidParameterException ex){
            System.out.println(ex.getMessage());
        }        
        catch(UnsupportedOperationException ex){
            System.out.println(ex.getMessage());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public static Boolean writeToFile(String path, ArrayList<Crash> crashs, String header){

        File file = new File(path);
        try (FileWriter fw = new FileWriter(file)) {
            file.createNewFile();
            fw.write(header + "\n");

            for (Crash crash : crashs) {
                fw.write(crash.toString() + "\n");
            }
            return true;
        }
        catch(FileNotFoundException ex){
            System.out.println("Caught exception: " + ex.getMessage());
            return false;
        }
        catch(IOException ex){
            System.out.println("Caught exception: " + ex.getMessage());
            return false;
        }
        catch(InvalidParameterException ex){
            System.out.println("Caught exception: " + ex.getMessage());
            return false;
        }        
        catch(UnsupportedOperationException ex){
            System.out.println("Caught exception: " + ex.getMessage());
            return false;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    private static Crash processLine(String line){

        String[] fields = line.trim().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);

        if (fields.length!=16) {
            throw new InvalidParameterException("Wrong number of parameters detected");
        }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("M/d/")
        .optionalStart()
        .appendPattern("uuuu")
        .optionalEnd()
        .optionalStart()
        .appendValueReduced(ChronoField.YEAR, 2, 2, 1920)
        .optionalEnd()
        .toFormatter();

        LocalDate date = fields[0].trim().isEmpty()? null : LocalDate.parse(fields[0].trim(), formatter);
        LocalTime time = fields[1].trim().isEmpty()? null : LocalTime.parse(fields[1].trim().replaceAll("[a-zA-Z]",""), DateTimeFormatter.ofPattern("H:m"));
        String location = fields[2].trim();
        String operation = fields[3].trim();
        String flight = fields[4].trim();
        String route = fields[5].trim();
        String type = fields[6].trim();
        String registration = fields[7].trim();
        String cn_In = fields[8].trim();
        int aboard = Integer.parseInt(fields[9].trim());
        int fatalities = Integer.parseInt(fields[10].trim());
        int ground = Integer.parseInt(fields[11].trim());
        int survivors = Integer.parseInt(fields[12].trim());
        Double survivalRate = Double.parseDouble(fields[13].trim());
        String summary = fields[14].trim();
        String clustID = fields[15].trim();

        return new Crash(date, time, location, operation, flight,route, type, registration,
        cn_In, aboard, fatalities, ground, survivors, survivalRate, summary, clustID);
    }

    private static String[] processHeader(String line){

        String[] fields = line.toLowerCase().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);

        if (fields.length!=16) {
            throw new InvalidParameterException("Wrong number of parameters detected");
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim().replaceAll("\\.*$", "");
        }

        return fields;
    }

    public static boolean tryParseInt(String value) {  
        try {  
            Integer.parseInt(value);  
            return true;  
         } catch (NumberFormatException e) {  
            return false;  
         }  
   }
}
