package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMoveListener implements Listener {

	Game game;
	private final static int seaLevel = Main.getPlugin().getConfig().getInt("seaLevel");
	private final static int cooldownTime = Main.getPlugin().getConfig().getInt("lootCooldownTime");
	private final static int lootAmount = Main.getPlugin().getConfig().getInt("lootAmount");
	
	public PlayerMoveListener(Game game) {
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
		if(event.getTo().getWorld().equals(game.getWorld())) {
			Player player = event.getPlayer();
			
			if(game.getEyeOfSalineOf(player) != null) {
				
				game.getEyeOfSalineOf(player).setTarget(event.getTo());
				
			}
			
			if(event.getTo().getBlock().getType().equals(Material.WATER) && event.getTo().getY() <= seaLevel && !player.hasPotionEffect(PotionEffectType.WATER_BREATHING) && game.getDrownerOf(player) == null) {
				
				game.getDrowners().add(new Drowner(game, player));
				
			}else if(game.getDrownerOf(player) != null) {
				
				game.getDrownerOf(player).clear();
				
			}
			
			for(GameTeam team : game.getTeams()) {
				
				if(team.getTreasureArea().isIn(event.getTo()) && !team.getTreasureArea().isIn(event.getFrom())) {
					if(team.equals(game.getTeamOf(player))) {
						for(ItemStack item : player.getInventory()) {
							switch(item.getType()) {
							case GOLD_INGOT :
								team.addPoints(item.getAmount() * 20);
								game.addPointsEarned(player, item.getAmount() * 20);
								item.setAmount(0);
								break;
							case DIAMOND :
								team.addPoints(item.getAmount() * 100);
								game.addPointsEarned(player, item.getAmount() * 100);
								item.setAmount(0);
								break;
							default :
								break;
							}
						}
					}else {
						if(!game.getLootCooldown().contains(player)) {
							player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, team.takePoints((int) (lootAmount + (lootAmount * (game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.LOOTING) * .25))))));
							game.getLootCooldown().add(player);
							BukkitRunnable task = new BukkitRunnable() {
								@Override
								public void run() {
									game.getLootCooldown().remove(player);
								}
							};
							task.runTaskLater(Main.getPlugin(), cooldownTime * 20);
						}else {
							player.sendMessage(ChatColor.RED + "You must wait before you can loot again.");
						}
					}
					
					break;
				}
				
			}
			
		}
	}
}
