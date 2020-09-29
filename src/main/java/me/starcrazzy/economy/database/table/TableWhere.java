package me.starcrazzy.economy.database.table;

import java.util.stream.Stream;

public class TableWhere {

    private String name,operator;
    private Object value;
    private Boolean in;

    public TableWhere(String name, Object value) {
        this(name, "=", value);
    }

    public TableWhere(String name, Object values, boolean in) {
        this.name = name.toLowerCase();
        this.value = values;
        this.in = in;
    }

    public TableWhere(String key, String operator, Object value) {
        this.name = key;
        this.operator = operator;
        this.value = value;
    }

    public Object[] getValues() {
        if (value.getClass().isArray()) return (Object[])value;
        return new Object[]{ value };
    }

    public String toSQL() {
        if (operator != null) {
            return "`" + name + "` "+operator+" ?";
        } else {
            String[] questionMarks = Stream.of(getValues()).map(s->"?").toArray(String[]::new);
            return "`"+name+"` "+(!in?"NOT ":"")+"IN("+String.join(",", questionMarks)+")";
        }
    }
}
