package me.ttno1.treasurewars;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

	private Game game;
	
	EntitySpawnListener(Game game) {
		
		this.game = game;
		
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onEntityExplode(EntitySpawnEvent event) {
		if(event.getLocation().getWorld().equals(game.getWorld())) {
			
			if(event.getEntityType().equals(EntityType.PARROT)) {
				event.setCancelled(true);
			}
			
		}
	}
	
}
