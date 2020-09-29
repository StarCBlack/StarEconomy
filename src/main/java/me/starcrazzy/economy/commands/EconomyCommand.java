package me.starcrazzy.economy.commands;

import me.starcrazzy.economy.user.data.EconomyUser;
import me.starcrazzy.economy.user.manager.EconomyUserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EconomyCommand extends ECommand{
    public EconomyCommand() {
        super("money");
    }

    public void execute(CommandSender sender, Player player, String label, String[] args) {
        if(args.length == 0) {
            player.sendMessage("§eSeu money e: " + ChatColor.WHITE +  EconomyUserManager.find(player.getName()).getCoins());
            return;
        }
        if (player.hasPermission("economy.admin")) {
            if (args.length != 3) {
                sender.sendMessage("§cDigite /money <set/add/remove> <player> <valor>");
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage("§cUsuário não encontrado!");
                    return;
                }
                EconomyUser data = EconomyUserManager.find(target.getName());

                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByExtension("js");
                if(args[0].equalsIgnoreCase("set")) {
                    try {
                        String eval = args[2].toLowerCase().replace("x", String.valueOf(data.getCoins()));
                        Object raw = engine.eval(eval);
                        if (raw instanceof Number) {
                            long value = ((Number) raw).longValue();

                            data.setCoins(value);
                            sender.sendMessage("§aVocê alterou o money de " + target.getName() + " para " + value);
                        } else {
                            sender.sendMessage("§cNúmero inválido!");
                        }
                    } catch (Exception e) {
                        sender.sendMessage("§cFalha ao executar eval!");
                    }
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    try {
                        String eval = args[2].toLowerCase().replace("x", String.valueOf(data.getCoins()));
                        Object raw = engine.eval(eval);
                        if (raw instanceof Number) {
                            long value = ((Number) raw).longValue();
                            long cashValue = (long) (data.getCoins() - value);
                            data.setCoins(cashValue);
                            sender.sendMessage("§aVocê removeu " + value + " do usuário " + target.getName());
                        } else {
                            sender.sendMessage("§cNúmero inválido!");
                        }
                    } catch (Exception e) {
                        sender.sendMessage("§cFalha ao executar eval!");
                    }
                }
                if (args[0].equalsIgnoreCase("add")) {
                    try {
                        String eval = args[2].toLowerCase().replace("x", String.valueOf(data.getCoins()));
                        Object raw = engine.eval(eval);
                        if (raw instanceof Number) {
                            long value = ((Number) raw).longValue();
                            long cashValue = (long) (data.getCoins() + value);
                            data.setCoins(cashValue);
                            sender.sendMessage("§aVocê adicionou " + value + " para o usuário " + target.getName());
                        } else {
                            sender.sendMessage("§cNúmero inválido!");
                        }
                    } catch (Exception e) {
                        sender.sendMessage("§cFalha ao executar eval!");
                    }
                }
            }
        } else {
            sender.sendMessage("§cSem permissão!");
        }
    }

}

