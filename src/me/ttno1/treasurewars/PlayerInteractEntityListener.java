package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntityListener implements Listener{

	Game game;
	
	public PlayerInteractEntityListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		
		if(event.getRightClicked().getWorld().equals(game.getWorld())) {
			if(event.getHand().equals(EquipmentSlot.HAND)) {
				if(event.getRightClicked().getType().equals(EntityType.VILLAGER) && event.getRightClicked().getName().equals(ChatColor.GOLD + "Merchant")) {
					event.setCancelled(true);
					Merchant.openGui(event.getPlayer(), Merchant.MerchantPage.QUICK_BUY);
				}
				if(event.getRightClicked().getType().equals(EntityType.VILLAGER) && event.getRightClicked().getName().equals(ChatColor.AQUA + "Captain")) {
					event.setCancelled(true);
					Captain.openGui(event.getPlayer(), game.getTeamOf(event.getPlayer()));
				}
			}
		}
	}
	
}
