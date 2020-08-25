package me.ttno1.treasurewars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Ore implements ConfigurationSerializable{

	private LazyLocation location;
	private Material type;
	private Material ingot;
	private Game game;
	private int respawnTime;
	
	Ore(LazyLocation location, Material type, Game game){
		
		this.location = location;
		this.type = type;
		this.game = game;
		
		switch(type) {
		case IRON_ORE:
			ingot = Material.IRON_INGOT;
			respawnTime = Main.getPlugin().getConfig().getInt("ironRespawnTime");
			break;
		case GOLD_ORE:
			ingot = Material.GOLD_INGOT;
			respawnTime = Main.getPlugin().getConfig().getInt("goldRespawnTime");
			break;
		case DIAMOND_ORE:
			ingot = Material.DIAMOND;
			respawnTime = Main.getPlugin().getConfig().getInt("diamondRespawnTime");
			break;
		default:
			break;
		}
		
	}
	
	public LazyLocation getLocation() {
		return location;
	}
	
	public void breakOre(Player player) {
		
		game.getWorld().getBlockAt(location.toLocation()).setType(Material.BEDROCK);
		player.getInventory().addItem(new ItemStack(ingot, game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.FORTUNE) + 1));
		
		BukkitRunnable respawn = new BukkitRunnable() {
			@Override
			public void run() {
				game.getWorld().getBlockAt(location.toLocation()).setType(type);
			}
		};
		
		respawn.runTaskLater(Main.getPlugin(), respawnTime * 20);
		
	}

	//Serialization
	
	@Override
	public HashMap<String, Object> serialize() {
		
		HashMap<String, Object> serializeMap = new HashMap<String, Object>();
		serializeMap.put("type", type.toString());
		serializeMap.put("game", game.getName());
		serializeMap.put("location", location);
		return serializeMap;
		
	}
	
	public static Ore deserialize(Map<String, Object> map){
		
		return new Ore((LazyLocation) map.get("location"), Material.valueOf((String) map.get("type")), Main.getGame((String) map.get("game")));
		
	}
	
}
