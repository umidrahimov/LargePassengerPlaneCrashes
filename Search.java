public class Search {
    private String column;
    private String value;
    
    public Search(String column, String value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }
}
