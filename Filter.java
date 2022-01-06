public class Filter {
    private String column;
    private Class fieldType;
    private String value;
    private String lastValue;
    private FilterCondition filterCondition;
    
    public Filter(String column, Class fieldType, String value, FilterCondition filterCondition) {
        this(column, fieldType, value, null, filterCondition);
    }

    public Class getFieldType() {
        return fieldType;
    }


    public Filter(String column, Class filedType, String value, String lastValue, FilterCondition filterCondition) {
        this.column = column;
        this.fieldType = filedType;
        this.value = value;
        this.lastValue = lastValue;
        this.filterCondition = filterCondition;
    }

    public String getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }

    public String getLastValue() {
        return lastValue;
    }

    public FilterCondition getFilterCondition() {
        return filterCondition;
    }
}

enum FilterCondition{
    startsWith,
    endsWith,
    contains,
    Null,
    equals,
    greaterThan,
    lessThan,
    greaterOrEqualTo,
    lessOrEqualTo,
    between,
    inSpecificYear,
    inSpecificMonth,
    inSpecificDay
}
