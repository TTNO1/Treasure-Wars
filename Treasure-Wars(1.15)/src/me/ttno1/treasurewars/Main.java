package me.ttno1.treasurewars;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private static Main plugin;
	
	private static ArrayList<Game> games;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		plugin.saveDefaultConfig();
		
		ConfigurationSerialization.registerClass(Ore.class);
		ConfigurationSerialization.registerClass(GameTeam.class);
		ConfigurationSerialization.registerClass(Area.class);
		ConfigurationSerialization.registerClass(Merchant.class);
		ConfigurationSerialization.registerClass(Captain.class);
		
		File dataFolder = this.getDataFolder();
		if(!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		
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
			statsFile.mkdir();
		}
		
		File[] gameFiles = gamesFolder.listFiles();
		games = new ArrayList<Game>();
		
		for (File file : gameFiles) {
			String fileName = file.getName();
			String fileNameNE = fileName.substring(0, fileName.lastIndexOf("."));
			games.add(new Game(fileNameNE));
			plugin.getLogger().info("Registered Game: " + fileNameNE);
		}
		
		this.getCommand("tw").setExecutor(new MainCommand());
		
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
	
}
