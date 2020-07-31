package me.ttno1.treasurewars;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ItemConsumeListener implements Listener {

	private Game game;
	
	public ItemConsumeListener(Game game) {
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
    public void onItemConsume(PlayerItemConsumeEvent event) {
		
		if(event.getPlayer().getWorld().equals(game.getWorld())) {
			
			if(event.getItem().getType().equals(Material.POTION) && event.getItem().getItemMeta().getDisplayName().equals("Invisibility Potion (30 Seconds)")) {
				try {
					Constructor<?> Constructor = Utils.getNMSClass("PacketPlayOutEntityEquipment").getConstructor(int.class, Utils.getNMSClass("EnumItemSlot"), Utils.getNMSClass("ItemStack"));
					Object helmetPacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[5], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, new ItemStack(Material.AIR, 0)));
					Object chestplatePacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[4], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, new ItemStack(Material.AIR, 0)));
                    Object pantsPacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[3], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, new ItemStack(Material.AIR, 0)));
                    Object bootsPacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[2], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, new ItemStack(Material.AIR, 0)));
                    
                    for(Player player : game.getPlayers()) {
                    	Utils.sendPacket(player, helmetPacket);
                    	Utils.sendPacket(player, chestplatePacket);
                    	Utils.sendPacket(player, pantsPacket);
                    	Utils.sendPacket(player, bootsPacket);
					}
				}
				catch (Exception e1) {
					Main.getPlugin().getLogger().info("Something went wrong while sending armor packets for invisibility potions in game: " + game.getName() + " for player: " + event.getPlayer().getName());
                    e1.printStackTrace();
				}
				
				@SuppressWarnings("unused")
				int task = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					public void run() {
						try {
							Constructor<?> Constructor = Utils.getNMSClass("PacketPlayOutEntityEquipment").getConstructor(int.class, Utils.getNMSClass("EnumItemSlot"), Utils.getNMSClass("ItemStack"));
							Object helmetPacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[5], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, event.getPlayer().getEquipment().getHelmet()));
							Object chestplatePacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[4], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, event.getPlayer().getEquipment().getChestplate()));
		                    Object pantsPacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[3], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, event.getPlayer().getEquipment().getLeggings()));
		                    Object bootsPacket = Constructor.newInstance(event.getPlayer().getEntityId(), Utils.getNMSClass("EnumItemSlot").getEnumConstants()[2], Utils.getCraftbukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class).invoke(null, event.getPlayer().getEquipment().getBoots()));
		                    
		                    for(Player player : game.getPlayers()) {
		                    	Utils.sendPacket(player, helmetPacket);
		                    	Utils.sendPacket(player, chestplatePacket);
		                    	Utils.sendPacket(player, pantsPacket);
		                    	Utils.sendPacket(player, bootsPacket);
							}
						}
						catch (Exception e1) {
							Main.getPlugin().getLogger().info("Something went wrong while sending armor packets for invisibility potions in game: " + game.getName() + " for player: " + event.getPlayer().getName());
		                    e1.printStackTrace();
						}
					}
				}, 600L);
				
			}
			
		}
	}
}
