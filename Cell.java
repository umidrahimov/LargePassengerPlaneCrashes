public class Cell {
    private String stringVal;
    private int intVal;

    public Cell(){
        this.stringVal = "#n/a";
        this.intVal = Integer.MAX_VALUE;
    }

    public Cell(String stringVal, int intVal){
        this.stringVal = stringVal;
        this.intVal = intVal;
    }

    public String getStr(){
        return this.stringVal;
    }

    public int getInt(){
        return this.intVal;
    }

    public void setStr(String stringVal){
        this.stringVal = stringVal;
    }

    public void setInt(int intVal){
        this.intVal = intVal;
    }

    public boolean isStr(){
        if(!this.stringVal.equals("#n/a")){
            return true;
        }

        return false;
    }
}
