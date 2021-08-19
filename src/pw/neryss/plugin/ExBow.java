package pw.neryss.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ExBow implements CommandExecutor, Listener {
		
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (label.equalsIgnoreCase("exbow")) {
			if (args[0].equalsIgnoreCase("give")) {
				if (sender instanceof Player) {
					
				}
			}
		}
		return false;
	}
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
	}
}
