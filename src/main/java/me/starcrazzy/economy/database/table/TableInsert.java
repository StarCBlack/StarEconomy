package me.starcrazzy.economy.database.table;

import lombok.AllArgsConstructor;
import lombok.val;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@AllArgsConstructor
public class TableInsert {

    private final Connection connection;
    private final Table table;
    private final String[] fields;

    public TableInsertResult one(Object... values) throws SQLException {
        try (val ps = connection.prepareStatement(sql(), Statement.RETURN_GENERATED_KEYS)) {
            for (int x = 0; x < values.length; x++) {
                ps.setObject(x+1, values[x]);
            }
            ps.executeUpdate();

            long id = -1;
            try (val rs = ps.getGeneratedKeys()) {
                if (rs.first())
                    id = rs.getLong(1);
            }
            return new TableInsertResult(id);
        }
    }

    public TableInsertResult many(List<Object[]> values) throws SQLException {
        return many(values.toArray(new Object[values.size()][]));
    }

    public <T> TableInsertResult many(Collection<T> iterable, Function<T, Object[]> mapper) throws SQLException {
        return many(iterable.stream().map(mapper).toArray(Object[][]::new));
    }

    public TableInsertResult many(Object[][] values) throws SQLException  {
        try (val ps = connection.prepareStatement(sql(), Statement.RETURN_GENERATED_KEYS)) {
            List<Long> generated = new ArrayList<>();
            for (Object[] data : values) {
                for (int x = 0; x < data.length; x++)
                    ps.setObject(x + 1, data[x]);
                ps.executeUpdate();

                try (val rs = ps.getGeneratedKeys()) {
                    if (rs.next()) generated.add(rs.getLong(1));
                }
            }
            return new TableInsertResult(generated);
        }
    }

    private String sql() {
        String sql = "INSERT INTO `%s` (%s) VALUES (%s)";
        String[] fields = Stream.of(this.fields).map(s->"`"+s+"`").toArray(String[]::new);
        String[] values = Stream.of(this.fields).map(s->"?").toArray(String[]::new);
        sql = String.format(sql, table.getName(), String.join(",", fields), String.join(",", values));
        return sql;
    }
}
