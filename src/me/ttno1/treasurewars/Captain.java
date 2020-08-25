package me.ttno1.treasurewars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Captain implements ConfigurationSerializable{

	private String name;
	private LazyLocation location;
	private Villager villager;
	private static Profession captainProfession = Profession.valueOf(Main.getPlugin().getConfig().getString("captainProfession").toUpperCase());
	
	Captain(LazyLocation location, String name){
		
		this.location = location;
		this.name = name;
		
	}
	
	public static Captain deserialize(Map<String, Object> map){
		
		return new Captain((LazyLocation) map.get("location"), (String) map.get("name"));
		
	}
	
	protected void spawn() {
		
		villager = (Villager) location.getWorld().spawnEntity(location.toLocation(), EntityType.VILLAGER);
		villager.setProfession(captainProfession);
		villager.setInvulnerable(true);
		villager.addPotionEffect(Utils.slowness);
		villager.setCollidable(false);
		villager.setCanPickupItems(false);
		villager.setCustomNameVisible(true);
		villager.setCustomName(ChatColor.AQUA + "Captain");
		
	}
	
	protected void kill() {
		
		villager.remove();
		
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public HashMap<String, Object> serialize() {
		
		HashMap<String, Object> serializeMap = new HashMap<String, Object>();
		serializeMap.put("name", name);
		serializeMap.put("location", location);
		return serializeMap;
		
	}
	
	//GUI
	
	protected final static MemorySection prices = (MemorySection) Main.getPlugin().getConfig().get("captain");
	
	public static void openGui(Player player, GameTeam team) {
		
		Inventory inv = Bukkit.createInventory(null, 27, "Captain");
			
		inv.setItem(11, Utils.customItem(Material.GOLD_NUGGET, 1, ChatColor.YELLOW + "Looting", upgradeLore(Utils.lore(ChatColor.GRAY + "Allows your team to steal more", ChatColor.GRAY + "loot from other teams.", ""), team, Upgrade.LOOTING), null, 0, null, true, false));
		inv.setItem(12, Utils.customItem(Material.DIAMOND_PICKAXE, 1, ChatColor.YELLOW + "Efficiency", upgradeLore(Utils.lore(ChatColor.GRAY + "Your team permanently gains", ChatColor.GRAY + "efficiency on all tools.", ""), team, Upgrade.EFFICIENCY), null, 0, null, true, false));
		inv.setItem(13, Utils.customItem(Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Protection", upgradeLore(Utils.lore(ChatColor.GRAY + "Your team permanently gains", ChatColor.GRAY + "protection on all armor.", ""), team, Upgrade.PROTECTION), null, 0, null, true, false));
		inv.setItem(14, Utils.customItem(Material.GOLDEN_PICKAXE, 1, ChatColor.YELLOW + "Fortune", upgradeLore(Utils.lore(ChatColor.GRAY + "Your team gets double", ChatColor.GRAY + "resources from ores.", ""), team, Upgrade.FORTUNE), null, 0, null, true, false));
		inv.setItem(15, Utils.customItem(Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Sharpness", upgradeLore(Utils.lore(ChatColor.GRAY + "Your team permanently gains", ChatColor.GRAY + "sharpness on all weapons.", ""), team, Upgrade.SHARPNESS), null, 0, null, true, false));
		
		player.openInventory(inv);
		
	}
	
	public static void buyUpgrade(Player player, Upgrade upgrade, int price, Material unit) {
		
		if(player.getInventory().containsAtLeast(new ItemStack(unit), price)) {
			if(Main.getGameOf(player).getTeamOf(player).upgrade(upgrade)) {
				player.getInventory().removeItem(new ItemStack(unit, price));
			}
		}else {
			player.sendMessage(ChatColor.RED + "You cannot afford to buy that.");
		}
		
	}
	
	protected static ArrayList<String> upgradeLore(ArrayList<String> prefix, GameTeam team, Upgrade upgrade) {
		
		ArrayList<String> lore;
		
		if(prefix == null) {
			lore = new ArrayList<String>();
		}else {
			lore = prefix;
		}
		
		int upgradeLevel = team.getUpgradeLevel(upgrade);
		
		switch(upgrade) {
		case FORTUNE:
			if(upgradeLevel == 1) {
				lore.add(ChatColor.GREEN + "Unlocked");
			}else {
				lore.add(ChatColor.GRAY + "Cost: " + ChatColor.AQUA + Integer.toString(prices.getInt("fortune1")) + " Diamonds");
			}
			break;
		case EFFICIENCY:
			for(int i = 1; i <= 3; i++) {
				if(upgradeLevel >= i) {
					lore.add(ChatColor.GRAY + "Efficiency " + Integer.toString(i) + ChatColor.GREEN + " Unlocked");
				}else {
					lore.add(ChatColor.GRAY + "Efficiency " + Integer.toString(i) + " Cost: " + ChatColor.AQUA + Integer.toString(prices.getInt("efficiency" + Integer.toString(i))) + " Diamonds");
				}
			}
			break;
		case LOOTING:
			for(int i = 1; i <= 2; i++) {
				if(upgradeLevel >= i) {
					lore.add(ChatColor.GRAY + Integer.toString(i * 25) + "% More Loot " + ChatColor.GREEN + " Unlocked");
				}else {
					lore.add(ChatColor.GRAY + Integer.toString(i * 25) + "% More Loot " + " Cost: " + ChatColor.AQUA + Integer.toString(prices.getInt("looting" + Integer.toString(i))) + " Diamonds");
				}
			}
			break;
		case PROTECTION:
			for(int i = 1; i <= 4; i++) {
				if(upgradeLevel >= i) {
					lore.add(ChatColor.GRAY + "Protection " + Integer.toString(i) + ChatColor.GREEN + " Unlocked");
				}else {
					lore.add(ChatColor.GRAY + "Protection " + Integer.toString(i) + " Cost: " + ChatColor.AQUA + Integer.toString(prices.getInt("protection" + Integer.toString(i))) + " Diamonds");
				}
			}
			break;
		case SHARPNESS:
			for(int i = 1; i <= 3; i++) {
				if(upgradeLevel >= i) {
					lore.add(ChatColor.GRAY + "Sharpness " + Integer.toString(i) + ChatColor.GREEN + " Unlocked");
				}else {
					lore.add(ChatColor.GRAY + "Sharpness " + Integer.toString(i) + " Cost: " + ChatColor.AQUA + Integer.toString(prices.getInt("sharpness" + Integer.toString(i))) + " Diamonds");
				}
			}
			break;
		default:
			break;
		
		}
		
		return lore;
		
	}

	public static enum Upgrade{
		LOOTING(2, null),
		EFFICIENCY(3, Enchantment.DIG_SPEED),
		FORTUNE(1, null),
		PROTECTION(4, Enchantment.PROTECTION_ENVIRONMENTAL),
		SHARPNESS(3, Enchantment.DAMAGE_ALL);
		
		int maxLevel;
		Enchantment enchant;
		
		Upgrade(int maxLevel, Enchantment enchant)	{
			this.maxLevel = maxLevel;
			this.enchant = enchant;
		}
		
		public int getMaxLevel() {
			return maxLevel;
		}
		
		public Enchantment getEnchant() {
			return enchant;
		}
		
	}
	
}
