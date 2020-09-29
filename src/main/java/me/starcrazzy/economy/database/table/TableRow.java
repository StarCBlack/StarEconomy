package me.starcrazzy.economy.database.table;

import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TableRow {

    private Map<String, Object> columns = new HashMap<>();

    @SneakyThrows
    public TableRow(ResultSet rs) {
        val metadata = rs.getMetaData();
        for (int x = 1; x <= metadata.getColumnCount(); x++) {
            String name = metadata.getColumnName(x);
            columns.put(name.toLowerCase(), rs.getObject(x));
        }
    }

    public void set(String key, Object value) {
        columns.put(key.toLowerCase(), value);
    }

    public Object get(String key) {
        return columns.get(key.toLowerCase());
    }

    public Number getNumber(String key) {
        Object o = get(key);
        if (o == null) return 0;
        return (Number) o;
    }

    public boolean getBoolean(String key) {
        return getByte(key)==1;
    }

    public byte getByte(String key) {
        return getNumber(key).byteValue();
    }

    public short getShort(String key) {
        return getNumber(key).shortValue();
    }

    public int getInt(String key) {
        return getNumber(key).intValue();
    }

    public long getLong(String key) {
        return getNumber(key).longValue();
    }

    public double getDouble(String key) {
        return getNumber(key).doubleValue();
    }

    public String getString(String key) {
        return get(key).toString();
    }

    public String getString(String key, String def) {
        String value = get(key).toString();
        return value != null ? value : def;
    }

    public Map<String, Class<?>> getTypes() {
        Map<String, Class<?>> map = new HashMap<>();
        this.columns.forEach((string,obj) -> {
            map.put(string, obj != null ? obj.getClass() : null);
        });
        return map;
    }

    public Map<String, Object> toMap() {
        return columns;
    }

    public JsonObject toJson() {
        return Table.GSON.toJsonTree(columns).getAsJsonObject();
    }

    @Override
    public String toString() {
        return toMap().toString();
    }
}
