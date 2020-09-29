package me.starcrazzy.economy.database.manager;

import com.google.common.collect.Maps;
import me.starcrazzy.economy.Main;
import me.starcrazzy.economy.database.MySQL;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author oNospher
 **/
public class MySQLManager {
    private static HashMap<String, MySQL> databases = Maps.newHashMap();

    /**
     * Setup all mysql databases on call constructor of this class
     */
    public MySQLManager() {
        ConfigurationSection section = this.getConfigurationSection();

        MySQLManager.databases.put("general", new MySQL(
                section.getString("host"),
                section.getString("user"),
                section.getString("password"),
                section.getString("database")
        ));

        this.start();
    }

    /**
     * @return ConfigurationSection
     */
    private ConfigurationSection getConfigurationSection() {
        return Main.getInstance().getConfig().getConfigurationSection("settings.mysql");
    }

    /**
     * @param database
     * @return MySQL
     */
    public static MySQL getMySQL(String database) {
        return MySQLManager.databases.get(database);
    }

    /**
     * Start all mysql databases
     */
    public void start() {
        MySQLManager.databases.forEach((name, mysql) -> {
            try {
                mysql.start();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Refresh all mysql databases
     */
    public void refresh() {
        MySQLManager.databases.forEach((name, mysql) -> {
            try {
                mysql.refresh();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
}
