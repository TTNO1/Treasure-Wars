package me.ttno1.treasurewars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MainCommandTabComplete implements TabCompleter {
	
	private static ArrayList<String> commandList = new ArrayList<String>();
	
	static {
		commandList.add("newgame");
		commandList.add("newteam");
		commandList.add("oreplacemode");
		commandList.add("setspawn");
		commandList.add("settreasurearea");
		commandList.add("setlobby");
		commandList.add("setlobbyentity");
		commandList.add("newmerchant");
		commandList.add("newcaptain");
		commandList.add("removemerchant");
		commandList.add("removecaptain");
		commandList.add("removeteam");
		commandList.add("deletegame");
		commandList.add("updateworld");
		commandList.add("joingame");
		commandList.add("startgame");
		commandList.add("disablegame");
		commandList.add("leavegame");
		commandList.add("help");
		commandList.add("setup");
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		if(args.length > 1) {
			return null;
		}
		
		if(args.length == 1) {
			
			for(String string : commandList) {
				
				if(string.startsWith(args[0])) {
					list.add(string);
				}
				
			}
			
		}
		
		return list;
		
	}

}
