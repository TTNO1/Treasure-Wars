package me.ttno1.treasurewars;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private static Main plugin;
	private static ArrayList<Game> games;
	private static ArrayList<Setup> setups;
	private UpdateChecker updateChecker = new UpdateChecker(78867);
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		if(!updateChecker.isUpToDate()) {
			Main.getPlugin().getLogger().warning("Treasure Wars is out of date!  Upgrade to the newest version on spigotmc.org for the latest features and bug fixes.");
		}
		
		ConfigurationSerialization.registerClass(LazyLocation.class);
		ConfigurationSerialization.registerClass(Ore.class);
		ConfigurationSerialization.registerClass(GameTeam.class);
		ConfigurationSerialization.registerClass(Area.class);
		ConfigurationSerialization.registerClass(Merchant.class);
		ConfigurationSerialization.registerClass(Captain.class);
		ConfigurationSerialization.registerClass(LobbyEntity.class);
		
		File dataFolder = this.getDataFolder();
		if(!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		
		plugin.saveDefaultConfig();
		
		File gamesFolder = new File(this.getDataFolder() + File.separator + "games");
		if(!gamesFolder.exists()) {
			gamesFolder.mkdir();
		}
		
		File worldsFolder = new File(this.getDataFolder() + File.separator + "worlds");
		if(!worldsFolder.exists()) {
			worldsFolder.mkdir();
		}
		
		File statsFile = new File(this.getDataFolder() + File.separator + "playerStats.yml");
		if(!statsFile.exists()) {
			try {
				statsFile.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().info("Error while creating player stats file.");
				e.printStackTrace();
			}
		}
		
		File[] gameFiles = gamesFolder.listFiles();
		games = new ArrayList<Game>();
		setups = new ArrayList<Setup>();
		
		for (File file : gameFiles) {
			String fileName = file.getName();
			String fileNameNE = fileName.substring(0, fileName.lastIndexOf("."));
			new Game(fileNameNE);
			plugin.getLogger().info("Registered Game: " + fileNameNE);
		}
		
		this.getCommand("tw").setExecutor(new MainCommand());
		
		this.getCommand("tw").setTabCompleter(new MainCommandTabComplete());
		
		if(this.getConfig().getBoolean("enableLeaveCommand")) {
			this.getCommand("leave").setExecutor(new LeaveCommand());
		}
		
		MainPlayerInteractEntityListener mainPlayerInteractEntityListener = new MainPlayerInteractEntityListener();
		Bukkit.getServer().getPluginManager().registerEvents(mainPlayerInteractEntityListener, plugin);
		
		PlayerDisconnectListener playerDisconnectListener = new PlayerDisconnectListener();
		Bukkit.getServer().getPluginManager().registerEvents(playerDisconnectListener, plugin);
		
		MainPlayerInteractListener mainPlayerInteractListener = new MainPlayerInteractListener();
		Bukkit.getServer().getPluginManager().registerEvents(mainPlayerInteractListener, plugin);
		
		MainInventoryClickListener mainInventoryClickListener = new MainInventoryClickListener();
		Bukkit.getServer().getPluginManager().registerEvents(mainInventoryClickListener, plugin);
		
		MainDropItemListener mainDropItemListener = new MainDropItemListener();
		Bukkit.getServer().getPluginManager().registerEvents(mainDropItemListener, plugin);
		
	}
	
	@Override
	public void onDisable() {
		
		for(Game game : games) {
			if(game.getLobbyEntity() != null) {
				game.getLobbyEntity().remove();
			}
		}
		
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
	public static Game getGame(String name) {
		
		for(Game game : games) {
			if(game.getName().equals(name)) {
				return game;
			}
		}
		
		return null;
		
	}
	
	public static ArrayList<Game> getGames() {
		return games;
	}
	
	public static Game getGameOf(Player player) {
		
		for(Game game : games) {
			if(game.getPlayers().contains(player)) {
				return game;
			}
		}
		return null;
				
	}
	
	public static ArrayList<Setup> getSetups() {
		
		return setups;
		
	}
	
	public static Setup getSetupOf(Player player) {
		
		for(Setup setup : setups) {
			if(setup.getPlayer().equals(player)) {
				return setup;
			}
		}
		
		return null;
		
	}
	
}
