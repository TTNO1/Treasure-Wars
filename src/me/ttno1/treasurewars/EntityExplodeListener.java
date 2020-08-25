package me.ttno1.treasurewars;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

	private Game game;
	
	public EntityExplodeListener(Game game) {
		
		this.game = game;
		
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		if(event.getLocation().getWorld().equals(game.getWorld())) {
			
			for(Block block : event.blockList()) {
				if(!block.hasMetadata("tw") || block.getType().equals(Material.STONE_BRICKS)) {
					event.blockList().remove(block);
				}
			}
			
		}
	}
	
}
