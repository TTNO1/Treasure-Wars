package me.ttno1.treasurewars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class ItemDamageListener implements Listener {

	private Game game;
	
	public ItemDamageListener(Game game) {
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
    public void onItemDamage(PlayerItemDamageEvent event) {
		if(event.getPlayer().getWorld().equals(game.getWorld())) {
			event.setCancelled(true);
		}
	}
}
