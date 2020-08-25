package me.ttno1.treasurewars;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.ttno1.treasurewars.Captain.Upgrade;

public class RespawnListener implements Listener {

	private Game game;
	private int timer;
	private final static int respawnTime = Main.getPlugin().getConfig().getInt("respawnTime");
	
	public static ArrayList<Material> keepItems = new ArrayList<Material>();
	static {
		keepItems.add(Material.LEATHER_HELMET);
		keepItems.add(Material.LEATHER_LEGGINGS);
		keepItems.add(Material.LEATHER_CHESTPLATE);
		keepItems.add(Material.LEATHER_BOOTS);
		keepItems.add(Material.CHAINMAIL_BOOTS);
		keepItems.add(Material.CHAINMAIL_LEGGINGS);
		keepItems.add(Material.IRON_BOOTS);
		keepItems.add(Material.IRON_LEGGINGS);
		keepItems.add(Material.DIAMOND_LEGGINGS);
		keepItems.add(Material.DIAMOND_BOOTS);
		keepItems.add(Material.TURTLE_HELMET);
	}
	
	public RespawnListener(Game game) {
		
		this.game = game;
		
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(event.getPlayer().getWorld().equals(game.getWorld())) {
			
			event.setRespawnLocation(game.getTeamOf(event.getPlayer()).getSpawn().toLocation());
			event.getPlayer().setGameMode(GameMode.SPECTATOR);
			
			for(ItemStack itemStack : event.getPlayer().getInventory()) {
				
				boolean keep = false;
				
				for(Material type : keepItems) {
					if(itemStack.getType().equals(type)) {
						keep = true;
						break;
					}
				}
				
				if(!keep) {
					itemStack.setAmount(0);
				}
				
			}
			
			event.getPlayer().getInventory().addItem(Utils.customItem(Material.WOODEN_SWORD, 1, null, null, game.getTeamOf(event.getPlayer()).getUpgradeEnchant(Upgrade.SHARPNESS), game.getTeamOf(event.getPlayer()).getUpgradeLevel(Captain.Upgrade.SHARPNESS), null, false, false));
			
			timer = respawnTime;
			
			BukkitRunnable task = new BukkitRunnable() {
				public void run() {
					
					try {
						event.getPlayer().sendTitle(ChatColor.RED + "You Died", ChatColor.YELLOW + "You Will Respawn In " + Integer.toString(timer) + " Seconds", 0, 20, 0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(timer  == 0) {
						event.getPlayer().teleport(game.getTeamOf(event.getPlayer()).getSpawn().toLocation());
						event.getPlayer().setGameMode(GameMode.SURVIVAL);
						event.getPlayer().resetTitle();
						
						this.cancel();
					}
					
					timer--;
					
				}
			};
			
			task.runTaskTimer(Main.getPlugin(), 0, 20);
			
		}
	}
	
}
