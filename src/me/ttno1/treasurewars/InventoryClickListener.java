package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InventoryClickListener implements Listener{

	private Game game;
	
	public InventoryClickListener(Game game) {
		this.game = game;
	}
	
	@EventHandler (ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked().getWorld().equals(game.getWorld())) {
			
			if(event.getSlotType().equals(SlotType.ARMOR)) {
				event.setCancelled(true);
				return;
			}
			
			Player player = (Player) event.getWhoClicked();
			
			Enchantment swordEnchant;
			int swordEnchantLevel;
			Enchantment toolEnchant;
			int toolEnchantLevel;
			
			switch(event.getView().getTitle()) {
			
			case "Quick Buy" :
				
				swordEnchant = game.getTeamOf(player).getUpgradeEnchant(Captain.Upgrade.SHARPNESS);
				swordEnchantLevel = game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.SHARPNESS);
				toolEnchant = game.getTeamOf(player).getUpgradeEnchant(Captain.Upgrade.EFFICIENCY);
				toolEnchantLevel = game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.EFFICIENCY);
				
				switch(event.getSlot()) {
				case 9 :
					Merchant.openGui(player, Merchant.MerchantPage.BLOCKS);
					break;
				case 18 :
					Merchant.openGui(player, Merchant.MerchantPage.WEAPONS);
					break;
				case 27 :
					Merchant.openGui(player, Merchant.MerchantPage.ARMOR);
					break;
				case 36 :
					Merchant.openGui(player, Merchant.MerchantPage.TOOLS);
					break;
				case 45 :
					Merchant.openGui(player, Merchant.MerchantPage.MAGIC);
					break;
				case 12 :
					Merchant.buyItem(player, new ItemStack(Material.valueOf(game.getTeamOf(player).getColor() + "_WOOL"), 16), Merchant.blocksPrices.getInt("wool"), Material.IRON_INGOT);
					break;
				case 21 :
					Merchant.buyItem(player, new ItemStack(Material.OAK_PLANKS, 16), Merchant.blocksPrices.getInt("wood"), Material.IRON_INGOT);
					break;
				case 30 :
					Merchant.buyItem(player, new ItemStack(Material.valueOf(game.getTeamOf(player).getColor() + "_TERRACOTTA"), 16), Merchant.blocksPrices.getInt("terracotta"), Material.IRON_INGOT);
					break;
				case 13 :
					Merchant.buyItem(player, Utils.customItem(Material.STONE_SWORD, 1, null, null, swordEnchant, swordEnchantLevel, null, false, false), Merchant.weaponsPrices.getInt("stoneSword"), Material.IRON_INGOT);
					break;
				case 22 :
					Merchant.buyItem(player, Utils.customItem(Material.IRON_SWORD, 1, null, null, swordEnchant, swordEnchantLevel, null, false, false), Merchant.weaponsPrices.getInt("ironSword"), Material.IRON_INGOT);
					break;
				case 31 :
					Merchant.buyItem(player, new ItemStack(Material.BOW, 1), Merchant.weaponsPrices.getInt("bow"), Material.IRON_INGOT);
					break;
				case 14 :
					Merchant.buyArmor(player, Merchant.ArmorTier.LEATHER, Merchant.armorPrices.getInt("leather"), Material.IRON_INGOT);
					break;
				case 23 :
					Merchant.buyArmor(player, Merchant.ArmorTier.IRON, Merchant.armorPrices.getInt("iron"), Material.IRON_INGOT);
					break;
				case 32 :
					Merchant.buyArmor(player, Merchant.ArmorTier.TURTLE, Merchant.armorPrices.getInt("turtleShell"), Material.IRON_INGOT);
					break;
				case 15 :
					Merchant.buyItem(player, Utils.customItem(Material.WOODEN_PICKAXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("woodenPickaxe"), Material.IRON_INGOT);
					break;
				case 24 :
					Merchant.buyItem(player, Utils.customItem(Material.WOODEN_AXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("woodenAxe"), Material.IRON_INGOT);
					break;
				case 33 :
					Merchant.buyItem(player, new ItemStack(Material.SHEARS, 1), Merchant.toolsPrices.getInt("shears"), Material.IRON_INGOT);
					break;
				case 16 :
					Merchant.buyItem(player, Utils.customItem(Material.TOTEM_OF_UNDYING, 1, "Voodoo Puppet", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), null, 0, null, true, false), Merchant.magicPrices.getInt("voodooPuppet"), Material.IRON_INGOT);
					break;
				case 25 :
					Merchant.buyItem(player, new ItemStack(Material.GOLDEN_APPLE, 1), Merchant.magicPrices.getInt("goldenApple"), Material.IRON_INGOT);
					break;
				case 34 :
					Merchant.buyItem(player, Utils.customItem(Material.PARROT_SPAWN_EGG, 1, "Parrot", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), null, 0, null, true, false), Merchant.magicPrices.getInt("parrot"), Material.IRON_INGOT);
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			case "Blocks" :
				
				switch(event.getSlot()) {
				case 0 :
					Merchant.openGui(player, Merchant.MerchantPage.QUICK_BUY);
					break;
				case 18 :
					Merchant.openGui(player, Merchant.MerchantPage.WEAPONS);
					break;
				case 27 :
					Merchant.openGui(player, Merchant.MerchantPage.ARMOR);
					break;
				case 36 :
					Merchant.openGui(player, Merchant.MerchantPage.TOOLS);
					break;
				case 45 :
					Merchant.openGui(player, Merchant.MerchantPage.MAGIC);
					break;
				case 12 :
					Merchant.buyItem(player, new ItemStack(Material.valueOf(game.getTeamOf(player).getColor() + "_WOOL"), 16), Merchant.blocksPrices.getInt("wool"), Material.IRON_INGOT);
					break;
				case 13 :
					Merchant.buyItem(player, new ItemStack(Material.OAK_PLANKS, 16), Merchant.blocksPrices.getInt("wood"), Material.IRON_INGOT);
					break;
				case 14 :
					Merchant.buyItem(player, new ItemStack(Material.valueOf(game.getTeamOf(player).getColor() + "_TERRACOTTA"), 16), Merchant.blocksPrices.getInt("terracotta"), Material.IRON_INGOT);
					break;
				case 15 :
					Merchant.buyItem(player, new ItemStack(Material.STONE_BRICKS, 8), Merchant.blocksPrices.getInt("stoneBricks"), Material.IRON_INGOT);
					break;
				case 16 :
					Merchant.buyItem(player, new ItemStack(Material.GLASS, 10), Merchant.blocksPrices.getInt("glass"), Material.IRON_INGOT);
					break;
				case 21 :
					Merchant.buyItem(player, new ItemStack(Material.LADDER, 12), Merchant.blocksPrices.getInt("ladder"), Material.IRON_INGOT);
					break;
				case 22 :
					Merchant.buyItem(player, new ItemStack(Material.OBSIDIAN, 6), Merchant.blocksPrices.getInt("obsidian"), Material.IRON_INGOT);
					break;
				case 23 :
					Merchant.buyItem(player, new ItemStack(Material.WATER_BUCKET, 1), Merchant.blocksPrices.getInt("water"), Material.IRON_INGOT);
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			case "Weapons" :
				
				swordEnchant = game.getTeamOf(player).getUpgradeEnchant(Captain.Upgrade.SHARPNESS);
				swordEnchantLevel = game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.SHARPNESS);
				
				switch(event.getSlot()) {
				case 9 :
					Merchant.openGui(player, Merchant.MerchantPage.BLOCKS);
					break;
				case 0 :
					Merchant.openGui(player, Merchant.MerchantPage.QUICK_BUY);
					break;
				case 27 :
					Merchant.openGui(player, Merchant.MerchantPage.ARMOR);
					break;
				case 36 :
					Merchant.openGui(player, Merchant.MerchantPage.TOOLS);
					break;
				case 45 :
					Merchant.openGui(player, Merchant.MerchantPage.MAGIC);
					break;
				case 12 :
					Merchant.buyItem(player, Utils.customItem(Material.STONE_SWORD, 1, null, null, swordEnchant, swordEnchantLevel, null, false, false), Merchant.weaponsPrices.getInt("stoneSword"), Material.IRON_INGOT);
					break;
				case 13 :
					Merchant.buyItem(player, Utils.customItem(Material.IRON_SWORD, 1, null, null, swordEnchant, swordEnchantLevel, null, false, false), Merchant.weaponsPrices.getInt("ironSword"), Material.IRON_INGOT);
					break;
				case 14 :
					Merchant.buyItem(player, Utils.customItem(Material.DIAMOND_SWORD, 1, null, null, swordEnchant, swordEnchantLevel, null, false, false), Merchant.weaponsPrices.getInt("diamondSword"), Material.IRON_INGOT);
					break;
				case 21 :
					Merchant.buyItem(player, new ItemStack(Material.BOW, 1), Merchant.weaponsPrices.getInt("bow"), Material.IRON_INGOT);
					break;
				case 22 :
					Merchant.buyItem(player, new ItemStack(Material.ARROW, 12), Merchant.weaponsPrices.getInt("arrows"), Material.IRON_INGOT);
					break;
				case 23 :
					Merchant.buyItem(player, Utils.customItem(Material.TIPPED_ARROW, 4, null, null, null, 0, new PotionEffect(PotionEffectType.POISON, 160, 0, false, false, true), false, false), Merchant.weaponsPrices.getInt("poisonArrow"), Material.IRON_INGOT);
					break;
				case 24 :
					Merchant.buyItem(player, Utils.customItem(Material.TIPPED_ARROW, 4, null, null, null, 0, new PotionEffect(PotionEffectType.WEAKNESS, 160, 0, false, false, true), false, false), Merchant.weaponsPrices.getInt("weaknessArrow"), Material.IRON_INGOT);
					break;
				case 30 :
					Merchant.buyItem(player, Utils.customItem(Material.FIRE_CHARGE, 1, "Cannonball", null, null, 0, null, false, false), Merchant.weaponsPrices.getInt("cannonball"), Material.IRON_INGOT);
					break;
				case 31 :
					Merchant.buyItem(player, new ItemStack(Material.TNT, 1), Merchant.weaponsPrices.getInt("tnt"), Material.IRON_INGOT);
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			case "Armor" :
				
				switch(event.getSlot()) {
				case 9 :
					Merchant.openGui(player, Merchant.MerchantPage.BLOCKS);
					break;
				case 18 :
					Merchant.openGui(player, Merchant.MerchantPage.WEAPONS);
					break;
				case 0 :
					Merchant.openGui(player, Merchant.MerchantPage.QUICK_BUY);
					break;
				case 36 :
					Merchant.openGui(player, Merchant.MerchantPage.TOOLS);
					break;
				case 45 :
					Merchant.openGui(player, Merchant.MerchantPage.MAGIC);
					break;
				case 12 :
					Merchant.buyArmor(player, Merchant.ArmorTier.LEATHER, Merchant.armorPrices.getInt("leather"), Material.IRON_INGOT);
					break;
				case 13 :
					Merchant.buyArmor(player, Merchant.ArmorTier.IRON, Merchant.armorPrices.getInt("iron"), Material.IRON_INGOT);
					break;
				case 14 :
					Merchant.buyArmor(player, Merchant.ArmorTier.DIAMOND, Merchant.armorPrices.getInt("diamond"), Material.IRON_INGOT);
					break;
				case 15 :
					Merchant.buyArmor(player, Merchant.ArmorTier.TURTLE, Merchant.armorPrices.getInt("turtleShell"), Material.IRON_INGOT);
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			case "Tools" :
				
				toolEnchant = game.getTeamOf(player).getUpgradeEnchant(Captain.Upgrade.EFFICIENCY);
				toolEnchantLevel = game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.EFFICIENCY);
				
				switch(event.getSlot()) {
				case 9 :
					Merchant.openGui(player, Merchant.MerchantPage.BLOCKS);
					break;
				case 18 :
					Merchant.openGui(player, Merchant.MerchantPage.WEAPONS);
					break;
				case 27 :
					Merchant.openGui(player, Merchant.MerchantPage.ARMOR);
					break;
				case 0 :
					Merchant.openGui(player, Merchant.MerchantPage.QUICK_BUY);
					break;
				case 45 :
					Merchant.openGui(player, Merchant.MerchantPage.MAGIC);
					break;
				case 12 :
					Merchant.buyItem(player, Utils.customItem(Material.WOODEN_PICKAXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("woodenPickaxe"), Material.IRON_INGOT);
					break;
				case 13 :
					Merchant.buyItem(player, Utils.customItem(Material.STONE_PICKAXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("stonePickaxe"), Material.IRON_INGOT);
					break;
				case 14 :
					Merchant.buyItem(player, Utils.customItem(Material.IRON_PICKAXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("ironPickaxe"), Material.IRON_INGOT);
					break;
				case 15 :
					Merchant.buyItem(player, Utils.customItem(Material.DIAMOND_PICKAXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("diamondPickaxe"), Material.IRON_INGOT);
					break;
				case 21 :
					Merchant.buyItem(player, Utils.customItem(Material.WOODEN_AXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("woodenAxe"), Material.IRON_INGOT);
					break;
				case 22 :
					Merchant.buyItem(player, Utils.customItem(Material.STONE_AXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("stoneAxe"), Material.IRON_INGOT);
					break;
				case 23 :
					Merchant.buyItem(player, Utils.customItem(Material.IRON_AXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("ironAxe"), Material.IRON_INGOT);
					break;
				case 24 :
					Merchant.buyItem(player, Utils.customItem(Material.DIAMOND_AXE, 1, null, null, toolEnchant, toolEnchantLevel, null, false, false), Merchant.toolsPrices.getInt("diamondAxe"), Material.IRON_INGOT);
					break;
				case 30 :
					Merchant.buyItem(player, new ItemStack(Material.SHEARS, 1), Merchant.toolsPrices.getInt("shears"), Material.IRON_INGOT);
					break;
				case 31 :
					Merchant.buyItem(player, new ItemStack(Material.COMPASS, 1), Merchant.toolsPrices.getInt("compass"), Material.IRON_INGOT);
					game.getCompasses().add(new Compass(game, player));
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			case "Magic" :
				
				switch(event.getSlot()) {
				case 9 :
					Merchant.openGui(player, Merchant.MerchantPage.BLOCKS);
					break;
				case 18 :
					Merchant.openGui(player, Merchant.MerchantPage.WEAPONS);
					break;
				case 27 :
					Merchant.openGui(player, Merchant.MerchantPage.ARMOR);
					break;
				case 36 :
					Merchant.openGui(player, Merchant.MerchantPage.TOOLS);
					break;
				case 0 :
					Merchant.openGui(player, Merchant.MerchantPage.QUICK_BUY);
					break;
				case 12 :
					Merchant.buyItem(player, Utils.customItem(Material.TOTEM_OF_UNDYING, 1, "Voodoo Puppet", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), null, 0, null, true, false), Merchant.magicPrices.getInt("voodooPuppet"), Material.IRON_INGOT);
					break;
				case 13 :
					Merchant.buyItem(player, Utils.customItem(Material.ENDER_EYE, 1, "Eye of Saline", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), Enchantment.PROTECTION_ENVIRONMENTAL, 1, null, true, true), Merchant.magicPrices.getInt("eyeOfSaline"), Material.IRON_INGOT);
					break;
				case 14 :
					Merchant.buyItem(player, new ItemStack(Material.GOLDEN_APPLE, 1), Merchant.magicPrices.getInt("goldenApple"), Material.IRON_INGOT);
					break;
				case 15 :
					Merchant.buyItem(player, Utils.customItem(Material.PARROT_SPAWN_EGG, 1, "Parrot", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), null, 0, null, true, false), Merchant.magicPrices.getInt("parrot"), Material.IRON_INGOT);
					break;
				case 16 :
					Merchant.buyItem(player, Utils.customItem(Material.OAK_SLAB, 1, "Walk the Plank", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), Enchantment.PROTECTION_ENVIRONMENTAL, 1, null, true, true), Merchant.magicPrices.getInt("walkThePlank"), Material.IRON_INGOT);
					break;
				case 21 :
					Merchant.buyItem(player, new ItemStack(Material.ENDER_PEARL, 1), Merchant.magicPrices.getInt("enderPearl"), Material.IRON_INGOT);
					break;
				case 22 :
					Merchant.buyItem(player, new ItemStack(Material.CHORUS_FRUIT, 1), Merchant.magicPrices.getInt("chorusFruit"), Material.IRON_INGOT);
					break;
				case 23 :
					Merchant.buyItem(player, Utils.customItem(Material.ZOMBIE_PIGMAN_SPAWN_EGG, 1, "Zombie Pigman", Utils.lore(" ", ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "click to use"), null, 0, null, true, false), Merchant.magicPrices.getInt("zombiePigman"), Material.IRON_INGOT);
					break;
				case 30 :
					Merchant.buyItem(player, Utils.customItem(Material.POTION, 1, null, null, null, 0, new PotionEffect(PotionEffectType.SPEED, 900, 1, false, false, true), false, false), Merchant.magicPrices.getInt("speedPotion"), Material.IRON_INGOT);
					break;
				case 31 :
					Merchant.buyItem(player, Utils.customItem(Material.POTION, 1, null, null, null, 0, new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, false, false, true), false, false), Merchant.magicPrices.getInt("invisibilityPotion"), Material.IRON_INGOT);
					break;
				case 32 :
					Merchant.buyItem(player, Utils.customItem(Material.POTION, 1, null, null, null, 0, new PotionEffect(PotionEffectType.WATER_BREATHING, 900, 1, false, false, true), false, false), Merchant.magicPrices.getInt("waterBreathingPotion"), Material.IRON_INGOT);
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			case "Captain" :
				
				switch(event.getSlot()) {
				case 11 :
					Captain.buyUpgrade(player, Captain.Upgrade.LOOTING, Captain.prices.getInt("looting" + Integer.toString(game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.LOOTING) + 1)), Material.DIAMOND);
					break;
				case 12 :
					Captain.buyUpgrade(player, Captain.Upgrade.EFFICIENCY, Captain.prices.getInt("efficiency" + Integer.toString(game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.EFFICIENCY) + 1)), Material.DIAMOND);
					break;
				case 13 :
					Captain.buyUpgrade(player, Captain.Upgrade.PROTECTION, Captain.prices.getInt("protection" + Integer.toString(game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.PROTECTION) + 1)), Material.DIAMOND);
					break;
				case 14 :
					Captain.buyUpgrade(player, Captain.Upgrade.FORTUNE, Captain.prices.getInt("fortune" + Integer.toString(game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.FORTUNE) + 1)), Material.DIAMOND);
					break;
				case 15 :
					Captain.buyUpgrade(player, Captain.Upgrade.SHARPNESS, Captain.prices.getInt("sharpness" + Integer.toString(game.getTeamOf(player).getUpgradeLevel(Captain.Upgrade.SHARPNESS) + 1)), Material.DIAMOND);
					break;
					
				}
				
				event.setCancelled(true);
				
				break;
				
			}
			
		}
	}
	
}
