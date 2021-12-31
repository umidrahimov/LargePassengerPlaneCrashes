import java.io.*;
import java.util.ArrayList;

public class FileParser{
    public static ArrayList<ArrayList<Cell>> readCsvFile(String fileName){
        File inpFile = new File(fileName);
        ArrayList<ArrayList<Cell>> data = new ArrayList<ArrayList<Cell>>();
        for(int i = 0; i < 16;i++){
            data.add(new ArrayList<Cell>());
        }
        try(BufferedReader br = new BufferedReader(new FileReader(inpFile))) {
            br.readLine();
			for(String line; (line = br.readLine()) != null; ){
                String[] parsed = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);
                for(int i =0 ; i < parsed.length; i++){
                    Cell c = new Cell();
                    String trimedText = parsed[i].trim();
                    try{
                        c.setInt(Integer.parseInt(trimedText));
                    }catch(Exception e){
                        c.setStr(trimedText);  
                    }

                    data.get(i).add(c);
                }
			}
		}catch (Exception e){
            System.out.println("Error occured while reading and parsing csv file!\nClass: FileParser\nMethod: readCsvFile(String fileName)");
        }

        return data;
    }

    public static void main(String args[]){
        ArrayList<ArrayList<Cell>> data = readCsvFile("Large_Passenger_Plane_Crashes_1933_to_2009.csv");
        for(Cell c: data.get(0)){
            if(c.isStr()){
                System.out.println("String val: " + c.getStr());
            }else{
                System.out.print("Int val: ");
                System.out.println(c.getInt());
            }
        }
    }
}