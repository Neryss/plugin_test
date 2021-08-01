package pw.neryss.plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void	onEnable() {
		// startup
		// reloads
		// plugin reload
		System.out.println("Hello!");
	}
	
	@Override
	public void onDisable() {
		// shutdown and same as above
	}
	
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
		return false;
	}
}
