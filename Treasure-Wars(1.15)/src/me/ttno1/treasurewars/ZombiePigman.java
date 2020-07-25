package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ZombiePigman {

	private GameTeam team;
	private Game game;
	private PigZombie pigman;
	private int counter;
	private int time;
	private BukkitRunnable task;
	
	ZombiePigman(GameTeam team, Game game, Location spawnLoc){
		this.team = team;
		this.game = game;
		counter = 0;
		time = 120;
		
		pigman = (PigZombie) game.getWorld().spawnEntity(spawnLoc, EntityType.PIG_ZOMBIE);
		pigman.setCustomName(team.getChatColor() + team.getName() + ChatColor.WHITE + " Golem - " + Integer.toString(time) + "s");
		pigman.setCustomNameVisible(true);
		pigman.setRemoveWhenFarAway(false);
		pigman.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS, 1));
		
		task = new BukkitRunnable() {
			@Override
			public void run() {
				if(counter == 1) {
					time--;
					counter = 0;
					pigman.setCustomName(team.getChatColor() + team.getName() + ChatColor.WHITE + " Golem - " + Integer.toString(time) + "s");
					if(time == 0) {
						clear();
					}
				}else {
					counter++;
				}
				if(pigman.getTarget() == null) {
					if(!game.getTeamOf(Utils.getNearestPlayer(pigman, 10)).equals(team)) {
						pigman.setTarget((LivingEntity) Utils.getNearestPlayer(pigman, 10));
					}
				}
			}
		};
		
		task.runTaskTimer(Main.getPlugin(), 10, 10);
		
	}
	
	public GameTeam getTeam() {
		return team;
	}
	
	public boolean isEntity(Entity entity) {
		
		if(pigman.equals(entity)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	void clear() {
		task.cancel();
		pigman.remove();
		game.getZombiePigmans().remove(this);
	}
	
}
