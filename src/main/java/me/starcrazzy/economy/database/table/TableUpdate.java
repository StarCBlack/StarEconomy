package me.starcrazzy.economy.database.table;

import lombok.val;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

public class TableUpdate extends QueryBuilder<TableUpdate> {

    private String[] fields;
    private Object[] values;

    public TableUpdate(Connection connection, Table table, String... fields) {
        super(connection, table);
        this.fields = fields;
    }

    public TableUpdate values(Object... objects) {
        if (objects.length != fields.length)
            throw new RuntimeException("Invalid size, values size need to be equal to fields size");
        this.values = objects;
        return this;
    }

    public Integer execute() throws SQLException {
        val query = buildQuery();

        String[] fields = Stream.of(this.fields).map(s->"`"+s+"` = ?").toArray(String[]::new);
        String join = String.join(", ", fields);

        String sql = "UPDATE `"+table+"` SET "+join+query.sql;
        if (Table.DEBUG) System.out.println("[DEBUG] "+sql);

        try (val ps = link.prepareStatement(sql)) {
            int column = 1;
            for (Object o : values) ps.setObject(column++, o);
            for (Object o : query.values) ps.setObject(column++, o);
            return ps.executeUpdate();
        }
    }
}
