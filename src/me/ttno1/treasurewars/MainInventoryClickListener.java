package me.ttno1.treasurewars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainInventoryClickListener implements Listener{
	
	@EventHandler (ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
		if(Main.getGameOf((Player) event.getWhoClicked()) != null) {
			if(Main.getGameOf((Player) event.getWhoClicked()).getState().equals(Game.GameState.QUEUE)) {
				if(event.getView().getTitle().equals("Team Selection")) {
					Player player = (Player) event.getWhoClicked();
					Game game = Main.getGameOf(player);
					for(int i = 0; i < game.getTeams().size(); i++) {
						GameTeam team = game.getTeams().get(i);
						if(event.getSlot() == i) {
							game.getTeamSelection().put(player, team);
							player.closeInventory();
							break;
						}
					}
					event.setCancelled(true);
				}
			}
		}
	}
	
}
