package pw.neryss.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.md_5.bungee.api.ChatColor;

public class TeamsGui implements CommandExecutor, Listener {
	public static Inventory inv;
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] aargs) {
		if (label.equalsIgnoreCase("changeteam")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry you cannot do this");
				return true;
			}
			Player player = (Player)sender;
			System.out.println(inv.getItem(8).getItemMeta().getDisplayName());
			player.openInventory(inv);
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!e.getInventory().equals(inv))
			return ;
		if (e.getCurrentItem() == null)
			return ;
		if (e.getCurrentItem().getItemMeta() == null)
			return ;
		if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
			return ;
		// So they can't take the item in the GUI
		e.setCancelled(true);
		Player player = (Player)e.getWhoClicked();
		if (e.getSlot() == 0) {
			// blue team
			ItemStack[] armor = player.getEquipment().getArmorContents();
			armor = changeColor(armor, Color.BLUE);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "Successfully changed team");
		}
		else if (e.getSlot() == 1) {
			// red team
			ItemStack[] armor = player.getEquipment().getArmorContents();
			armor = changeColor(armor, Color.RED);
			player.getEquipment().setArmorContents(armor);
			player.sendMessage(ChatColor.GOLD + "Successfully changed team");
		}
		else if (e.getSlot() == 8)
			player.closeInventory();
	}
	
	public ItemStack[] changeColor(ItemStack[] a, Color color) {
		for (ItemStack item: a) {
			try {
				if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_LEGGINGS
						|| item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET) {
					LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
					meta.setColor(color);
					item.setItemMeta(meta);
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		return a;
	}
	
	public void createInv() {
		System.out.println("JE CREE INVENTAIRE");
		inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Select your team");
		ItemStack item = new ItemStack(Material.BLUE_CONCRETE);
		ItemMeta meta = item.getItemMeta();
		
		// Blue team
		meta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click to join team");
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(0, item);
		
		// Red team
		item.setType(Material.RED_CONCRETE);
		meta.setDisplayName(ChatColor.RED + "RED TEAM");
		item.setItemMeta(meta);
		inv.setItem(1, item);
		
		// Close inv
		item.setType(Material.BARRIER);
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close menu ");
		lore.clear();
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(8, item);
		System.out.println(inv.getItem(8).getItemMeta().getDisplayName());
	}
}
