package me.starcrazzy.economy.database.table;

import lombok.val;

import java.sql.Connection;
import java.sql.SQLException;

public class TableDelete extends QueryBuilder<TableDelete> {

    public TableDelete(Connection connection, Table table) {
        super(connection, table);
    }

    public void execute() throws SQLException {
        val query = buildQuery();

        String sql = "DELETE FROM `"+table+"`"+query.sql;
        if (Table.DEBUG) System.out.println("[DEBUG] "+sql);

        try (val ps = link.prepareStatement(sql)) {
            for (int x = 0; x < query.values.size(); x++)
                ps.setObject(x+1, query.values.get(x));
            ps.executeUpdate();
        }
    }

}
