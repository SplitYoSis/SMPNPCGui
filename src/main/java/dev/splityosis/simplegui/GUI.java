package dev.splityosis.simplegui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GUI implements Listener {

    private static Map<String, GUI> GUIs = new HashMap<String, GUI>();
    private static List<String> GUINames = new ArrayList<>();
    private Map<Integer, List<String>> slotCommands;
    private Map<Integer, List<String>> slotMessages;
    private Inventory inv = null;
    private String GUIName;
    private String title;
    private int rows;




    public GUI(String GUIName, String title, int rows) {
        GUINames.add(GUIName);
        slotCommands = new HashMap<Integer, List<String>>();
        slotMessages = new HashMap<Integer, List<String>>();
        this.GUIName = GUIName;
        this.title = title;
        this.rows = rows;
        try {
            inv = Bukkit.createInventory(null, rows * 9, title);
        }catch (Exception e){
            Util.log("&9SimpleGUIs &7- &4Couldn't load GUI ("+ this.GUIName +"). Invalid rows amount [more than 6 or less than 1]");
        }
        putItems();
        Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);

        GUIs.put(this.GUIName.toLowerCase(), this);
    }

    public void putItems() {
        ConfigurationSection section = Main.instance.getConfig().getConfigurationSection(GUIName +".slots");
        for (String path : section.getKeys(false)) {
            int slot = Integer.valueOf(path)-1;
            Material material = Material.getMaterial(section.getString(path+".material"));
            String name = section.getString(path+".name");
            int amount = section.getInt(path+".amount");
            List<String> lore = section.getStringList(path+".lore");
            List<String> commands = section.getStringList(path+".commands");
            List<String> messages = section.getStringList(path+".messages");
            slotCommands.put(slot, commands);
            slotMessages.put(slot, messages);
            ItemStack item = Util.createItemStack(material, 0, name, amount, lore);
            if (section.getBoolean(path+".glow"))
                Util.addEnchantToItem(item, Enchantment.SOUL_SPEED, 1, true);

            inv.setItem(slot, item);
        }
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        e.setCancelled(true);

        if (slotMessages.containsKey(e.getSlot()))
            for (String msg : slotMessages.get(e.getSlot()))
                Util.sendMessage(e.getWhoClicked(), msg.replace("<player>", e.getWhoClicked().getName()));

        if (slotCommands.containsKey(e.getSlot()))
            for (String cmd : slotCommands.get(e.getSlot()))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("<player>", e.getWhoClicked().getName()));
    }



    public void openInventory(Player player) {
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory().equals(inv))
            e.setCancelled(true);
    }

    public static GUI getGUI(String GUIName){
        return GUIs.get(GUIName.toLowerCase());
    }

    public static List<String> getGUIsNames(){
        return GUINames;
    }

    public void dddddddddd(){
        HandlerList.unregisterAll(this);
        GUINames.remove(GUIName);
        slotMessages = new HashMap<>();
        slotCommands = new HashMap<>();
    }

    public static void unloadGUIs(){
        for (GUI g : GUIs.values())
            g.dddddddddd();

        GUIs = new HashMap<String, GUI>();
    }
}