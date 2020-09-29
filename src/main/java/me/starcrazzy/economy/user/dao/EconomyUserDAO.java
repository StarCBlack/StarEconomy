package me.starcrazzy.economy.user.dao;

import me.starcrazzy.economy.database.MySQL;
import me.starcrazzy.economy.database.data.Parameters;
import me.starcrazzy.economy.database.manager.MySQLManager;
import me.starcrazzy.economy.database.table.Table;
import me.starcrazzy.economy.database.table.TableColumn;
import me.starcrazzy.economy.database.table.TableRow;
import me.starcrazzy.economy.user.data.EconomyUser;
import me.starcrazzy.economy.user.manager.EconomyUserManager;

import java.sql.SQLException;

/**
 * @author oNospher
 **/
public class EconomyUserDAO<U extends EconomyUser> {

    private Table table = new Table("economy");

    public EconomyUserDAO() {
        MySQL mySQL = MySQLManager.getMySQL("general");
        Table.setDefaultConnection(mySQL.getConnection());
    }


    public void createTable() {
        table.addColumn("username", TableColumn.UUID);
        table.addColumn("coins", TableColumn.DOUBLE);
        table.create();
    }

    public U insert(U element) throws SQLException {
        table.insert(
                "username",
                "coins"
        ).one(
                element.getUsername(),
                element.getCoins()
        );

        return element;
    }

    public <K, V> void update(Parameters<K, V> keys, U element) throws SQLException {
        Integer affectedRows = table.update(
                keys.getKey().toString()
        ).values(
                keys.getValue().toString()
        ).where(
                "username",
                element.getUsername()

        ).execute();

        if (affectedRows <= 0) this.insert(element);
    }

    public <K extends String, V> U find(K key, V value) {
        TableRow row = table.query().where(key, value).first();

        if (row == null) {
            EconomyUser economyUser = new EconomyUser(
                    value.toString(),
                   0

            );

            EconomyUserManager.getUsers().put(economyUser.getUsername(), economyUser);
            return (U) economyUser;
        }

        EconomyUser economyUser = EconomyUserManager.toUser(row);
        EconomyUserManager.getUsers().put(economyUser.getUsername(), economyUser);
        return (U) economyUser;
    }

}