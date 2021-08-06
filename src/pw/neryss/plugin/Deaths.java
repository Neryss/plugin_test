package pw.neryss.plugin;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Deaths implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity().getPlayer();
		World pword = player.getWorld();
		Skulls skull = new Skulls();
		pword.dropItem(player.getLocation(), skull.getPlayerHead(player.getName()));
	}
}
