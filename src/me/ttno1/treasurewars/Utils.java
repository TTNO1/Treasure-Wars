package me.ttno1.treasurewars;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Utils {
	
	public static PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true, false);
	
	public static DateTimeFormatter gameTimeFormat = DateTimeFormatter.ofPattern("mm:ss");
	
	private static HashMap<DyeColor, ChatColor> getChatColorMap = new HashMap<DyeColor, ChatColor>();
	
	static {
		
		getChatColorMap.put(DyeColor.BLACK, ChatColor.BLACK);
		getChatColorMap.put(DyeColor.BLUE, ChatColor.DARK_BLUE);
		getChatColorMap.put(DyeColor.CYAN, ChatColor.AQUA);
		getChatColorMap.put(DyeColor.GRAY, ChatColor.DARK_GRAY);
		getChatColorMap.put(DyeColor.GREEN, ChatColor.DARK_GREEN);
		getChatColorMap.put(DyeColor.LIGHT_BLUE, ChatColor.BLUE);
		getChatColorMap.put(DyeColor.LIGHT_GRAY, ChatColor.GRAY);
		getChatColorMap.put(DyeColor.LIME, ChatColor.GREEN);
		getChatColorMap.put(DyeColor.MAGENTA, ChatColor.DARK_PURPLE);
		getChatColorMap.put(DyeColor.ORANGE, ChatColor.GOLD);
		getChatColorMap.put(DyeColor.PINK, ChatColor.LIGHT_PURPLE);
		getChatColorMap.put(DyeColor.PURPLE, ChatColor.DARK_PURPLE);
		getChatColorMap.put(DyeColor.RED, ChatColor.DARK_RED);
		getChatColorMap.put(DyeColor.WHITE, ChatColor.WHITE);
		getChatColorMap.put(DyeColor.YELLOW, ChatColor.YELLOW);
		
	}
	
	public static ArrayList<String> lore(String ... lines){
		
		ArrayList<String> output = new ArrayList<String>();
		
		for(String line : lines) {
			output.add(line);
		}
		
		return output;
		
	}
	
	public static ItemStack customItem(Material material, int quantity, String name, ArrayList<String> lore, Enchantment enchant, int enchantLevel, PotionEffect potionEffect, boolean hideAttributes, boolean hideEnchants) {
		
		ItemStack item = new ItemStack(material, quantity);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + name);
		itemMeta.setLore(lore);
		if(enchant != null) {
			itemMeta.addEnchant(enchant, enchantLevel, true);
		}
		if(potionEffect != null) {
			PotionMeta potionMeta = (PotionMeta) itemMeta;
			potionMeta.addCustomEffect(potionEffect, false);
		}
		if(hideAttributes) {
			itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		if(hideEnchants) {
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(itemMeta);
		return item;
		
	}
	
	public static ChatColor getChatColor (DyeColor dyeColor) {
		
		return getChatColorMap.get(dyeColor);
		
	}
	
	public static Player getNearestPlayer(Location location) {
		
		Player nearestPlayer;
		
		if(location.getWorld().getPlayers().size() > 0) {
			nearestPlayer = location.getWorld().getPlayers().get(0);
		}else {
			nearestPlayer = null;
		}
		
		for(Player player : location.getWorld().getPlayers()) {
			if(location.distance(player.getLocation()) < location.distance(nearestPlayer.getLocation())) {
				nearestPlayer = player;
			}
		}
		
		return nearestPlayer;
		
	}
	
	public static Player getNearestPlayer(Entity entity, int apothem) {
		
		Player nearestPlayer;
		ArrayList<Player> nearbyPlayers = new ArrayList<Player>();
		
		for(Entity ent : entity.getNearbyEntities(apothem, apothem, apothem)) {
			if(ent instanceof Player) {
				nearbyPlayers.add((Player) ent);
			}
		}
		
		if(nearbyPlayers.size() > 0) {
			nearestPlayer = nearbyPlayers.get(0);
		}else {
			nearestPlayer = null;
		}
		
		for(Player player : nearbyPlayers) {
			if(entity.getLocation().distance(player.getLocation()) < entity.getLocation().distance(nearestPlayer.getLocation())) {
				nearestPlayer = player;
			}
		}
		
		return nearestPlayer;
		
	}
	
	public static void sendPacket(Player player, Object packet) throws Exception {
    	
		Object handle = player.getClass().getMethod("getHandle").invoke(player);
        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
    
	}

    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
       
    	String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return Class.forName("net.minecraft.server." + version + "." + name);
   
    }
    
    public static Class<?> getCraftbukkitClass(String name) throws ClassNotFoundException {
        
    	String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
   
    }
    
    public static void deleteFile(File file) {
    	
    	if(file.isDirectory()) {
    		
    		for(String fileName : file.list()) {
    			deleteFile(new File(file, fileName));
    		}
    		
    	}else {
    		
    		file.delete();
    		
    	}
    	
    }
    
    public static BlockFace yawToDirection(float yaw) {
    	
    	BlockFace direction;
    	
    	if(yaw >= 335 && yaw < 25) {
    		direction = BlockFace.SOUTH;
    	}else if(yaw >= 25 && yaw < 65) {
    		direction = BlockFace.SOUTH_WEST;
    	}else if(yaw >= 65 && yaw < 115) {
    		direction = BlockFace.WEST;
    	}else if(yaw >= 115 && yaw < 155) {
    		direction = BlockFace.NORTH_WEST;
    	}else if(yaw >= 155 && yaw < 205) {
    		direction = BlockFace.NORTH;
    	}else if(yaw >= 205 && yaw < 245) {
    		direction = BlockFace.NORTH_EAST;
    	}else if(yaw >= 245 && yaw < 295) {
    		direction = BlockFace.EAST;
    	}else if(yaw >= 295 && yaw < 335) {
    		direction = BlockFace.SOUTH_EAST;
    	}else {
    		direction = null;
    	}
    	
    	return direction;
    	
    }
	
}
