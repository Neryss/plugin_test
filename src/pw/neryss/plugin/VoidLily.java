package pw.neryss.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VoidLily implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (checkFlower(e)) {
			e.setDropItems(false);
			Random r = new Random();
			int res = r.nextInt(100);
			if (res <= 15) {
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
		lore.add(ChatColor.GOLD + "(Right click) Teleports you to the aimed position");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		voidLily.setItemMeta(meta);
		return voidLily;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.LILY_OF_THE_VALLEY))
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player)e.getPlayer();
				if (e.getAction() == Action.RIGHT_CLICK_AIR) {
					Block block = player.getTargetBlock(null, 40);
					player.teleport(block.getLocation());
					player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 5);
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
