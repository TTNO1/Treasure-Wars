package me.ttno1.treasurewars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class LobbyEntity implements ConfigurationSerializable{

	private Game game;
	private Villager villager;
	private ArmorStand armorStand;
	private String name;
	private LazyLocation location;
	
	LobbyEntity(Game game, LazyLocation location, String name){
		
		this.game = game;
		this.location = location;
		this.name = name;
		
		villager = (Villager) location.getWorld().spawnEntity(location.toLocation(), EntityType.VILLAGER);
		updateStatus();
		villager.setCustomNameVisible(true);
		villager.setInvulnerable(true);
		villager.setCollidable(false);
		villager.addPotionEffect(Utils.slowness);
		villager.setGravity(false);
		villager.setRemoveWhenFarAway(false);
		
		armorStand = (ArmorStand) location.getWorld().spawnEntity(location.toLocation().clone().add(0, .33, 0), EntityType.ARMOR_STAND);
		armorStand.setCustomName(this.name);
		armorStand.setCustomNameVisible(true);
		armorStand.setInvulnerable(true);
		armorStand.setCollidable(false);
		armorStand.setGravity(false);
		armorStand.setRemoveWhenFarAway(false);
		armorStand.setVisible(false);
		
	}
	
	public static LobbyEntity deserialize(Map<String, Object> map) {
		
		return new LobbyEntity(Main.getGame((String) map.get("game")), (LazyLocation) map.get("location"), (String) map.get("name"));
		
	}
	
	public String getName() {
		return name;
	}
	
	public void remove() {
		villager.remove();
		armorStand.remove();
	}
	
	public boolean isEntity(Entity entity) {
		
		if(entity.equals(villager) || entity.equals(armorStand)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void updateStatus() {
		
		String status;
		
		switch(game.getState()) {
		case ACTIVE:
			status = "In-Game";
			break;
		case DISABLED:
			status = ChatColor.RED + "Disabled";
			break;
		case QUEUE:
			status = "In-Queue";
			break;
		case RESETING:
			status = "Reseting...";
			break;
		default:
			status = null;
			break;
		}
		
		villager.setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD + "Status: " + ChatColor.RESET + status);
		
	}

	@Override
	public HashMap<String, Object> serialize() {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("location", location);
		map.put("name", name);
		map.put("game", game.getName());
		
		return map;
	}
	
}
