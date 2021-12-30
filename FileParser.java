import java.io.*;
import java.util.ArrayList;

public class FileParser{
    public static ArrayList<ArrayList<String>> readCsvFile(String fileName){
        File inpFile = new File(fileName);
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < 16;i++){
            data.add(new ArrayList<String>());
        }
        try(BufferedReader br = new BufferedReader(new FileReader(inpFile))) {
            br.readLine();
			for(String line; (line = br.readLine()) != null; ){
                String[] parsed = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);
                for(int i =0 ; i < parsed.length; i++){
                    data.get(i).add(parsed[i].trim());
                }
			}
		}catch (Exception e){
            System.out.println("Error occured while reading and parsing csv file!\nClass: FileParser\nMethod: readCsvFile(String fileName)");
        }

        return data;
    }
}