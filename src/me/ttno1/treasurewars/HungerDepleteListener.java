package me.ttno1.treasurewars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerDepleteListener implements Listener {

	private Game game;
	
	public HungerDepleteListener(Game game) {
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
    public void onHungerDeplete(FoodLevelChangeEvent event) {
		if(event.getEntity().getWorld().equals(game.getWorld())) {
			event.setCancelled(true);
		}
	}
}
