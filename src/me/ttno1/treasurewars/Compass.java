package me.ttno1.treasurewars;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Compass {

	private Player player;
	private Game game;
	private BukkitRunnable task;
	
	public Compass(Game game, Player player) {
		
		this.game = game;
		this.player = player;
		
		task = new BukkitRunnable() {
			@Override
			public void run() {
				player.setCompassTarget(Utils.getNearestPlayer(player.getLocation()).getLocation());
				if(!player.getInventory().contains(Material.COMPASS)) {
					clear();
				}
			}
		};
		
		task.runTaskTimer(Main.getPlugin(), 1, 10);
		
	}
	
	public void cancel() {
		
		task.cancel();
		if(player.getBedSpawnLocation() != null) {
			player.setCompassTarget(player.getBedSpawnLocation());
		}else {
			player.setCompassTarget(game.getTeamOf(player).getSpawn().toLocation());
		}
		
	}
	
	public void clear() {
		
		cancel();
		game.getCompasses().remove(this);
		
	}
	
}
