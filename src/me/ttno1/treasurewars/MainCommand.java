package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MainCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player;
		Game game = null;
		
		if(sender instanceof Player) {
			player = (Player) sender;
		}else {
			Main.getPlugin().getLogger().info("You must be a player to execute that command.");
			return true;
		}
		
		if(args.length > 1) {
			if(!args[0].equals("newgame")) {
				if(Main.getGame(args[1]) == null) {
					player.sendMessage(ChatColor.RED + "That game does not exist.");
					return true;
				}else {
					game = Main.getGame(args[1]);
				}
			}
		}
		
		switch(args[0]) {
		case "newgame" :
			if(player.hasPermission("treasurewars.newgame")) {
				if(args.length >= 5) {
					player.sendMessage(Game.newGame(args[1], args[2], args[3], args[4], player.getWorld()));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw newgame [Game Name] [Players Per Team] [Game Duration ex. 6.3 = 6min 30sec] [Maximum Points]");
				}
			}
			break;
		case "newteam" :
			if(player.hasPermission("treasurewars.newteam")) {
				if(args.length >= 4) {
					player.sendMessage(game.newTeam(args[2], args[3]));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw newteam [Game Name] [Team Name] [Team Color (Dye Color)]");
				}
			}
			break;
		case "setqueue" :
			if(player.hasPermission("treasurewars.setqueue")) {
				if(args.length >= 2) {
					player.sendMessage(game.setQueueLocation(new LazyLocation(player.getLocation())));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw setqueue [Game Name]");
				}
			}
			break;
		case "oreplacemode" :
			if(player.hasPermission("treasurewars.oreplacemode")) {
				if(args.length >= 2) {
					player.sendMessage(game.orePlaceMode(player));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw oreplacemode [Game Name]");
				}
			}
			break;
		case "setspawn" :
			if(player.hasPermission("treasurewars.setspawn")) {
				if(args.length >= 3) {
					if(game.getTeam(args[2]) != null) {
						player.sendMessage(game.getTeam(args[2]).setSpawn(new LazyLocation(player.getLocation())));
					}else {
						player.sendMessage(ChatColor.RED + "A team with that name does not exist.");
					}
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw setspawn [Game Name] [Team Name]");
				}
			}
			break;
		case "settreasurearea" :
			if(player.hasPermission("treasurewars.settreasurearea")) {
				if(args.length >= 3) {
					if(game.getTeam(args[2]) != null) {
						player.sendMessage(game.getTeam(args[2]).selectTreasureArea(player));
					}else {
						player.sendMessage(ChatColor.RED + "A team with that name does not exist.");
					}
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw settreasurearea [Game Name] [Team Name]");
				}
			}
			break;
		case "setlobby" :
			if(player.hasPermission("treasurewars.setlobby")) {
				if(args.length >= 2) {
					player.sendMessage(game.setLobbyLocation(new LazyLocation(player.getLocation())));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw setlobby [Game Name]");
				}
			}
			break;
		case "newmerchant" :
			if(player.hasPermission("treasurewars.newmerchant")) {
				if(args.length >= 3) {
					player.sendMessage(game.newMerchant(args[2], new LazyLocation(player.getLocation())));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw newmerchant [Game Name] [Merchant Name]");
				}
			}
			break;
		case "newcaptain" :
			if(player.hasPermission("treasurewars.newcaptain")) {
				if(args.length >= 3) {
					player.sendMessage(game.newCaptain(args[2], new LazyLocation(player.getLocation())));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw newcaptain [Game Name] [Captain Name]");
				}
			}
			break;
		case "setlobbyentity" :
			if(player.hasPermission("treasurewars.setlobbyentity")) {
				if(args.length >= 3) {
					String name = "";
					for(int i = 0; i < args.length; i++) {
						if(i != 0 && i != 1) {
							name = name + args[i] + " ";
						}
					}
					name = name.trim();
					player.sendMessage(game.setLobbyEntity(new LazyLocation(player.getLocation()), name));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw setlobbyentity [Game Name] [Villager Name]");
				}
			}
			break;
		case "removemerchant" :
			if(player.hasPermission("treasurewars.removemerchant")) {
				if(args.length >= 3) {
					player.sendMessage(game.removeMerchant(args[2]));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw removemerchant [Game Name] [Merchant Name]");
				}
			}
			break;
		case "removecaptain" :
			if(player.hasPermission("treasurewars.removecaptain")) {
				if(args.length >= 3) {
					player.sendMessage(game.removeCaptain(args[2]));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw removecaptain [Game Name] [Captain Name]");
				}
			}
			break;
		case "removeteam" :
			if(player.hasPermission("treasurewars.removeteam")) {
				if(args.length >= 3) {
					player.sendMessage(game.removeTeam(args[2]));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw removeteam [Game Name] [Tean Name]");
				}
			}
			break;
		case "deletegame" :
			if(player.hasPermission("treasurewars.deletegame")) {
				if(args.length >= 2) {
					player.sendMessage(game.delete());
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw deletegame [Game Name]");
				}
			}
			break;
		case "updateworld" :
			if(player.hasPermission("treasurewars.updateworld")) {
				if(args.length >= 2) {
					player.sendMessage(game.updateWorld());
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw updateworld [Game Name]");
				}
			}
			break;
		case "joingame" :
			if(player.hasPermission("treasurewars.joingame")) {
				if(args.length >= 2) {
					player.sendMessage(game.joinGame(player));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw joingame [Game Name]");
				}
			}
			break;
		case "startgame" :
			if(player.hasPermission("treasurewars.startgame")) {
				if(args.length >= 2) {
					game.startGame();
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw startgame [Game Name]");
				}
			}
			break;
		case "leavegame" :
			if(player.hasPermission("treasurewars.leavegame")) {
				if(args.length >= 2) {
					player.sendMessage(game.leaveGame(player));
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw leavegame [Game Name]");
				}
			}
			break;
		case "disablegame" :
			if(player.hasPermission("treasurewars.disablegame")) {
				if(args.length >= 2) {
					player.sendMessage(game.disable());
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw disablegame [Game Name]");
				}
			}
			break;
		case "help" :
			if(player.hasPermission("treasurewars.help")) {
				if(args.length >= 1) {
					TextComponent url = new TextComponent("https://github.com/TTNO1/Treasure-Wars/wiki");
					url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/TTNO1/Treasure-Wars/wiki"));
					player.sendMessage("For help with Treasure Wars please visit the wiki: " + url);
				}else {
					player.sendMessage(ChatColor.GOLD + "/tw help");
				}
			}
			break;
		case "setup" :
			if(player.hasPermission("treasurewars.setup")) {
				if(Main.getSetupOf(player) == null) {
					Main.getSetups().add(new Setup(player));
				}else {
					player.sendMessage(ChatColor.RED + "Please finish or cancel the current setup first.");
				}
			}
			break;
		case "next" :
			if(Main.getSetupOf(player) != null) {
				Main.getSetupOf(player).nextStep(true);
			}
			break;
		case "cancel" :
			if(Main.getSetupOf(player) != null) {
				Main.getSetupOf(player).clear(true);
			}
			break;
		}
		
		
		return true;
	}

}
