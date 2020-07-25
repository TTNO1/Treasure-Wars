package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OrePlaceListener implements Listener{
	
	Game game;
	Player player;
	
	OrePlaceListener(Game game, Player player){
		this.game = game;
		this.player = player;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.getPlayer().equals(player)) {
			if(event.getBlockPlaced().getWorld().equals(game.getWorld())) {
				
				switch(event.getBlock().getType()) {
				case IRON_ORE :
					game.getOres().add(new Ore(new LazyLocation(event.getBlockPlaced().getLocation()), Material.IRON_ORE, game));
					game.getConfig().set("ores", game.getOres());
					game.saveConfig();
					player.sendMessage("Saved Iron Ore");
					break;
				case GOLD_ORE :
					game.getOres().add(new Ore(new LazyLocation(event.getBlockPlaced().getLocation()), Material.GOLD_ORE, game));
					game.getConfig().set("ores", game.getOres());
					game.saveConfig();
					player.sendMessage("Saved Gold Ore");
					break;
				case DIAMOND_ORE :
					game.getOres().add(new Ore(new LazyLocation(event.getBlockPlaced().getLocation()), Material.DIAMOND_ORE, game));
					game.getConfig().set("ores", game.getOres());
					game.saveConfig();
					player.sendMessage("Saved Diamond Ore");
					break;
				default :
					player.sendMessage(ChatColor.GRAY + "Block placed was not a valid ore, ignoring it.");
					break;
				}
				
			}
		}
	}
	
}
