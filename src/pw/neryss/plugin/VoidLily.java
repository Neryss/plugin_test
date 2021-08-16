package pw.neryss.plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;

public class VoidLily implements CommandExecutor, Listener {
	Map<String, Long> cooldowns = new HashMap<String, Long>();
	boolean	_activated = true;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("lily")) {
			System.out.println("aled");
			if (sender instanceof Player) {
				Player player = (Player)sender;
			if (args[0].equalsIgnoreCase("disable")) {
				player.sendMessage("Lily disabled" + _activated);
				_activated = false;
				player.sendMessage("Lily disabled" + _activated);
				return true;
			}
			else if (args[0].equalsIgnoreCase("enable")) {
				player.sendMessage("Lily enabled");
				_activated = true;
				return true;
			}
			else if (args[0].equalsIgnoreCase("forcegive")) {
					if (player.hasPermission("voidlily.use")) {
						if (player.getInventory().firstEmpty() == -1) {
							World world = player.getWorld();
							world.dropItem(player.getLocation(), getItem());
							player.sendMessage(ChatColor.GOLD + "You received your lily");
							return true;							
						}
						else {
							player.getInventory().addItem(getItem());
							player.sendMessage(ChatColor.GOLD + "You received your lily");
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (checkFlower(e) && _activated) {
			System.out.println("activated " + _activated);
			Random r = new Random();
			int res = r.nextInt(100);
			if (res <= 15) {
				e.setDropItems(false);
				if (e.getPlayer().getInventory().firstEmpty() == -1) {
					World world = e.getPlayer().getWorld();
					world.dropItem(e.getPlayer().getLocation(), getItem());
					e.getPlayer().sendMessage(ChatColor.BLUE + "" + ChatColor.ITALIC + "It seems something appeared beneath your feet...");
				}
				e.getPlayer().getInventory().addItem(getItem());
				e.getPlayer().sendMessage(ChatColor.BLUE + "Something appeared in your hands...");
			}
		}
	}
	
	public ItemStack getItem() {
		ItemStack voidLily = new ItemStack(Material.LILY_OF_THE_VALLEY);
		ItemMeta meta = voidLily.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Void Lily");
		
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.DARK_BLUE + "You've been blessed with a special lily");
		lore.add(ChatColor.GOLD + "(Right click) Teleports you to a player");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		voidLily.setItemMeta(meta);
		return voidLily;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.LILY_OF_THE_VALLEY)
				&& !e.getHand().equals(EquipmentSlot.OFF_HAND))
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					e.setCancelled(true);
					Location pLoc = e.getPlayer().getEyeLocation();
					float yaw = e.getPlayer().getEyeLocation().getYaw();
					float pitch = e.getPlayer().getEyeLocation().getPitch();
					List<BoundingBox> allBBs = Bukkit.getOnlinePlayers().stream()
							.filter(player -> !player.equals(e.getPlayer()))
							.sorted(Comparator.comparingDouble(player -> player.getLocation().distanceSquared(pLoc)))
							.map(Entity::getBoundingBox)
							.collect(Collectors.toList());
					org.bukkit.util.Vector pdir = pLoc.getDirection();
					for (double d = 0; d < 50; d += 0.001) {
						org.bukkit.util.Vector ray = pLoc.clone().toVector().add(pdir.clone().multiply(d));
						for (BoundingBox BBs : allBBs) {
							if (BBs.overlaps(ray, ray)) {
								if (cooldowns.containsKey(e.getPlayer().getName())) {
									if (cooldowns.get(e.getPlayer().getName()) > System.currentTimeMillis()) {
										long remain = (cooldowns.get(e.getPlayer().getName()) - System.currentTimeMillis()) / 1000;
										e.getPlayer().sendMessage(ChatColor.RED + "Remaining cooldowns: " + remain + "s");
										return ;
									}
								}
								cooldowns.put(e.getPlayer().getName(), System.currentTimeMillis() + (5 * 1000));
								Location tpLoc = ray.toLocation(e.getPlayer().getWorld());
								tpLoc.setYaw(yaw);
								tpLoc.setPitch(pitch);
								e.getPlayer().teleport(tpLoc);
								e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 5);
								break ;
							}
						}
					}
					
				}
			}
	}
	
	boolean checkFlower(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.LILY_OF_THE_VALLEY || e.getBlock().getType() == Material.DANDELION
				|| e.getBlock().getType() == Material.POPPY || e.getBlock().getType() == Material.BLUE_ORCHID
				|| e.getBlock().getType() == Material.ALLIUM || e.getBlock().getType() == Material.AZURE_BLUET
				|| e.getBlock().getType() == Material.RED_TULIP || e.getBlock().getType() == Material.ORANGE_TULIP
				|| e.getBlock().getType() == Material.WHITE_TULIP || e.getBlock().getType() == Material.PINK_TULIP
				|| e.getBlock().getType() == Material.OXEYE_DAISY || e.getBlock().getType() == Material.CORNFLOWER
				|| e.getBlock().getType() == Material.WITHER_ROSE || e.getBlock().getType() == Material.SUNFLOWER
				|| e.getBlock().getType() == Material.LILAC || e.getBlock().getType() == Material.ROSE_BUSH
				|| e.getBlock().getType() == Material.PEONY)
			return true;
		return false;
	}
}
