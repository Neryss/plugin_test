package pw.neryss.plugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	@Override
	public void	onEnable() {
		// startup
		// reloads
		// plugin reload
		System.out.println("Hello!");
		this.getCommand("launch").setExecutor(new Fly());
		this.getCommand("Doctor").setExecutor(new Heal());
		this.getCommand("boots").setExecutor(new Boots());
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		// shutdown and same as above
	}
	
	@EventHandler
	public void onJump(PlayerMoveEvent e) {
		Player player = (Player)e.getPlayer();
		if (player.getInventory().getBoots() != null)
			if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of leaping"))
				if (player.getInventory().getBoots().getItemMeta().hasLore())
					if (e.getFrom().getY() < e.getTo().getY() &&
							player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
						player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
					}
	}
	
	@EventHandler
	public void onFall(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player)event.getEntity();
			if (event.getCause() == DamageCause.FALL)
				if (player.getInventory().getBoots() != null)
					if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of leaping"))
						if (player.getInventory().getBoots().getItemMeta().hasLore()) {
							event.setCancelled(true);
						}
		}
	}
}
