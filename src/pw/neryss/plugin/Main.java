package pw.neryss.plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void	onEnable() {
		// startup
		// reloads
		// plugin reload
		System.out.println("Hello!");
		this.getCommand("launch").setExecutor(new Fly());
		this.getCommand("Doctor").setExecutor(new Heal());
	}
	
	@Override
	public void onDisable() {
		// shutdown and same as above
	}
}
