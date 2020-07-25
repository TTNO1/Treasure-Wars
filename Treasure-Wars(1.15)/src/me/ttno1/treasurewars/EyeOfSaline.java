package me.ttno1.treasurewars;

import org.bukkit.Location;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EyeOfSaline {

	private Game game;
	private Player player;
	private EnderSignal enderEye;
	private BukkitRunnable task;
	private int timer;
	
	EyeOfSaline(Game game, Player player) {
		
		this.game = game;
		this.player = player;
		
		timer = 0;
		enderEye = (EnderSignal) game.getWorld().spawnEntity(player.getLocation().clone().add(0, 2, 0), EntityType.ENDER_SIGNAL);
		
		task = new BukkitRunnable() {
			@Override
			public void run() {
				
				enderEye.setDespawnTimer(0);
				timer = timer + 1;
				
				if(timer == 15) {
					clear();
				}
				
			}	
		};
		
		task.runTaskTimer(Main.getPlugin(), 60, 60);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setTarget(Location location) {
		
		enderEye.setTargetLocation(location);
		
	}
	
	public void clear() {
		
		task.cancel();
		enderEye.setDespawnTimer(81);
		game.getEyesOfSaline().remove(this);
		
	}
	
}
