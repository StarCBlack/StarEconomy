package me.starcrazzy.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ECommand extends Command {


    public ECommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        Player player = arg0 instanceof Player ? (Player)arg0 : null;
        execute(arg0, player, arg1, arg2);
        return true;
    }

    public abstract void execute(CommandSender sender, Player player, String label, String[] args);
}
