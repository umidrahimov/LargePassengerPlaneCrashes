import java.util.ArrayList;

public class DataFrame {
    private ArrayList<ArrayList<Cell>> listOfColumns;
    private ArrayList<ArrayList<Cell>> listOfRows;

    public DataFrame(ArrayList<ArrayList<Cell>> listOfColumns){
        this.listOfColumns = listOfColumns;
        this.listOfRows = this.toListOfRows(this.listOfColumns);
    }

    public ArrayList<ArrayList<Cell>> getListOfColumns(){
        return this.listOfColumns;
    }

    public void setListOfColumns(ArrayList<ArrayList<Cell>> listOfColumns){
        this.listOfColumns = listOfColumns;
    }

    public ArrayList<ArrayList<Cell>> getListOfRows(){
        return this.listOfRows;
    }

    public void setListOfRows(ArrayList<ArrayList<Cell>> listOfRows){
        this.listOfRows = listOfRows;
    }

    private ArrayList<ArrayList<Cell>> toListOfRows(ArrayList<ArrayList<Cell>> listOfColumns){
        ArrayList<ArrayList<Cell>> listOfRows = new ArrayList<ArrayList<Cell>>();

        for(int i = 0; i<listOfColumns.get(0).size(); i++){
            ArrayList<Cell> row = new ArrayList<Cell>();
            for(ArrayList<Cell> column: listOfColumns){
                row.add(column.get(i));
            }

            listOfRows.add(row);
        }
        return listOfRows;
    }

    public Cell getCell(int row_index , int column_index){
       if(this.listOfRows.get(row_index).get(column_index) == this.listOfColumns.get(column_index).get(row_index)){
           return this.listOfRows.get(row_index).get(column_index);
       }else{
           System.out.println("Bug in DataFrame, listOfRows differs from listOfColumns");
       }

       return new Cell();
    }

    public void setCell(int row_index, int column_index, Cell cell){
        this.listOfRows.get(row_index).set(column_index, cell);
        this.listOfColumns.get(column_index).set(row_index,cell);
    }

    public String rowToString(int row_index, int[] specifiedIndexs){
        String output_result = String.valueOf(row_index)+". ";

        for(int i = 0 ; i < this.listOfRows.get(row_index).size(); i++){
            boolean addToResult = false;
            for(int specifiedIndex: specifiedIndexs){
                if(i == specifiedIndex){
                    addToResult = true;
                    break;
                }
            }

            if(addToResult){
                output_result += String.valueOf(this.listOfRows.get(row_index).get(i)) + " ";
            }
        }

        return output_result;
    }

    public String rowToString(int row_index){
        String output_result = String.valueOf(row_index)+". ";

        for(int i = 0 ; i < this.listOfRows.get(row_index).size(); i++){
            output_result += String.valueOf(this.listOfRows.get(row_index).get(i)) + " ";
        }

        return output_result;
    }

    public void listEntities(int start, int end , int[] specifiedIndexs){
        int cnt = 0;
        for(int i = start ; i <= end ; i++){
            
            String s = this.rowToString(i, specifiedIndexs);
            System.out.println(s);

            cnt += 1;
        }

        System.out.println("\nTotal number of records listed: " + String.valueOf(cnt));
    }

    public void listEntities(int start, int end){
        int cnt = 0;
        for(int i = start ; i <= end; i++){

            String s = this.rowToString(i);
            System.out.println(s);

            cnt += 1;
        }

        System.out.println("\nTotal number of records listed: " + String.valueOf(cnt));
    }
}
