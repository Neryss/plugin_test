package pw.neryss.plugin;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class Skulls implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("skull")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry you can't do that");
				return true;
			}
			Player player = (Player)sender;
			if (args.length == 0) {
				// gives player head
				player.sendMessage(ChatColor.GOLD + "You got the skull of " + player.getName());
				player.getInventory().addItem(getPlayerHead(player.getName()));
				return true;
			}
			// args to give the required player head
			player.sendMessage(ChatColor.GOLD + "You got the skull of " + args[0]);
			player.getInventory().addItem(getPlayerHead(args[0]));
			return true;
		}
		return false;
	}
	
	public ItemStack getPlayerHead(String name) {
		// weird shit to check server version validity
		boolean isNewVer = Arrays.stream(Material.values())
				.map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
		Material type = Material.matchMaterial(isNewVer ? "PLAYER_HEAD" : "SKULL_ITEM");
		ItemStack item = new ItemStack(type, 1);
		// idk and deprecated but hey it works !
		if (!isNewVer)
			item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setOwner(name);
		item.setItemMeta (meta);
		return (item);
	}
}
