package me.ttno1.treasurewars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnectListener implements Listener{

	@EventHandler (ignoreCancelled = true)
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		
		if(Main.getGameOf(event.getPlayer()) != null) {
			Main.getGameOf(event.getPlayer()).leaveGame(event.getPlayer());
		}
		
	}
}
