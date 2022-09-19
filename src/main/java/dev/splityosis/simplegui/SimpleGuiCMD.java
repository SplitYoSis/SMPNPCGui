package dev.splityosis.simplegui;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleGuiCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender.hasPermission("SimpleGUIs.reload") || sender.hasPermission("SimpleGUIs.open"))) {
            Util.sendMessage(sender, "&cYou don't have permission to do this.");
            return false;
        }

        if (args.length == 0){
            sendHelp(sender);
            return false;
        }

        //Reload command
        if (args[0].equalsIgnoreCase("reload")){
            if (!sender.hasPermission("SimpleGUIs.reload")){
                Util.sendMessage(sender, "&cYou don't have permission to do this.");
                return false;
            }
            //reload
            Main.instance.reloadConfig();
            Util.sendMessage(sender,"&bReloading the plugin...");
            Util.sendMessage(sender,"&bLoaded &9"+Main.loadGUIsFromConfig()+"&b GUIs");
            return true;
        }

        //Open command
        if (args[0].equalsIgnoreCase("open")){
            if (!sender.hasPermission("SimpleGUIs.open")){
                Util.sendMessage(sender, "&cYou don't have permission to do this.");
                return false;
            }

            if (args.length < 3){
                sendHelp(sender);
                return false;
            }

            GUI gui = GUI.getGUI(args[1]);
            if (gui == null){
                Util.sendMessage(sender, "&cUnknown GUI ("+args[1]+").");
                return false;
            }

            Player player = Bukkit.getPlayer(args[2]);
            if (player == null){
                Util.sendMessage(sender, "&cThat player isn't online.");
                return false;
            }
            gui.openInventory(player);
            return true;
        }
        sendHelp(sender);
        return false;
    }

    public void sendHelp(CommandSender sender){
        Util.sendMessage(sender, "&bCommands:");
        Util.sendMessage(sender, "&7- &9SimpleGUIs reload &7- &bReloads the config");
        Util.sendMessage(sender, "&7- &9SimpleGUIs open [gui] [player] &7- &bOpens an existing gui");
    }
}
