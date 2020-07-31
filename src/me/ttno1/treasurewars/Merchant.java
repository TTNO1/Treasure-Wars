package me.ttno1.treasurewars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Merchant implements ConfigurationSerializable{

	private String name;
	private LazyLocation location;
	private Villager villager;
	
	Merchant(LazyLocation location, String name){
		
		this.location = location;
		this.name = name;
		
	}
	
	public static Merchant deserialize(Map<String, Object> map){
		
		return new Merchant((LazyLocation) map.get("location"), (String) map.get("name"));
		
	}
	
	public String getName() {
		return name;
	}
	
	protected void spawn() {
		
		villager = (Villager) location.getWorld().spawnEntity(location.toLocation(), EntityType.VILLAGER);
		villager.setProfession(Profession.valueOf(Main.getPlugin().getConfig().getString("merchantProfession")));
		villager.setInvulnerable(true);
		villager.addPotionEffect(Utils.slowness);
		villager.setCollidable(false);
		villager.setCanPickupItems(false);
		villager.setCustomNameVisible(true);
		villager.setCustomName(ChatColor.GOLD + "Merchant");
		
	}
	
	protected void kill() {
		
		villager.remove();
		
	}
	
	@Override
	public HashMap<String, Object> serialize() {
		
		HashMap<String, Object> serializeMap = new HashMap<String, Object>();
		serializeMap.put("name", name);
		serializeMap.put("location", location.serialize());
		return serializeMap;
		
	}
	
	//GUI
	
	public static enum MerchantPage{
		QUICK_BUY,
		BLOCKS,
		WEAPONS,
		ARMOR,
		TOOLS,
		MAGIC
	}
	
	@SuppressWarnings("unchecked")
	protected static HashMap<String, Integer> blocksPrices = (HashMap<String, Integer>) Main.getPlugin().getConfig().getMapList("blocks");
	@SuppressWarnings("unchecked")
	protected static HashMap<String, Integer> weaponsPrices = (HashMap<String, Integer>) Main.getPlugin().getConfig().getMapList("weapons");
	@SuppressWarnings("unchecked")
	protected static HashMap<String, Integer> armorPrices = (HashMap<String, Integer>) Main.getPlugin().getConfig().getMapList("armor");
	@SuppressWarnings("unchecked")
	protected static HashMap<String, Integer> toolsPrices = (HashMap<String, Integer>) Main.getPlugin().getConfig().getMapList("tools");
	@SuppressWarnings("unchecked")
	protected static HashMap<String, Integer> magicPrices = (HashMap<String, Integer>) Main.getPlugin().getConfig().getMapList("magic");
	
	public static void openGui(Player player, MerchantPage page) {
		
		Inventory inv;
		int greenGlass = 0;
		
		switch(page) {
		case QUICK_BUY:
			inv = Bukkit.createInventory(null, 54, "Quick Buy");
			
			inv.setItem(12, Utils.customItem(Material.WHITE_WOOL, 16, "Wool", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("wool").toString() + " Iron", "", ChatColor.GRAY + "Cheap block that", ChatColor.GRAY + "dissolves in water"), null, 0, null, true, false));
			inv.setItem(21, Utils.customItem(Material.OAK_PLANKS, 16, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("wood").toString() + " Iron", "", ChatColor.GRAY + "Sturdy, water-proof", ChatColor.GRAY + "block"), null, 0, null, true, false));
			inv.setItem(30, Utils.customItem(Material.TERRACOTTA, 16, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("terracotta").toString() + " Iron", "", ChatColor.GRAY + "Solid colored block"), null, 0, null, true, false));
			
			greenGlass = 1;
			break;
		case BLOCKS:
			inv = Bukkit.createInventory(null, 54, "Blocks");
			
			inv.setItem(12, Utils.customItem(Material.WHITE_WOOL, 16, "Wool", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("wool").toString() + " Iron", "", ChatColor.GRAY + "Cheap block that", ChatColor.GRAY + "dissolves in water"), null, 0, null, true, false));
			inv.setItem(13, Utils.customItem(Material.OAK_PLANKS, 16, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("wood").toString() + " Iron", "", ChatColor.GRAY + "Sturdy, water-proof", ChatColor.GRAY + "block"), null, 0, null, true, false));
			inv.setItem(14, Utils.customItem(Material.TERRACOTTA, 16, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("terracotta").toString() + " Iron", "", ChatColor.GRAY + "Solid colored block"), null, 0, null, true, false));
			inv.setItem(15, Utils.customItem(Material.STONE_BRICKS, 8, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("stoneBricks").toString() + " Iron", "", ChatColor.GRAY + "Heavy, blast-proof", ChatColor.GRAY + "block"), null, 0, null, true, false));
			inv.setItem(16, Utils.customItem(Material.GLASS, 10, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("glass").toString() + " Iron", "", ChatColor.GRAY + "Fragile transparent", ChatColor.GRAY + "block"), null, 0, null, true, false));
			inv.setItem(21, Utils.customItem(Material.LADDER, 12, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("ladder").toString() + " Iron", "", ChatColor.GRAY + "Good for climbing"), null, 0, null, true, false));
			inv.setItem(22, Utils.customItem(Material.OBSIDIAN, 6, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("obsidian").toString() + " Iron", "", ChatColor.GRAY + "Strong block, good", ChatColor.GRAY + "for defense"), null, 0, null, true, false));			
			inv.setItem(23, Utils.customItem(Material.WATER_BUCKET, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + blocksPrices.get("water").toString() + " Iron", "", ChatColor.GRAY + "Don't worry,", ChatColor.GRAY + "you won't drown"), null, 0, null, true, false));
			
			greenGlass = 10;
			break;
		case WEAPONS:
			inv = Bukkit.createInventory(null, 54, "Weapons");
			
			inv.setItem(12, Utils.customItem(Material.WOODEN_SWORD, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("woodenSword").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(13, Utils.customItem(Material.STONE_SWORD, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("stoneSword").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(14, Utils.customItem(Material.IRON_SWORD, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("ironSword").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(15, Utils.customItem(Material.DIAMOND_SWORD, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("diamondSword").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(21, Utils.customItem(Material.BOW, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("bow").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(22, Utils.customItem(Material.ARROW, 12, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("arrows").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(23, Utils.customItem(Material.TIPPED_ARROW, 4, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("poisonArrow").toString() + " Iron"), null, 0, new PotionEffect(PotionEffectType.POISON, 160, 0, false, false, true), true, false));
			inv.setItem(24, Utils.customItem(Material.TIPPED_ARROW, 4, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("weaknessArrow").toString() + " Iron"), null, 0, new PotionEffect(PotionEffectType.WEAKNESS, 160, 0, false, false, true), true, false));
			inv.setItem(30, Utils.customItem(Material.FIRE_CHARGE, 1, "Cannonball", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("cannonball").toString() + " Iron", "", ChatColor.GRAY + "Handheld cannonball"), null, 0, null, true, false));
			inv.setItem(31, Utils.customItem(Material.TNT, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + weaponsPrices.get("tnt").toString() + " Iron", "", ChatColor.GRAY + "Explosive block"), null, 0, null, true, false));
			
			greenGlass = 19;
			break;
		case ARMOR:
			inv = Bukkit.createInventory(null, 54, "Armor");
			
			inv.setItem(12, Utils.customItem(Material.LEATHER_LEGGINGS, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + armorPrices.get("leather").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(13, Utils.customItem(Material.IRON_LEGGINGS, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + armorPrices.get("iron").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(14, Utils.customItem(Material.DIAMOND_LEGGINGS, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + armorPrices.get("diamond").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(15, Utils.customItem(Material.TURTLE_HELMET, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + armorPrices.get("turtleShell").toString() + " Iron", "", ChatColor.GRAY + "Extends your air", ChatColor.GRAY + "capacity underwater"), Enchantment.WATER_WORKER, 1, null, true, false));
			
			greenGlass = 28;
			break;
		case TOOLS:
			inv = Bukkit.createInventory(null, 54, "Tools");
			
			inv.setItem(12, Utils.customItem(Material.WOODEN_PICKAXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("woodenPickaxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(13, Utils.customItem(Material.STONE_PICKAXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("stonePickaxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(14, Utils.customItem(Material.IRON_PICKAXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("ironPickaxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(15, Utils.customItem(Material.DIAMOND_PICKAXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("diamondPickaxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(21, Utils.customItem(Material.WOODEN_AXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("woodenAxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(22, Utils.customItem(Material.STONE_AXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("stoneAxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(23, Utils.customItem(Material.IRON_AXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("ironAxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(24, Utils.customItem(Material.DIAMOND_AXE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("diamondAxe").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(30, Utils.customItem(Material.SHEARS, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("shears").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(31, Utils.customItem(Material.COMPASS, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + toolsPrices.get("compass").toString() + " Iron", "", ChatColor.GRAY + "Locates the nearest", ChatColor.GRAY + "enemy player"), null, 0, null, true, false));
			
			greenGlass = 37;
			break;
		case MAGIC:
			inv = Bukkit.createInventory(null, 54, "Magic");
			
			inv.setItem(12, Utils.customItem(Material.TOTEM_OF_UNDYING, 1, "Voodoo Puppet", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("voodooPuppet").toString() + " Iron", "", ChatColor.GRAY + "Deals half the health", ChatColor.GRAY + "of the closest enemy player"), null, 0, null, true, false));
			inv.setItem(13, Utils.customItem(Material.ENDER_EYE, 1, "Eye of Saline", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("eyeOfSaline").toString() + " Iron", "", ChatColor.GRAY + "Protects you from", ChatColor.GRAY + "enemy voodoo puppets", ChatColor.GRAY + "for 45 seconds"), Enchantment.PROTECTION_ENVIRONMENTAL, 1, null, true, true));
			inv.setItem(14, Utils.customItem(Material.GOLDEN_APPLE, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("goldenApple").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(15, Utils.customItem(Material.PARROT_SPAWN_EGG, 1, "Parrot", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("parrot").toString() + " Iron", "", ChatColor.GRAY + "Pulls you out of water", ChatColor.GRAY + "before you drown", ChatColor.GRAY + "(1 time use)"), null, 0, null, true, false));
			inv.setItem(16, Utils.customItem(Material.OAK_SLAB, 1, "Walk the Plank", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("walkThePlank").toString() + " Iron", "", ChatColor.GRAY + "Creates a path of", ChatColor.GRAY + "planks in front of you"), Enchantment.PROTECTION_ENVIRONMENTAL, 1, null, true, true));
			inv.setItem(21, Utils.customItem(Material.ENDER_PEARL, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("enderPearl").toString() + " Iron"), null, 0, null, true, false));
			inv.setItem(22, Utils.customItem(Material.CHORUS_FRUIT, 1, null, Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("chorusFruit").toString() + " Iron", "", ChatColor.GRAY + "Teleports you in a", ChatColor.GRAY + "random direction"), null, 0, null, true, false));
			inv.setItem(23, Utils.customItem(Material.ZOMBIE_PIGMAN_SPAWN_EGG, 1, "Zombie Pigman", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("zombiePigman").toString() + " Iron", "", ChatColor.GRAY + "Summons a zombie pigman", ChatColor.GRAY + "to battle enemies"), null, 0, null, true, false));
			inv.setItem(30, Utils.customItem(Material.POTION, 1, "Speed II Potion (45 Seconds)", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("speedPotion").toString() + " Iron"), null, 0, new PotionEffect(PotionEffectType.SPEED, 900, 1, false, false, true), true, false));
			inv.setItem(31, Utils.customItem(Material.POTION, 1, "Invisibility Potion (30 Seconds)", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("invisibilityPotion").toString() + " Iron"), null, 0, new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0, false, false, true), true, false));
			inv.setItem(32, Utils.customItem(Material.POTION, 1, "Water Breathing Potion (45 Seconds)", Utils.lore(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + magicPrices.get("waterBreathingPotion").toString() + " Iron"), null, 0, new PotionEffect(PotionEffectType.WATER_BREATHING, 900, 0, false, false, true), true, false));
			
			greenGlass = 46;
			break;
		default:
			inv = Bukkit.createInventory(null, 54, "Merchant");
			
			inv.setItem(23, Utils.customItem(Material.OAK_SIGN, 1, ChatColor.DARK_PURPLE + "Info", Utils.lore(ChatColor.GRAY + "Select a category", ChatColor.GRAY + "on the left to", ChatColor.GRAY + "buy items."), null, 0, null, true, false));
			break;
		}
		
		inv.setItem(0, Utils.customItem(Material.NETHER_STAR, 1, ChatColor.YELLOW + "Quick Buy", Utils.lore(ChatColor.GRAY + "Click to View"), null, 0, null, true, false));
		inv.setItem(9, Utils.customItem(Material.OAK_PLANKS, 1, ChatColor.YELLOW + "Blocks", Utils.lore(ChatColor.GRAY + "Click to View"), null, 0, null, true, false));
		inv.setItem(18, Utils.customItem(Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Weapons", Utils.lore(ChatColor.GRAY + "Click to View"), null, 0, null, true, false));
		inv.setItem(27, Utils.customItem(Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", Utils.lore(ChatColor.GRAY + "Click to View"), null, 0, null, true, false));
		inv.setItem(36, Utils.customItem(Material.STONE_PICKAXE, 1, ChatColor.YELLOW + "Tools", Utils.lore(ChatColor.GRAY + "Click to View"), null, 0, null, true, false));
		inv.setItem(45, Utils.customItem(Material.TOTEM_OF_UNDYING, 1, ChatColor.YELLOW + "Magic", Utils.lore(ChatColor.GRAY + "Click to View"), null, 0, null, true, false));
		
		inv.setItem(1, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		inv.setItem(10, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		inv.setItem(19, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		inv.setItem(28, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		inv.setItem(37, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		inv.setItem(46, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
		
		if(greenGlass != 0) {
			inv.setItem(greenGlass, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
		}
		
		player.openInventory(inv);
		
	}
	
	public static void buyItem(Player player, ItemStack item, int price, Material unit) {
		
		if(player.getInventory().containsAtLeast(new ItemStack(unit), price)) {
			player.getInventory().removeItem(new ItemStack(unit, price));
			player.getInventory().addItem(item);
		}else {
			player.sendMessage(ChatColor.RED + "You cannot afford to buy that.");
		}
		
	}
	
	public static void buyArmor(Player player, ArmorTier armorTier, int price, Material unit) {
		
		if(player.getInventory().containsAtLeast(new ItemStack(unit), price)) {
			
			player.getInventory().removeItem(new ItemStack(unit, price));
			
			Enchantment armorEnchant = Main.getGameOf(player).getTeamOf(player).getUpgradeEnchant(Captain.Upgrade.PROTECTION);
			int armorEnchantLevel = Main.getGameOf(player).getTeamOf(player).getUpgradeLevel(Captain.Upgrade.PROTECTION);
			Material bootsMaterial;
			Material leggingsMaterial;
			
			switch(armorTier) {
			case LEATHER:
				bootsMaterial = Material.LEATHER_BOOTS;
				leggingsMaterial = Material.LEATHER_LEGGINGS;
				break;
			case DIAMOND:
				bootsMaterial = Material.DIAMOND_BOOTS;
				leggingsMaterial = Material.DIAMOND_LEGGINGS;
				break;
			case IRON:
				bootsMaterial = Material.IRON_BOOTS;
				leggingsMaterial = Material.IRON_LEGGINGS;
				break;
			case TURTLE:
				player.getInventory().setHelmet(Utils.customItem(Material.TURTLE_HELMET, 1, null, null, armorEnchant, armorEnchantLevel, null, false, false));
				return;
			default:
				bootsMaterial = null;
				leggingsMaterial = null;
				break;
			}
			
			player.getInventory().setBoots(Utils.customItem(bootsMaterial, 1, null, null, armorEnchant, armorEnchantLevel, null, false, false));
			player.getInventory().setLeggings(Utils.customItem(leggingsMaterial, 1, null, null, armorEnchant, armorEnchantLevel, null, false, false));
			
		}else {
			player.sendMessage(ChatColor.RED + "You cannot afford to buy that.");
		}
		
	}
	
	public static enum ArmorTier {
		LEATHER,
		IRON,
		DIAMOND,
		TURTLE
	}

}
