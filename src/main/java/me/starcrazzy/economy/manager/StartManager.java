package me.starcrazzy.economy.manager;

import me.starcrazzy.economy.Main;
import me.starcrazzy.economy.commands.ECommand;
import me.starcrazzy.economy.database.manager.MySQLManager;
import me.starcrazzy.economy.database.runnable.MySQLRefreshRunnable;
import me.starcrazzy.economy.user.dao.EconomyUserDAO;
import me.starcrazzy.economy.utils.ClassGetter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import java.lang.reflect.Modifier;

public class StartManager {
    public StartManager() {
        new MySQLManager();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new MySQLRefreshRunnable(), 0L, 20L * 60);
        new EconomyUserDAO<>().createTable();
        registerCommands();
    }
    private void registerCommands() {
        ClassGetter.getClassesForPackage(this, "me.starcrazzy.economy.commands").forEach(c -> {
            if (ECommand.class.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
                try {
                    ECommand instance = (ECommand) c.getConstructor().newInstance();
                    ((CraftServer) Main.getInstance().getServer()).getCommandMap().register("essentials", instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ;
    }
}
