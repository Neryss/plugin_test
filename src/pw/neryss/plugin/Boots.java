package pw.neryss.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Boots implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("boots")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Can't give em to the console");
				return true;
			}
			Player player = (Player)sender;
			if (player.getInventory().firstEmpty() == -1) {
				Location loc = player.getLocation();	
				World world = player.getWorld();
				world.dropItem(loc, getItem());
				player.sendMessage(ChatColor.GOLD + "Boots dropped!");
				return true;
			}
			player.getInventory().addItem(getItem());
			player.sendMessage(ChatColor.BLUE + "You got something");
			return true;
		}
		return false;
	}
	
	public ItemStack getItem() {
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta meta = boots.getItemMeta();
		
		meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Boots of leaping");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Beautiful test boots, they look great");
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		boots.setItemMeta(meta);
		return boots;
	}
}
