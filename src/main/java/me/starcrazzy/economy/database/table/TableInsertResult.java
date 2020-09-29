package me.starcrazzy.economy.database.table;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TableInsertResult {

    private List<Long> keys;

    public TableInsertResult(long id) {
        keys = new ArrayList<>();
        keys.add(id);
    }

    public long first() {
        return keys.size() > 0 ? keys.get(0) : -1;
    }

    public long[] toArray() {
        return keys.stream().mapToLong(l->l).toArray();
    }
}
