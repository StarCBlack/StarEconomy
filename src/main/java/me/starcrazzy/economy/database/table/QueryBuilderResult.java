package me.starcrazzy.economy.database.table;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class QueryBuilderResult {

    public String sql;
    public List<Object> values;

    public Object[] values() {
        return values.toArray();
    }
}
