package me.ttno1.treasurewars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OreBreakListener implements Listener{
	
	Game game;
	Player player;
	
	OreBreakListener(Game game, Player player){
		this.game = game;
		this.player = player;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getPlayer().equals(player)) {
			if(event.getBlock().getWorld().equals(game.getWorld())) {
				if(game.isOreBlock(event.getBlock())) {
					game.getOres().remove(game.getOre(event.getBlock()));
					game.getConfig().set("ores", game.getOres());
					game.saveConfig();
					player.sendMessage(event.getBlock().getType().toString() + " has been removed.");
				}
			}
		}
	}

}
