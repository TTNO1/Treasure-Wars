package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Drowner {

	private Game game;
	private Player player;
	private BukkitRunnable task;
	private static int drownTime = Main.getPlugin().getConfig().getInt("drownTime");
	
	Drowner(Game game, Player player) {
		
		this.game = game;
		this.player = player;
		
		task = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(20 / drownTime >= player.getHealth() && player.getShoulderEntityLeft() != null) {
					player.teleport(game.getTeamOf(player).getSpawn().toLocation());
					player.setHealth(2);
					player.setShoulderEntityLeft(null);
					player.sendMessage(ChatColor.GREEN + "Your parrot saved you from drowning!");
					clear();
					return;
				}
				
				player.damage(20 / drownTime);
				
			}
			
		};
		
		task.runTaskTimer(Main.getPlugin(), 20, 20);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void clear() {
		
		task.cancel();
		game.getDrowners().remove(this);
		
	}
	
}
