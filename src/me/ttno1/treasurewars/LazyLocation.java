package me.ttno1.treasurewars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class LazyLocation implements ConfigurationSerializable {

	private float x;
	private float y;
	private float z;
	private float yaw;
	private float pitch;
	private String worldName;
	
	public LazyLocation(float x, float y, float z, float yaw, float pitch, String worldName) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.worldName = worldName;
		
	}
	
	public LazyLocation(Location location) {
		
		this.x = (float) location.getX();
		this.y = (float) location.getY();
		this.z = (float) location.getZ();
		this.yaw = (float) location.getYaw();
		this.pitch = (float) location.getPitch();
		this.worldName = location.getWorld().getName();
		
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public World getWorld() {
		return Bukkit.getWorld(worldName);
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	@Override
	public Map<String, Object> serialize() {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		map.put("z", z);
		map.put("yaw", yaw);
		map.put("pitch", pitch);
		map.put("world", worldName);
		return map;
		
	}
	
	public Location toLocation() {
		
		return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
		
	}
	
	public static Location toLocation(LazyLocation lazyLoc) {
		
		return lazyLoc.toLocation();
		
	}
	
	public static LazyLocation deserialize(Map<String, Object> map) {
		
		return new LazyLocation((float) ((double) map.get("x")), (float) ((double) map.get("y")), (float) ((double) map.get("z")), (float) ((double) map.get("yaw")), (float) ((double) map.get("pitch")), (String) map.get("world"));
		
	}
	
}
