import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FileParser{
    public static ArrayList<ArrayList<String>> readCsvFile(String fileName){
        File inpFile = new File(fileName);
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

        try(BufferedReader br = new BufferedReader(new FileReader(inpFile))) {
            br.readLine();
			for(String line; (line = br.readLine()) != null; ){
				ArrayList<String> row = new ArrayList<String>();
                String[] parsed = line.split(",");
                for(int i =0 ; i < parsed.length; i++){
                    parsed[i] = parsed[i].trim();
                }
                Collections.addAll(row, parsed);
                data.add(row);
			}
		}catch (Exception e){
        }

        return data;
    }
}