package pw.neryss.plugin;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	private static TeamsGui t_gui;
	private static VoidLily v_lily;
	@Override
	public void	onEnable() {
		// startup
		// reloads
		// plugin reload
		System.out.println("Hello!");
		initThis();
	}
	
	@Override
	public void onDisable() {
		// shutdown and same as above
	}
	
	public void	initThis() {
		v_lily = new VoidLily();
		this.getCommand("launch").setExecutor(new Fly());
		this.getCommand("Doctor").setExecutor(new Heal());
		this.getCommand("boots").setExecutor(new Boots());
		this.getCommand("starstaff").setExecutor(new Staff());
		this.getCommand("skull").setExecutor(new Skulls());
		this.getCommand("changeteam").setExecutor(new TeamsGui());
		this.getCommand("lily").setExecutor(v_lily);
		this.getServer().getPluginManager().registerEvents(new Boots(), this);
		this.getServer().getPluginManager().registerEvents(new Staff(), this);
		this.getServer().getPluginManager().registerEvents(new TeamsGui(), this);
		this.getServer().getPluginManager().registerEvents(new Deaths(), this);
		this.getServer().getPluginManager().registerEvents(v_lily, this);
		t_gui = new TeamsGui();
		t_gui.createInv();
	}
}