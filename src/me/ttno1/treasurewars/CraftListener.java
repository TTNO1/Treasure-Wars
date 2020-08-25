package me.ttno1.treasurewars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftListener implements Listener{
	
	private Game game;
	
	public CraftListener(Game game){
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onCraft(CraftItemEvent event) {
		if(event.getWhoClicked().getWorld().equals(game.getWorld())) {
			
			event.setCancelled(true);
			
		}
	}
	
}
