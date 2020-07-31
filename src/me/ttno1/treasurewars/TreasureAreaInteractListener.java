package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class TreasureAreaInteractListener implements Listener{

	private Player player;
	private GameTeam team;
	private Vector coord1;
	private Vector coord2;
	
	TreasureAreaInteractListener(Player player, GameTeam team){
		
		this.player = player;
		this.team = team;
		player.sendMessage(ChatColor.DARK_PURPLE + "Please right-click on the corners of the Treasure Area.");
		
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onInteract(PlayerInteractEvent event) {
		
		if(event.getPlayer().equals(player)) {
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(event.getHand().equals(EquipmentSlot.HAND)) {
					if(coord1 == null) {
						coord1 = event.getClickedBlock().getLocation().toVector();
						event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "First corner set.");
					}else {
						coord2 = event.getClickedBlock().getLocation().toVector();
						team.setTreasureArea(new Area(coord1, coord2, event.getClickedBlock().getWorld().getName()));
						event.getPlayer().sendMessage(ChatColor.GREEN + "Treasure area has been set successfully.");
						clear(false);
					}
				}
			}
		
		}
	}
	
	public void clear(boolean msg) {
		
		if(msg) {
			player.sendMessage(ChatColor.RED + "Treasure Area selection cancelled.");
		}
		
		HandlerList.unregisterAll(this);
		team.getGame().getTreasureAreaListeners().remove(this);
		
	}
	
	public Player getPlayer() {
		
		return player;
		
	}
	
}
