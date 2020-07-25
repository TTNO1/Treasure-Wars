package me.ttno1.treasurewars;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

	Game game;
	
	public EntityDamageByEntityListener(Game game) {
		this.game = game;
	}
	
	@EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity().getWorld().equals(game.getWorld())) {
			
			if(event.getEntity() instanceof PigZombie) {
				
				ZombiePigman pigman = null;
				
				for(ZombiePigman zombiePigman : game.getZombiePigmans()) {
					if(zombiePigman.isEntity(event.getEntity())) {
						pigman = zombiePigman;
					}
				}
				
				if(pigman == null) {
					return;
				}
				
				if(event.getDamager() instanceof Player) {
					if(game.getTeamOf((Player) event.getDamager()).equals(pigman.getTeam())) {
						event.setCancelled(true);
					}
				}
				
				if(event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						if(game.getTeamOf((Player) arrow.getShooter()).equals(pigman.getTeam())) {
							event.setCancelled(true);
						}
					}
				}
				
			}
			
		}
	}
}
