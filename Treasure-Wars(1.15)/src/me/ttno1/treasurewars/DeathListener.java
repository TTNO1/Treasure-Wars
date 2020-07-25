package me.ttno1.treasurewars;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {

	private Game game;
	
	DeathListener(Game game) {
		
		this.game = game;
		
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().getWorld().equals(game.getWorld())) {
			Player player = event.getEntity();
			
			if(player.getKiller() != null) {
				
				event.setDeathMessage(game.getTeamOf(player).getChatColor() + player.getName() + " was killed by " + game.getTeamOf(player.getKiller()).getChatColor() + player.getKiller().getName());
				game.addKill(player.getKiller());
				game.updateScoreboard(player.getKiller());
				
				for(ItemStack item : player.getInventory()) {
					if(item.getType().equals(Material.IRON_INGOT) || item.getType().equals(Material.GOLD_INGOT) || item.getType().equals(Material.DIAMOND)) {
						player.getKiller().getInventory().addItem(item);
					}
				}
				
			}else {
				
				event.setDeathMessage(game.getTeamOf(player).getChatColor() + player.getName() + " Died");
				
			}
			
			if(game.getEyeOfSalineOf(player) != null) {
				game.getEyeOfSalineOf(player).clear();
			}
			
			if(game.getDrownerOf(player) != null) {
				game.getDrownerOf(player).clear();
			}
			
		}
	}
	
}
