public class Cell {
    private String stringVal;
    private int intVal;
    private double doubleVal;

    public Cell(){
        this.stringVal = "#n/a";
        this.intVal = Integer.MAX_VALUE;
        this.doubleVal = Double.POSITIVE_INFINITY;
    }

    public Cell(String stringVal, int intVal,double doubleVal){
        this.stringVal = stringVal;
        this.intVal = intVal;
        this.doubleVal = doubleVal;
    }

    public String getStr(){
        return this.stringVal;
    }

    public int getInt(){
        return this.intVal;
    }

    public double getDouble(){
        return this.doubleVal;
    }

    public void setStr(String stringVal){
        this.stringVal = stringVal;
    }

    public void setInt(int intVal){
        this.intVal = intVal;
    }

    public void setDouble(double doubleVal){
        this.doubleVal = doubleVal;
    }

    public boolean isStr(){
        if(!this.stringVal.equals("#n/a") && this.doubleVal == Double.POSITIVE_INFINITY && this.intVal == Integer.MAX_VALUE){
            return true;
        }

        return false;
    }

    public boolean isInt(){
        if(this.stringVal.equals("#n/a") && this.doubleVal == Double.POSITIVE_INFINITY && this.intVal != Integer.MAX_VALUE){
            return true;
        }

        return false;
    }

    public boolean isDouble(){
        if(this.stringVal.equals("#n/a") && this.doubleVal != Double.POSITIVE_INFINITY && this.intVal == Integer.MAX_VALUE){
            return true;
        }

        return false;
    }

    public String toString(){
        if(this.isInt()){
            return String.valueOf(this.intVal);
        }else if(this.isStr()){
            return this.stringVal;
        }else if(this.isDouble()){
            return String.valueOf(this.doubleVal);
        }

        return "#TYPE_ERROR";
    }
}