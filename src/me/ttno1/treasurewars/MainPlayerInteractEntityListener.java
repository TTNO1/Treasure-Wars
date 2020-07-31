package me.ttno1.treasurewars;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MainPlayerInteractEntityListener implements Listener{
	
	@EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof Villager) {
			if(event.getHand().equals(EquipmentSlot.HAND)) {
				for(Game game : Main.getGames()) {
					if(game.getLobbyEntity().isEntity(event.getRightClicked())) {
						event.setCancelled(true);
						game.joinGame(event.getPlayer());
						break;
					}
				}
			}
		}
	}
	
}
