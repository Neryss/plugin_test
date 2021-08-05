package pw.neryss.plugin;
import org.bukkit.event.Listener;
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
		this.getCommand("starstaff").setExecutor(new Staff());
		this.getCommand("skull").setExecutor(new Skulls());
		this.getServer().getPluginManager().registerEvents(new Boots(), this);
		this.getServer().getPluginManager().registerEvents(new Staff(), this);
	}
	
	@Override
	public void onDisable() {
		// shutdown and same as above
	}
}