package me.starcrazzy.economy.database.table;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TableQuery extends QueryBuilder<TableQuery> {

    private String fields = "*";
    private Integer limit;

    public TableQuery(Connection connection, Table table) {
        super(connection, table);
    }

    public TableQuery limit(int len) {
        limit = len;
        return this;
    }

    public TableQuery columns(String... fields) {
        this.fields = String.join(",", Stream.of(fields).map(s->"`"+s+"`").toArray(String[]::new));
        return this;
    }

    private ResultSet exec() throws Exception {
        val query = buildQuery();

        String sql = "SELECT "+fields+" FROM `"+table+"`"+query.sql;
        if (limit != null) sql+=" LIMIT "+limit;
        if (Table.DEBUG) System.out.println("[DEBUG] "+sql);

        val ps = link.prepareStatement(sql);
        for (int x = 0; x < query.values.size(); x++)
            ps.setObject(x+1, query.values.get(x));
        return ps.executeQuery();
    }

    @SneakyThrows
    public Number sum(String field) {
        this.fields = "SUM(`"+field+"`) as total";
        ResultSet rs = exec();
        Number value = rs.first() ? (Number)rs.getObject("total") : 0;
        close(rs);
        return value;
    }

    @SneakyThrows
    public long count() {
        this.fields = "COUNT(*) as total";
        ResultSet rs = exec();
        long value = rs.first() ? rs.getLong("total") : 0;
        close(rs);
        return value;
    }

    @SneakyThrows
    public List<TableRow> get() {
        ResultSet rs = exec();

        List<TableRow> rows = new ArrayList<>();
        while (rs.next()) {
            rows.add(new TableRow(rs));
        }
        close(rs);
        return rows;
    }

    @SneakyThrows
    public TableRow first() {
        ResultSet rs = exec();
        TableRow row = (rs.first() ? new TableRow(rs) : null);
        close(rs);
        return row;
    }

    private void close(ResultSet rs) {
        try {
            val s = rs.getStatement();
            rs.close();
            s.close();
        } catch (Exception ignored) {}
    }
}
