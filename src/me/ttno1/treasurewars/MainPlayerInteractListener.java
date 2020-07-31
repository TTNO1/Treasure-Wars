package me.ttno1.treasurewars;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MainPlayerInteractListener implements Listener{
	
	@EventHandler (ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
		if(Main.getGameOf(event.getPlayer()) != null) {
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(Main.getGameOf(event.getPlayer()).getState().equals(Game.GameState.QUEUE)) {
					if(event.getItem().getType().equals(Material.RED_BED) && event.getItem().getItemMeta().getDisplayName().equals("Leave Game")) {
						event.setCancelled(true);
						Main.getGameOf(event.getPlayer()).leaveGame(event.getPlayer());
					}
					if(event.getItem().getType().equals(Material.MAP) && event.getItem().getItemMeta().getDisplayName().equals("Select Team")) {
						event.setCancelled(true);
						Main.getGameOf(event.getPlayer()).selectTeam(event.getPlayer());
					}
				}
			}
		}
	}
	
}
