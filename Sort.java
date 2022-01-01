public class Sort {
    private String column;
    private String order;
    
    public Sort(String column, String order) {
        this.column = column;
        this.order = order;
    }

    public String getColumn() {
        return column;
    }

    public String getOrder() {
        return order;
    }    
}
