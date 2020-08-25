package me.ttno1.treasurewars;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MainPlayerInteractListener implements Listener{
	
	@EventHandler (ignoreCancelled = false)
    public void onPlayerInteract(PlayerInteractEvent event) {
		Main.getPlugin().getLogger().info("Event Fired");
		if(event.getItem() != null) {
			Main.getPlugin().getLogger().info(event.getItem().getItemMeta().getDisplayName());
			if(Main.getGameOf(event.getPlayer()) != null) {
				Main.getPlugin().getLogger().info("Player has game");
				if(Main.getGameOf(event.getPlayer()).getState().equals(Game.GameState.QUEUE)) {
					Main.getPlugin().getLogger().info("State is queue");
					if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						Main.getPlugin().getLogger().info("Action");
						if(event.getHand().equals(EquipmentSlot.HAND)) {
							Main.getPlugin().getLogger().info("Hand");
							if(event.getItem().getType().equals(Material.RED_BED) && event.getItem().getItemMeta().getDisplayName().equals("§rLeave Game")) {
								Main.getPlugin().getLogger().info("Item is bed");
								event.setCancelled(true);
								Main.getGameOf(event.getPlayer()).leaveGame(event.getPlayer());
							}
							if(event.getItem().getType().equals(Material.MAP) && event.getItem().getItemMeta().getDisplayName().equals("§rSelect Team")) {
								Main.getPlugin().getLogger().info("Item is Map");
								event.setCancelled(true);
								Main.getGameOf(event.getPlayer()).selectTeam(event.getPlayer());
							}
						}
					}
				}
			}
		}
	}
	
}
