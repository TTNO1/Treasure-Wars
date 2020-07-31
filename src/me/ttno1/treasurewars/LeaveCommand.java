package me.ttno1.treasurewars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			if(Main.getGameOf((Player) sender) != null) {
				Main.getGameOf((Player) sender).leaveGame((Player) sender);
			}
		}
		
		return true;
	}

}
