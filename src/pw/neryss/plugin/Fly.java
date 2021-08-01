package pw.neryss.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		System.out.println("Entered function");
		if (label.equalsIgnoreCase("hello")) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (player.hasPermission("hello.use")) {
					player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcum");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1H&2a&3v&4e fun!"));
					return (true);
				}
				else {
					player.sendMessage(ChatColor.RED + "You don't have the permission to execute this command");
					return true;
				}
			}
			else {
				sender.sendMessage("Hey console");
				return true;
			}
		}
		else if (label.equalsIgnoreCase("launch") || label.equalsIgnoreCase("lch")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("*console goes vroom*");
				return true;
			}
			Player player = (Player)sender;
			if (args.length == 0) {
				player.sendMessage("Squalala");
				player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
				return true;
			}
			if (isNum(args[0])) {
				player.sendMessage("Squalala");
				player.setVelocity(player.getLocation().getDirection().multiply(Integer.parseInt(args[0])).setY(2));
				return true;				
			}
			else
				player.sendMessage(ChatColor.RED + "Usage: /launch <number-value>");
		}
		return false;
	}
	
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
}
