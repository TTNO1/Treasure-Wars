package me.ttno1.treasurewars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

public class Area implements ConfigurationSerializable{

	private Vector minCoord;
	private Vector maxCoord;
	private String worldName;
	private Vector center;
	
	Area(Vector coord1, Vector coord2, String worldName){
		
		double minX = Math.min(coord1.getX(), coord2.getX());
		double minY = Math.min(coord1.getY(), coord2.getY());
		double minZ = Math.min(coord1.getZ(), coord2.getZ());
		
		double maxX = Math.max(coord1.getX(), coord2.getX());
		double maxY = Math.max(coord1.getY(), coord2.getY());
		double maxZ = Math.max(coord1.getZ(), coord2.getZ());
		
		minCoord = new Vector(minX, minY, minZ);
		maxCoord = new Vector(maxX, maxY, maxZ);
		
		center = new Vector((maxX + minX)/2, (maxY + minY)/2, (maxZ + minZ)/2);
		
		this.worldName = worldName;
		
	}
	
	public static Area deserialize(Map<String, Object> map){
		
		return new Area((Vector) map.get("minCoord"), (Vector) map.get("maxCoord"), (String) map.get("world"));
		
	}
	
	public boolean isIn(Location location) {
		
		if(location.getWorld().equals(Bukkit.getWorld(worldName))) {
			if((location.getX() >= minCoord.getX() && location.getX() <= maxCoord.getX()) && (location.getY() >= minCoord.getY() && location.getY() <= maxCoord.getY()) && (location.getZ() >= minCoord.getZ() && location.getZ() <= maxCoord.getZ())) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}
	
	public Vector getCenter() {
		return center;
	}

	@Override
	public HashMap<String, Object> serialize() {
		
		HashMap<String, Object> serializeMap = new HashMap<String, Object>();
		serializeMap.put("minCoord", minCoord);
		serializeMap.put("maxCoord", maxCoord);
		serializeMap.put("world", worldName);
		return serializeMap;
		
	}
	
}
