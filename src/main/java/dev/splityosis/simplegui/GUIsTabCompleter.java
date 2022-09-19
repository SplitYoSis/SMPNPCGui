package dev.splityosis.simplegui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class GUIsTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (! (sender instanceof Player)) return null;
        if (args.length != 2) return null;
        if (!args[0].equalsIgnoreCase("open")) return null;

        List<String> list = GUI.getGUIsNames();
        Collections.sort(list);
        return list;
    }
}


