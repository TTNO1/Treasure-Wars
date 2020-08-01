package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteractListener implements Listener{

	private Game game;
	private final static int puppetRange = Main.getPlugin().getConfig().getInt("voodooPuppetRange");
	private int counter;
	private Block block;
	
	public PlayerInteractListener(Game game) {
		this.game = game;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getPlayer().getWorld().equals(game.getWorld())) {
			if(event.getHand().equals(EquipmentSlot.HAND)) {
				if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					if(event.getItem() == null) {
						Main.getPlugin().getLogger().info("Someone clicked with an empty hand.");
						return;
					}
					
					Player player = event.getPlayer();
			
					switch(event.getMaterial()) {
					case PARROT_SPAWN_EGG :
						if(player.getShoulderEntityLeft() == null) {
							player.setShoulderEntityLeft(game.getWorld().spawnEntity(player.getLocation(), EntityType.PARROT));
							event.getItem().setAmount(event.getItem().getAmount() - 1);
						}
						event.setCancelled(true);
						break;
					case ENDER_EYE :
						if(game.getEyeOfSalineOf(player) == null) {
							game.getEyesOfSaline().add(new EyeOfSaline(game, player));
							event.getItem().setAmount(event.getItem().getAmount() - 1);
						}
						event.setCancelled(true);
						break;
					case TOTEM_OF_UNDYING :
						Player target = Utils.getNearestPlayer(player, puppetRange);
						if(target != null) {
							if(game.getEyeOfSalineOf(target) == null) {
								target.damage(target.getHealth() * .5, player);
							}else {
								game.getEyeOfSalineOf(target).clear();
								target.sendMessage(ChatColor.GREEN + "The Eye of Saline saved you from enemy voodoo!");
							}
							event.getItem().setAmount(event.getItem().getAmount() - 1);
						}else {
							player.sendMessage(ChatColor.RED + "There are no nearby enemy players.");
						}
						event.setCancelled(true);
						break;
					case FIRE_CHARGE :
						try {
							Fireball fireball = (Fireball) player.launchProjectile(Fireball.class);
							fireball.setYield(2);
							Object handle = Utils.getCraftbukkitClass("entity.CraftFireball").getDeclaredMethod("getHandle").invoke(fireball);
							Utils.getNMSClass("EntityFireball").getDeclaredField("dirX").set(handle, event.getPlayer().getEyeLocation().getDirection().getX() * .2);
							Utils.getNMSClass("EntityFireball").getDeclaredField("dirY").set(handle, event.getPlayer().getEyeLocation().getDirection().getY() * .2);
							Utils.getNMSClass("EntityFireball").getDeclaredField("dirZ").set(handle, event.getPlayer().getEyeLocation().getDirection().getZ() * .2);
							event.getItem().setAmount(event.getItem().getAmount() - 1);
						} catch (Exception e) {
							Main.getPlugin().getLogger().info("Something went wrong while trying to fire a connonball in game: " + game.getName() + " by player: " + player.getName());
							e.printStackTrace();
						}
						event.setCancelled(true);
						break;
					case OAK_SLAB :
						BlockFace direction = Utils.yawToDirection(player.getLocation().getYaw());
						block = player.getLocation().clone().add(0, -1, 0).getBlock().getRelative(direction);
						counter = 0;
						BukkitRunnable task = new BukkitRunnable() {
							@Override
							public void run() {
								if(block.getType().equals(Material.AIR)) {
									block.setType(Material.OAK_PLANKS);
								}
								counter++;
								block = block.getRelative(direction);
								if(counter == 35) {
									this.cancel();
								}
							}
						};
						task.runTaskTimer(Main.getPlugin(), 0, 1);
						event.setCancelled(true);
						break;
					case ZOMBIE_PIGMAN_SPAWN_EGG :
						if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
							game.getZombiePigmans().add(new ZombiePigman(game.getTeamOf(player), game, event.getClickedBlock().getLocation().clone().add(0, 1, 0)));
							event.setCancelled(true);
						}
						break;
					default :
						break;
					}
				}
			}
		}
	}
	
}
