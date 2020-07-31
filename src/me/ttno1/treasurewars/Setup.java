package me.ttno1.treasurewars;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class Setup{

	private static TextComponent next = new TextComponent(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "Next");
	private static TextComponent cancel = new TextComponent(ChatColor.RED + "" + ChatColor.UNDERLINE + "Cancel");
	
	static {
		next.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tw next"));
		cancel.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tw cancel"));
	}
	
	private Player player;
	private byte step;
	BukkitRunnable timeout;
	
	Setup(Player player) {
		
		this.player = player;
		step = 1;
		doStep();
		
		timeout = new BukkitRunnable() {
			@Override
			public void run() {
				player.sendMessage(ChatColor.RED + "Setup has been canceled: timed out.");
				clear(false);
			}
		};
		
		timeout.runTaskLater(Main.getPlugin(), 6000);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void resetTimeout() {
		
		timeout.cancel();
		timeout = null;
		timeout = new BukkitRunnable() {
			@Override
			public void run() {
				player.sendMessage(ChatColor.RED + "Setup has been canceled: timed out.");
				clear(false);
			}
		};
		timeout.runTaskLater(Main.getPlugin(), 6000);
		
	}
	
	public void nextStep(boolean doNext) {
		
		step++;
		
		resetTimeout();
		
		if(doNext) {
			doStep();
		}
		
	}
	
	public void doStep() {
		
		switch(step) {
		
		case 1 :
			sendStep("This guide will walk you through the setup proccess of a Treasure Wars game. You can stop at any time by clicking " + ChatColor.RED + "Cancel" + ChatColor.RESET + ". Click " + ChatColor.GREEN + "Next" + ChatColor.RESET + " if you wish to continue.", null);
			break;
		case 2 :
			sendStep("A \"game\" is an instance of Treasure Wars that has its own teams, world, merchants, etc. You cannot make a game in your main world. All Treasure Wars commands begin with \"/tw\" or \"/treasurewars\".", null);
			break;
		case 3 :
			sendStep("To create a game, go to the world in which you want this game to be and type or click on the command below.", "/tw newgame [Game Name] [Players Per Team] [Game Duration ex. 6.3 = 6min 30sec] [Maximum Points]");
			break;
		case 4 :
			sendStep("Next create a lobby, the lobby is the location where players are teleported when they finish a game, stand where you want the lobby to be and type or click on the command below.", "/tw setlobby [Game Name]");
			break;
		case 5 :
			sendStep("A lobby entity is a villager that players can click on to join a game, stand where you want it to be and use the command below. The villager will display its name and the status of the game above its head.", "/tw setlobbyentity [Game Name] [Villager Name]");
			break;
		case 6 :
			sendStep("The queue is the location where players wait for a game to start. Stand where you want it to be and use the command below.", "/tw setqueue [Game Name]");
			break;
		case 7 :
			sendStep("To add a team use the command below, you will need to do this for each team you want to add. The team color must be a color of dye.", "/tw newteam [Game Name] [Team Name] [Team Color (Dye Color)]");
			break;
		case 8 :
			sendStep("Each team needs a spawn point, stand where you want it to be and use the command below for each team.", "/tw setspawn [Game Name] [Team Name]");
			break;
		case 9 :
			sendStep("Each team also needs a treasure area, this is the area in which players can deposit their ores and loot from other teams. Use the command below and right-click the corners of the treasure area for each team.", "/tw settreasurearea [Game Name] [Team Name]");
			break;
		case 10 :
			sendStep("A Merchant is a villager that players can click on to buy items. Stand where you want it to be and type the command below for each Merchant. The name of the Merchant is not displayed anywhere it is only for reference.", "/tw newmerchant [Game Name] [Merchant Name]");
			break;
		case 11 :
			sendStep("A Captain is a villager that players can click on to buy upgrades for their team. Stand where you want it to be and type the command below for each Captain. The name of the Captain is not displayed anywhere it is only for reference.", "/tw newcaptain [Game Name] [Captain Name]");
			break;
		case 12 :
			sendStep("To register an ore in Treasure Wars, use the command below to toggle \"Ore Place Mode\". When in \"Ore Place Mode\" any ores that you place will be registered in the game as ores that a player can collect.", "/tw oreplacemode [Game Name]");
			break;
		case 13 :
			player.sendMessage(ChatColor.DARK_PURPLE + "------------------");
			player.sendMessage(ChatColor.UNDERLINE + "Other Useful Commands");
			player.sendMessage("/tw joingame [Game Name] - Joins the specified game.");
			player.sendMessage("/tw startgame [Game Name] - Force starts the specified game.");
			player.sendMessage("/tw disablegame [Game Name] - Toggles whether the game is disabled.");
			player.sendMessage("/tw leavegame [Game Name] - Leaves the specified game.");
			player.sendMessage("/tw updateworld [Game Name] - Saves the current state of the game's world to be what it resets to after the game finishes.");
			player.sendMessage(ChatColor.DARK_PURPLE + "------------------");
			player.sendMessage(cancel + "        " + next);
			player.sendMessage(ChatColor.DARK_PURPLE + "------------------");
			break;
		case 14 :
			player.sendMessage(ChatColor.GREEN + "Setup has ended.");
			clear(false);
			break;
			
		}
		
	}
	
	public void clear(boolean msg) {
		
		if(msg) {
			player.sendMessage(ChatColor.RED + "Setup has been canceled.");
		}
		
		timeout.cancel();
		Main.getSetups().remove(this);
		
	}
	
	public void sendStep(String message, String command) {
		
		TextComponent commandComponent = null;
		
		if(command != null) {
			commandComponent = new TextComponent(command);
			commandComponent.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, command));
		}
		
		player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "                        ");
		player.sendMessage(message);
		player.sendMessage(" ");
		player.spigot().sendMessage(commandComponent);
		player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "                        ");
		player.spigot().sendMessage(next, new TextComponent(ChatColor.RESET + "        "), cancel);
		player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "                        ");
		
	}
	
}
