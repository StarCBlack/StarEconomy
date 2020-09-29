package me.starcrazzy.economy;

import lombok.Getter;
import me.starcrazzy.economy.manager.StartManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        instance = this;
        new StartManager();
        getLogger().info(ChatColor.GREEN + "Plugin ativado com sucesso!");
        saveDefaultConfig();

    }
}
