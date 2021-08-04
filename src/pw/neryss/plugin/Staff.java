package pw.neryss.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Staff implements CommandExecutor, Listener {
	public List<String> list = new ArrayList<String>();
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("starstaff")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Sorry you can't use that");
				return true;
			}
			Player player = (Player)sender;
			if (player.getInventory().firstEmpty() == -1) {
				Location loc = player.getLocation();
				World world = player.getWorld();
				world.dropItem(loc, getItem());
				player.sendMessage(ChatColor.GOLD + "Staff dropped! Look at your feet man");
				return true;
			}
			player.getInventory().addItem(getItem());
			player.sendMessage(ChatColor.BLUE + "You've been blessed by the sea gods");
			return true;
		}
		return false;
	}
	
	public ItemStack getItem() {
		ItemStack staff = new ItemStack(Material.TRIDENT);
		ItemMeta meta = staff.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_BLUE + "Staff of Doom");
		
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "The staff of the ones who asked more than they could handle");
		lore.add(ChatColor.GREEN + "(LeftClick)" + ChatColor.GRAY + "Shoots fireballs");
		lore.add(ChatColor.GREEN + "(RightClick)" + ChatColor.GRAY + "Summons minions");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LOYALTY, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		staff.setItemMeta(meta);
		return staff;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT))
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = e.getPlayer();
				if (e.getAction() == Action.RIGHT_CLICK_AIR) {
					if (!list.contains(player.getName()))
						list.add(player.getName());
					return ;
				}
				if (e.getAction() == Action.LEFT_CLICK_AIR) {
					player.launchProjectile(Fireball.class);
				}
			}
		if (list.contains(e.getPlayer().getName())) {
			list.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onLand(ProjectileHitEvent e) {
		if (e.getEntityType() == EntityType.TRIDENT) {
			if (e.getEntity().getShooter() instanceof Player) {
				Player player = (Player)e.getEntity().getShooter();
				if (list.contains(player.getName())) {
					Location loc = e.getEntity().getLocation();
					loc.setY(loc.getY() + 1);
					for (int i = 0; i < 4; i++) {
						loc.getWorld().spawnEntity(loc, EntityType.DROWNED);
						loc.setX(loc.getX() + 1);
					}
				}
			}
		}
	}
}
