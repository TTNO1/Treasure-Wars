package me.ttno1.treasurewars;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener{
	
	private Game game;
	
	BlockBreakListener(Game game){
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getWorld().equals(game.getWorld())) {
			
			if(!event.getBlock().hasMetadata("tw")) {
				
				event.setCancelled(true);
				
			}
			
			if(game.isOreBlock(event.getBlock())) {
				
				game.getOre(event.getBlock()).breakOre(event.getPlayer());
				
			}
			
		}
	}

}
