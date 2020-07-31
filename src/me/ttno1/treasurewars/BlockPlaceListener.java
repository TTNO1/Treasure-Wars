package me.ttno1.treasurewars;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener implements Listener{
	
	private Game game;
	
	BlockPlaceListener(Game game){
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getBlockPlaced().getWorld().equals(game.getWorld())) {
			
			event.getBlockPlaced().setMetadata("tw", new FixedMetadataValue(Main.getPlugin(), 1));
		
			if(event.getBlock().getType().equals(Material.TNT)) {
				event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
				event.setCancelled(true);
			}
			
		}
	}
	
}
