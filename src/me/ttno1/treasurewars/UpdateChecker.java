package me.ttno1.treasurewars;

import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

	private int id;
	
	UpdateChecker(int id) {
		
		this.id = id;
		
	}
	
	public String getNewestVersion() {
		
		String newestVersion = null;
		
		try {
			Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=" + Integer.toString(id)).openStream());
			newestVersion = scanner.nextLine();
		} catch (Exception e) {
			Main.getPlugin().getLogger().info("An error occurred while trying to check for updates.");
			e.printStackTrace();
		}
		
		return newestVersion;
		
	}
	
	public boolean isUpToDate() {
		
		if(getNewestVersion().equals(Main.getPlugin().getDescription().getVersion())) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public int getId() {
		return id;
	}
	
}
