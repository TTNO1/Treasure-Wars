package me.ttno1.treasurewars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MainCommandTabComplete implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("newgame");
		list.add("newteam");
		list.add("oreplacemode");
		list.add("setspawn");
		list.add("settreasurearea");
		list.add("setlobby");
		list.add("setlobbyentity");
		list.add("newmerchant");
		list.add("newcaptain");
		list.add("removemerchant");
		list.add("removecaptain");
		list.add("removeteam");
		list.add("deletegame");
		list.add("updateworld");
		list.add("joingame");
		list.add("startgame");
		list.add("disablegame");
		list.add("help");
		list.add("setup");
		
		if(args.length > 1) {
			return null;
		}
		
		if(args.length == 1) {
			
			for(String string : list) {
				
				if(!string.startsWith(args[0])) {
					list.remove(string);
				}
				
			}
			
		}
		
		return list;
		
	}

}
