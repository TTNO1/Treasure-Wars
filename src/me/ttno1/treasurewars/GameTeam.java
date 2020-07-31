package me.ttno1.treasurewars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.ttno1.treasurewars.Game.EndReason;

public class GameTeam implements ConfigurationSerializable{
	
	private String name;
	private String color;
	private Game game;
	private LazyLocation spawn;
	private Area treasureArea;
	private ChatColor chatColor;
	private ArrayList<Player> players;
	private HashMap<Captain.Upgrade, Enchantment> upgradeEnchants;
	private HashMap<Captain.Upgrade, Integer> upgradeLevels;
	private int points;
	private DyeColor dyeColor;
	
	GameTeam(String name, String color, Game game){
		
		this.name = name;
		this.color = color.toUpperCase();
		this.game = game;
		points = 0;
		
		dyeColor = DyeColor.valueOf(color);
		chatColor = Utils.getChatColor(dyeColor);
		players = new ArrayList<Player>();
		upgradeLevels = new HashMap<Captain.Upgrade, Integer>();
		upgradeEnchants = new HashMap<Captain.Upgrade, Enchantment>();
		
		for(Captain.Upgrade upgrade : Captain.Upgrade.values()) {
			upgradeLevels.put(upgrade, 0);
			upgradeEnchants.put(upgrade, null);
		}
		
	}
	
	public static GameTeam deserialize(Map<String, Object> map){
		
		return new GameTeam((String) map.get("name"), (String) map.get("color"), Main.getGame((String) map.get("game")));
		
	}

	//Basic get and set
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public Game getGame() {
		return game;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public Area getTreasureArea() {
		return treasureArea;
	}
	
	public void setTreasureArea(Area area){
		treasureArea = area;
		game.getConfig().set("teams." + name + ".treasureArea", area);
	}
	
	public int getUpgradeLevel(Captain.Upgrade upgrade) {
		return upgradeLevels.get(upgrade);
	}
	
	public Enchantment getUpgradeEnchant(Captain.Upgrade upgrade) {
		return upgradeEnchants.get(upgrade);
	}
	
	public LazyLocation getSpawn() {
		return spawn;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int number) {
		points = number;
	}
	
	public ChatColor getChatColor() {
		return chatColor;
	}
	
	public DyeColor getDyeColor() {
		return dyeColor;
	}
	
	//--
	
	public void addPoints(int number) {
		
		if(points + number < game.getMaxPoints()) {
			points = points + number;
		}else {
			points = game.getMaxPoints();
			game.endGame(EndReason.WIN);
		}
		
	}
	
	public int takePoints(int number) {
		
		if(number > points) {
			int ingotsTaken = (int) Math.floor(points / 20);
			points = points % 20;
			return ingotsTaken;
		}else {
			points = points - number;
			return number / 20;
		}
		
	}
	
	public void clear() {
		
		players.clear();
		points = 0;
		
		for(Captain.Upgrade upgrade : Captain.Upgrade.values()) {
			upgradeLevels.put(upgrade, 0);
			upgradeEnchants.put(upgrade, null);
		}
		
	}
	
	public void spawnArmorStand() {
		
		ArmorStand armorStand = (ArmorStand) game.getWorld().spawnEntity(treasureArea.getCenter().toLocation(game.getWorld()).subtract(0, 1, 0), EntityType.ARMOR_STAND);
		armorStand.setVisible(false);
		armorStand.setInvulnerable(true);
		armorStand.setCustomNameVisible(true);
		armorStand.setGravity(false);
		armorStand.setCustomName(chatColor + "" + ChatColor.BOLD + name + ChatColor.BOLD + "" + ChatColor.WHITE + " Treasure");
	
	}
	
	public String setSpawn(LazyLocation spawn){
		
		if(spawn.getWorld().equals(game.getWorld())) {
			this.spawn = spawn;
			game.getConfig().set("teams." + name + ".spawn", spawn);
			return ChatColor.GREEN + "Successfully set spawn of team: " + chatColor + name + ChatColor.GREEN + " to your location.";
		}else {
			return ChatColor.RED + "You are in the wrong world, please go to world: " + ChatColor.WHITE + game.getWorld().getName() + ChatColor.RED + " to set the spawn of this team.";
		}
		
	}
	
	public boolean upgrade(Captain.Upgrade upgrade) {
		
		if(upgradeLevels.get(upgrade) < upgrade.getMaxLevel()) {
			
			upgradeLevels.put(upgrade, upgradeLevels.get(upgrade) + 1);
			
			if(upgradeLevels.get(upgrade) == 1) {
				upgradeEnchants.put(upgrade, upgrade.getEnchant());
			}
			
			return true;
			
		}else {
			return false;
		}
		
	}
	
	public boolean addPlayer(Player player) {
		
		if(players.size() < game.getPlayersPerTeam()) {
			players.add(player);
			return true;
		}else {
			return false;
		}
		
	}
	
	public String selectTreasureArea(Player player){
		
		if(player.getWorld().equals(game.getWorld())) {
			Bukkit.getServer().getPluginManager().registerEvents(new TreasureAreaInteractListener(player, this), Main.getPlugin());
			return ChatColor.DARK_PURPLE + "Please right click the corner blocks of the treasure area.";
		}else {
			return ChatColor.RED + "You are in the wrong world, please go to world: " + ChatColor.WHITE + game.getWorld().getName() + ChatColor.RED + " to set the treasure area of this team.";
		}
		
	}
	
	@Override
	public HashMap<String, Object> serialize() {
		
		HashMap<String, Object> serializeMap = new HashMap<String, Object>();
		serializeMap.put("name", name);
		serializeMap.put("color", color);
		serializeMap.put("game", game.getName());
		serializeMap.put("spawn", spawn.serialize());
		serializeMap.put("treasureArea", treasureArea.serialize());
		return serializeMap;
		
	}
	
}
