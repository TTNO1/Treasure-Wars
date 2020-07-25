package me.ttno1.treasurewars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MainDropItemListener implements Listener{
	
	@EventHandler (ignoreCancelled = true)
    public void onInventoryClick(PlayerDropItemEvent event) {
		if(Main.getGameOf((Player) event.getPlayer()) != null) {
			if(Main.getGameOf((Player) event.getPlayer()).getState().equals(Game.GameState.QUEUE)) {
				event.setCancelled(true);
			}
		}
	}
	
}
