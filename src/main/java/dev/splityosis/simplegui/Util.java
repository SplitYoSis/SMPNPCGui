package dev.splityosis.simplegui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {

    public static void sendMessage (Player player, String message) {
        if (!message.isEmpty())
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(Player player, List<String> messageList) {
        for (String message : messageList)
            if (!message.isEmpty())
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void broadcast(List<String> messageList) {
        Util.log(messageList);
        for (Player player :  Bukkit.getOnlinePlayers())
            for (String message : messageList)
                if (!message.isEmpty())
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void broadcast(String message) {
        Util.log(message);
        for (Player player :  Bukkit.getOnlinePlayers())
            if (!message.isEmpty())
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage (CommandSender sender, String message) {
        if (!message.isEmpty())
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage (CommandSender sender, List<String> messageList) {
        for (String message : messageList)
            if (!message.isEmpty())
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public static void log(List<String> messageList) {
        for (String message : messageList)
            Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendTitle (Player player, String header, String footer, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(Util.fixColor(header), Util.fixColor(footer), fadeIn, stay, fadeOut);
    }

    @SuppressWarnings("deprecation")
    public static ItemStack createItemStack(Material material, int data, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> loreList = Arrays.asList(lore);
        for (int i = 0; i < loreList.size(); i++) {
            String s = loreList.get(i).replace('&', '§');
            loreList.set(i, s);
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
        return item;
    }


    @SuppressWarnings("deprecation")
    public static ItemStack createItemStack(Material material, int data, String name, int amount, List<String> loreList) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        for (int i = 0; i < loreList.size(); i++) {
            String s = loreList.get(i).replace('&', '§');
            loreList.set(i, s);
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
        return item;
    }


    public static void addEnchantToItem (ItemStack item, Enchantment enchantment, int level, boolean hideEnchants) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        if (hideEnchants)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    public static String fixColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static List<String> fixLoreColor(List<String> loreList) {
        for (int i = 0; i < loreList.size(); i++) {
            String s = loreList.get(i).replace('&', '§');
            loreList.set(i, s);
        }
        return loreList;
    }

    public static String getStringFromLocation(Location location) {
        if (location == null) {
            return "";
        }
        return location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
    }

    public static Location getLocationFromString(final String s) {
        if (s == null || s.trim() == "") {
            return null;
        }
        final String[] parts = s.split("_");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }
}