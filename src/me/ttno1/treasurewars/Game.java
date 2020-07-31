package me.ttno1.treasurewars;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Game {

	private static File statsFile = new File(Main.getPlugin().getDataFolder() + File.separator + "playerStats.yml");
	private static FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(statsFile);
	
	private String name;
	private World world;
	private double duration;
	private int playersPerTeam;
	private int maxPoints;
	private int maxPlayers;
	private LazyLocation lobbyLocation;
	private File file;
	private FileConfiguration config;
	private GameState state;
	private HashMap<UUID, OrePlaceListener> orePlaceListenerMap;
	private HashMap<UUID, OreBreakListener> oreBreakListenerMap;
	private ArrayList<Ore> ores;
	private ArrayList<GameTeam> teams;
	private ArrayList<Merchant> merchants;
	private ArrayList<Captain> captains;
	private ArrayList<Player> players;
	private LazyLocation queueLocation;
	private LobbyEntity lobbyEntity;
	private int queueTime;
	private LocalTime gameTime;
	private HashMap<Player, Integer> kills;
	private HashMap<Player, Integer> pointsEarned;
	private ArrayList<Class<?>> listenerClasses;
	private ArrayList<Object> listenerObjects;
	private ArrayList<Compass> compasses;
	private ArrayList<EyeOfSaline> eyesOfSaline;
	private ArrayList<Drowner> drowners;
	private ArrayList<Player> lootCooldown;
	private ArrayList<ZombiePigman> zombiePigmans;
	private BukkitRunnable timeTask;
	private HashMap<Player, GameTeam> teamSelection;
	
	@SuppressWarnings("unchecked")
	Game(String name){
		Main.getGames().add(this);
		this.name = name;
		file = new File(Main.getPlugin().getDataFolder() + File.separator + "games" + File.separator + name + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
		state = GameState.QUEUE;
		duration = config.getDouble("duration");
		playersPerTeam = config.getInt("playersPerTeam");
		maxPoints = config.getInt("maxPoints");
		world = Bukkit.getWorld(config.getString("world"));
		orePlaceListenerMap = new HashMap<UUID, OrePlaceListener>();
		oreBreakListenerMap = new HashMap<UUID, OreBreakListener>();
		queueLocation = (LazyLocation) config.get("queueLocation");
		lobbyLocation = (LazyLocation) config.get("lobbyLocation");
		players = new ArrayList<Player>();
		kills = new HashMap<Player, Integer>();
		pointsEarned = new HashMap<Player, Integer>();
		listenerClasses = new ArrayList<Class<?>>();
		listenerObjects = new ArrayList<Object>();
		ores = new ArrayList<Ore>();
		teams = new ArrayList<GameTeam>();
		merchants = new ArrayList<Merchant>();
		captains = new ArrayList<Captain>();
		compasses = new ArrayList<Compass>();
		eyesOfSaline = new ArrayList<EyeOfSaline>();
		drowners = new ArrayList<Drowner>();
		lootCooldown = new ArrayList<Player>();
		zombiePigmans = new ArrayList<ZombiePigman>();
		lobbyEntity = (LobbyEntity) config.get("lobbyEntity");
		teamSelection = new HashMap<Player, GameTeam>();
		
		ores = (ArrayList<Ore>) config.get("ores");
		teams = (ArrayList<GameTeam>) config.getList("teams");
		merchants = (ArrayList<Merchant>) config.getList("merchants");
		captains = (ArrayList<Captain>) config.getList("captains");

		if(teams != null) {
			maxPlayers = playersPerTeam * teams.size();
		}else {
			maxPlayers = 0;
		}
		
		listenerClasses.add(BlockBreakListener.class);
		listenerClasses.add(BlockPlaceListener.class);
		listenerClasses.add(DeathListener.class);
		listenerClasses.add(EntityDamageByEntityListener.class);
		listenerClasses.add(EntityExplodeListener.class);
		listenerClasses.add(HungerDepleteListener.class);
		listenerClasses.add(InventoryClickListener.class);
		listenerClasses.add(ItemConsumeListener.class);
		listenerClasses.add(ItemDamageListener.class);
		listenerClasses.add(PlayerInteractEntityListener.class);
		listenerClasses.add(PlayerInteractListener.class);
		listenerClasses.add(PlayerMoveListener.class);
		listenerClasses.add(RespawnListener.class);
		listenerClasses.add(EntitySpawnListener.class);
		
	}
	
	public int getPlayersPerTeam() {
		return playersPerTeam;
	}
	
	public String getName() {
		return name;
	}
	
	public World getWorld() {
		return world;
	}
	
	public ArrayList<GameTeam> getTeams() {
		return teams;
	}
	
	public FileConfiguration getConfig(){
		return config;
	}
	
	public File getFile() {
		return file;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public GameState getState() {
		return state;
	}
	
	public HashMap<Player, GameTeam> getTeamSelection() {
		return teamSelection;
	}
	
	public ArrayList<ZombiePigman> getZombiePigmans() {
		return zombiePigmans;
	}
	
	public ArrayList<Ore> getOres() {
		return ores;
	}
	
	public ArrayList<Player> getLootCooldown() {
		return lootCooldown;
	}
	
	public ArrayList<EyeOfSaline> getEyesOfSaline() {
		return eyesOfSaline;
	}
	
	public ArrayList<Compass> getCompasses() {
		return compasses;
	}
	
	public ArrayList<Drowner> getDrowners() {
		return drowners;
	}
	
	public LobbyEntity getLobbyEntity() {
		return lobbyEntity;
	}
	
	public EyeOfSaline getEyeOfSalineOf(Player player) {
		
		for(EyeOfSaline eye : eyesOfSaline) {
			if(eye.getPlayer().equals(player)) {
				return eye;
			}
		}
		
		return null;
		
	}
	
	public Drowner getDrownerOf(Player player) {
		
		for(Drowner drowner : drowners) {
			if(drowner.getPlayer().equals(player)) {
				return drowner;
			}
		}
		
		return null;
		
	}
	
	public void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			Main.getPlugin().getLogger().info("An errot occurred while trying to save file for game: " + name);
			e.printStackTrace();
		}
	}
	
	public int getMaxPoints() {
		return maxPoints;
	}
	
	public void addKill(Player player) {
		
		int score = kills.get(player);
		kills.put(player, score + 1);
		
	}
	
	public void addPointsEarned(Player player, int points) {
		
		int score = pointsEarned.get(player);
		pointsEarned.put(player, score + points);
		
	}
	
	public void queueScoreboard() {
		
		Scoreboard queueScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective queueObjective = queueScoreboard.registerNewObjective("queue", "dummy", ChatColor.BOLD + "" + ChatColor.GOLD + "Treasure Wars");
		queueObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		queueObjective.getScore(" ").setScore(7);
		queueObjective.getScore("Players: " + Integer.toString(players.size()) + "/" + Integer.toString(maxPlayers)).setScore(6);
		queueObjective.getScore("  ").setScore(5);
		queueObjective.getScore("Map: " + world.getName()).setScore(4);
		queueObjective.getScore("   ").setScore(3);
		queueObjective.getScore("Teams: " + Integer.toString(teams.size())).setScore(2);
		queueObjective.getScore("Team Size: " + Integer.toString(playersPerTeam) + " Players").setScore(1);
		queueObjective.getScore("    ").setScore(0);
		
		for(Player boardPlayer : players) {
			boardPlayer.setScoreboard(queueScoreboard);
		}
		
	}
	
	public String joinGame(Player player) {
		
		if(state.equals(GameState.QUEUE)) {
			
			if(players.contains(player)) {
				return ChatColor.RED + "You are already in that game.";
			}
			
			if(players.size() == maxPlayers) {
				return ChatColor.RED + "That game is already full and will begin shortly.";
			}
			
			player.teleport(queueLocation.toLocation());
			players.add(player);
			player.getInventory().addItem(Utils.customItem(Material.RED_BED, 1, "Leave Game", Utils.lore("", "Right click to leave game.", ""), null, 0, null, true, false));
			player.getInventory().addItem(Utils.customItem(Material.MAP, 1, "Select Team", Utils.lore("", "Right click to choose a team.", ""), null, 0, null, true, false));
			
			queueScoreboard();
			
			if(players.size() == maxPlayers) {
				
				queueTime = 10;
				
				BukkitRunnable task = new BukkitRunnable() {
					@Override
					public void run() {
						
						if(players.size() != maxPlayers) {
							this.cancel();
							for(Player timePlayer : players) {
								timePlayer.resetTitle();
							}
							return;
						}
						
						if(queueTime == 0) {
							this.cancel();
							if(players.size() == maxPlayers) {
								startGame();
							}
							for(Player timePlayer : players) {
								timePlayer.resetTitle();
							}
							return;
						}
						
						for(Player timePlayer : players) {
							timePlayer.sendTitle(ChatColor.YELLOW + Integer.toString(queueTime), "", 0, 20, 0);
						}
						
						queueTime--;
						
					}
				};
				
				task.runTaskTimer(Main.getPlugin(), 5, 20);
				
			}
			
			return "You have been placed in a queue. The game will start when the queue is full.";
			
		}else {
			switch(state) {
			case ACTIVE:
				return ChatColor.RED + "That game is currently active, please wait for it to end.";
			case DISABLED:
				return ChatColor.RED + "That game is disabled.";
			case RESETING:
				return ChatColor.RED + "Please wait for the game to reset.";
			default:
				return null;
			}
		}
		
	}
	
	public void selectTeam(Player player) {
		
		if(player.hasPermission("treasurewars.selectteam")) {
			
			Inventory inventory = Bukkit.createInventory(null, 54, "Team Selection");
			
			for(int i = 0; i < teams.size(); i++) {
				
				GameTeam team = teams.get(i);
				int playerCount = 0;
				
				for(Player teamPlayer : teamSelection.keySet()) {
					if(teamSelection.get(teamPlayer).equals(team)) {
						playerCount++;
					}
				}
				
				if(teamSelection.get(player).equals(team)) {
					inventory.setItem(i, Utils.customItem(Material.valueOf(team.getColor().toUpperCase() + "_WOOL"), 1, team.getName() + ChatColor.GRAY + " You", Utils.lore("", "Players: " + Integer.toString(playerCount) + "/" + Integer.toString(playersPerTeam), "", ChatColor.GRAY + "Click to Join"), Enchantment.PROTECTION_ENVIRONMENTAL, 1, null, true, true));
				}else {
					inventory.setItem(i, Utils.customItem(Material.valueOf(team.getColor().toUpperCase() + "_WOOL"), 1, team.getName(), Utils.lore("", "Players: " + Integer.toString(playerCount) + "/" + Integer.toString(playersPerTeam), "", ChatColor.GRAY + "Click to Join"), null, 0, null, true, false));
				}
				
			}
			
		}else {
			
			player.sendMessage(ChatColor.RED + "You do not have permission to choose a team.");
			
		}
		
	}
	
	public String disable() {
		
		switch(state) {
		case ACTIVE:
			endGame(EndReason.DISABLE);
			return ChatColor.DARK_PURPLE + "Please wait for the game to end ...";
		case DISABLED:
			state = GameState.QUEUE;
			return ChatColor.GREEN + "The game has been un-disabled.";
		case QUEUE:
			for(Player player : players) {
				leaveGame(player);
			}
			state = GameState.DISABLED;
			return ChatColor.GREEN + "The game has been disabled.";
		case RESETING:
			return ChatColor.DARK_PURPLE + "Please wait for the game to reset, then try again.";
		default:
			return null;
		}	
		
	}
	
	public void updateScoreboards() {
		
		for(Player player : players) {
			for(Team team : player.getScoreboard().getTeams()) {
				for(String entry : team.getEntries()) {
					if(entry.startsWith(team.getName() + ": ")) {
						int score = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(entry).getScore();
						team.removeEntry(entry);
						player.getScoreboard().resetScores(entry);
						player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(team.getName() + ": " + ChatColor.WHITE + Integer.toString(getTeam(team.getName()).getPoints())).setScore(score);
						team.addEntry(team.getName() + ": " + ChatColor.WHITE + Integer.toString(getTeam(team.getName()).getPoints()));
						break;
					}
				}
			}
		}
		
	}
	
	public void updateScoreboard(Player player) {
		
		for(String entry : player.getScoreboard().getEntries()) {
			if(entry.startsWith("Kills: ")) {
				player.getScoreboard().resetScores(entry);
				player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Kills: " + ChatColor.GREEN + Integer.toString(kills.get(player))).setScore(2);
			}
			if(entry.startsWith("Points Earned: ")) {
				player.getScoreboard().resetScores(entry);
				player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Points Earned: " + ChatColor.GREEN + Integer.toString(pointsEarned.get(player))).setScore(1);
			}
		}
		
	}
	
	public String leaveGame(Player player) {
		
		if(players.contains(player)) {
			if(state.equals(GameState.ACTIVE)) {
				
				players.remove(player);
				getTeamOf(player).getPlayers().remove(player);
				player.getInventory().clear();
				player.setHealth(20);
				player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				kills.put(player, 0);
				pointsEarned.put(player, 0);
				player.teleport(lobbyLocation.toLocation());
				
				if(players.size() == 0) {
					endGame(EndReason.CLOCK);
				}else {
					for(Player msgPlayer : players) {
						msgPlayer.sendMessage(getTeamOf(player).getChatColor() + player.getName() + ChatColor.RED + " Disconnected");
					}
				}
				
				return ChatColor.GREEN + "You have left the game.";
				
			}else if(state.equals(GameState.QUEUE)) {
				
				players.remove(player);
				player.getInventory().clear();
				player.teleport(lobbyLocation.toLocation());
				queueScoreboard();
				player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				
				return ChatColor.GREEN + "You have left the game.";
				
			}else {
				return ChatColor.RED + "Please wait for the game to reset.";
			}
		}else {
			return ChatColor.RED + "You are not in that game.";
		}
		
	}
	
	public void startGame() {
		
		if(world.getDifficulty().equals(Difficulty.PEACEFUL)) {
			world.setDifficulty(Difficulty.NORMAL);
		}
		world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		world.setGameRule(GameRule.DO_FIRE_TICK, false);
		world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
		world.setGameRule(GameRule.DO_INSOMNIA, false);
		world.setGameRule(GameRule.DO_MOB_LOOT, false);
		world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
		world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
		world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
		world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true);
		world.setGameRule(GameRule.KEEP_INVENTORY, true);
		
		for(Player player : teamSelection.keySet()) {
			
			teamSelection.get(player).addPlayer(player);
			
		}
		
		Collections.shuffle(teams);
		Collections.shuffle(players);
		
		for(Player player : players) {
			
			if(!teamSelection.containsKey(player)) {
				for(GameTeam team : teams) {
					if(team.addPlayer(player)) {
						break;
					}
				}
			}
			
		}
		
		for(GameTeam team : teams) {
			
			team.setPoints(0);
			team.spawnArmorStand();
			
		}
		
		gameTime = LocalTime.of(0 , (int) Math.floor(duration), (int) (duration - Math.floor(duration)), 0);
		
		for(Player player : players) {
			
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objective = scoreboard.registerNewObjective("scoreboard", "dummy", ChatColor.BOLD + "" + ChatColor.GOLD + "Treasure Wars");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Objective healthObj = scoreboard.registerNewObjective("Health", "health", ChatColor.RED + "\u2764");
			healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
			
			for(int i = 0; i < teams.size(); i++) {
				
				GameTeam team = teams.get(i);
				
				scoreboard.registerNewTeam(team.getName());
				
				scoreboard.getTeam(team.getName()).setAllowFriendlyFire(false);
				scoreboard.getTeam(team.getName()).setCanSeeFriendlyInvisibles(true);
				scoreboard.getTeam(team.getName()).setColor(team.getChatColor());
				
				for(Player teamPlayer : team.getPlayers()) {
					scoreboard.getTeam(team.getName()).addEntry(teamPlayer.getName());
				}
				
				objective.getScore(team.getName() + ": " + ChatColor.WHITE + Integer.toString(team.getPoints())).setScore(i + 6);
				scoreboard.getTeam(team.getName()).addEntry(team.getName() + ": " + ChatColor.WHITE + Integer.toString(team.getPoints()));
				
			}
			
			objective.getScore("     ").setScore(8 + teams.size());
			objective.getScore("Time: " + Utils.gameTimeFormat.format(gameTime)).setScore(7 + teams.size());
			objective.getScore("    ").setScore(6 + teams.size());
			objective.getScore("   ").setScore(5);
			objective.getScore("Points to Win: " + Integer.toString(maxPoints)).setScore(4);
			objective.getScore("  ").setScore(3);
			objective.getScore("Kills: " + ChatColor.GREEN + Integer.toString(kills.get(player))).setScore(2);
			objective.getScore("Points Earned: " + ChatColor.GREEN + Integer.toString(pointsEarned.get(player))).setScore(1);
			objective.getScore(" ").setScore(0);
			player.setScoreboard(scoreboard);
			
			player.getInventory().clear();
			
			ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
			chestplateMeta.setColor(getTeamOf(player).getDyeColor().getColor());
			chestplate.setItemMeta(chestplateMeta);
			player.getEquipment().setChestplate(chestplate);
			
			player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD, 1));
			
			player.setHealth(20);
			
			player.teleport(getTeamOf(player).getSpawn().toLocation());
			
		}
		
		for(int i = 0; i < listenerClasses.size(); i++) {
        	try {
        		Constructor<?> constructor = listenerClasses.get(i).getConstructor(Game.class);
				listenerObjects.add(constructor.newInstance(this));
	        	Bukkit.getServer().getPluginManager().registerEvents((Listener) listenerObjects.get(i), Main.getPlugin());
			} catch (Exception e) {
				Main.getPlugin().getLogger().info("Error while starting game (" + name + ") : Could not register listener class (" + listenerClasses.get(i).getSimpleName() + ")");
				e.printStackTrace();
			}
        }
		
		for(Merchant merchant : merchants) {
			merchant.spawn();
		}
		
		for(Captain captain : captains) {
			captain.spawn();
		}
		
		timeTask = new BukkitRunnable() {
			@Override
			public void run() {
				for(Player player : players) {
					
					player.getScoreboard().resetScores("Time: " + Utils.gameTimeFormat.format(gameTime));
					gameTime = gameTime.minusSeconds(1);
					player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Time: " + Utils.gameTimeFormat.format(gameTime)).setScore(7 + teams.size());
					
				}
			}
		};
		
		timeTask.runTaskTimer(Main.getPlugin(), 20, 20);
		
	}
	
	public void endGame(EndReason reason) {
		
		state = GameState.RESETING;
		lobbyEntity.updateStatus();
		
		switch(reason) {
		case CLOCK:
			for(Player player : players) {
				player.sendTitle(ChatColor.GOLD + "Draw", "Time is Up", 10, 100, 10);
			}
			break;
		case DISABLE:
			for(Player player : players) {
				player.sendTitle(ChatColor.RED + "Game Over", "This Game has Been Disabled", 10, 100, 10);
			}
			break;
		case WIN:
			
			GameTeam winner = teams.get(0);
			
			for(GameTeam team : teams) {
				if(team.getPoints() > winner.getPoints()) {
					winner = team;
				}
			}
			
			for(Player player : winner.getPlayers()) {
				player.sendTitle(ChatColor.GOLD + "Victory", "Your Team Won!", 10, 100, 10);
				
				int wins = statsConfig.getInt(player.getName() + ".wins");
				statsConfig.set(player.getName() + ".wins", wins + 1);
			}
			
			for(Player player : players) {
				if(!getTeamOf(player).equals(winner)) {
					player.sendTitle(ChatColor.RED + "Defeat", "You Lost", 10, 100, 10);
				}
			}
			
			break;
		default:
			break;
		}
		
		timeTask.cancel();
		timeTask = null;
		
		for(Merchant merchant : merchants) {
			merchant.kill();
		}
		
		for(Captain captain : captains) {
			captain.kill();
		}
		
		for(EyeOfSaline eye : eyesOfSaline) {
			eye.clear();
		}
		
		for(ZombiePigman pigman : zombiePigmans) {
			pigman.clear();
		}
		
		for(Compass compass : compasses) {
			compass.clear();
		}
		
		for(Drowner drowner : drowners) {
			drowner.clear();
		}
		
		lootCooldown.clear();
		
		for(Player player : players) {
			
			int killsInt;
			if(statsConfig.isSet(player.getName() + ".kills")) {
				killsInt = statsConfig.getInt(player.getName() + ".kills");
				statsConfig.set(player.getName() + ".kills", killsInt + kills.get(player));
			}else {
				statsConfig.set(player.getName() + ".kills", kills.get(player));
			}
			
			int pointsEarnedInt;
			if(statsConfig.isSet(player.getName() + ".pointsEarned")) {
				pointsEarnedInt = statsConfig.getInt(player.getName() + ".pointsEarned");
				statsConfig.set(player.getName() + ".pointsEarned", pointsEarnedInt + pointsEarned.get(player));
			}else {
				statsConfig.set(player.getName() + ".pointsEarned", pointsEarned.get(player));
			}
			
		}
		
		try {
			statsConfig.save(statsFile);
		} catch (IOException e1) {
			Main.getPlugin().getLogger().info("An error occured while trying to save the stats file.");
			e1.printStackTrace();
		}
		
		kills.clear();
		pointsEarned.clear();
		
		BukkitRunnable subTask = new BukkitRunnable() {
			@Override
			public void run() {
				
				Bukkit.getServer().unloadWorld(world, true);
				
				try {
					FileUtils.copyDirectory(new File(Main.getPlugin().getDataFolder() + File.separator + "worlds" + File.separator + world.getName()), world.getWorldFolder());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				WorldCreator worldCreator = new WorldCreator(config.getString("world"));
				world = Bukkit.getServer().createWorld(worldCreator);
				
				for(GameTeam team : teams) {
					team.clear();
				}
				
				if(reason.equals(EndReason.DISABLE)) {
					state = GameState.DISABLED;
				}else {
					state = GameState.QUEUE;
				}
				
				lobbyEntity.updateStatus();
				
			}
		};
		
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				
				for(Player player : players) {
					player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
					player.getInventory().clear();
					player.setGameMode(GameMode.SURVIVAL);
					player.getEnderChest().clear();
					player.setHealth(20);
					player.teleport(lobbyLocation.toLocation());
				}
				
				for(int i = 0; i < listenerObjects.size(); i++) {
			        HandlerList.unregisterAll((Listener) listenerObjects.get(i));
		        }
				
				listenerObjects.clear();
				
				players.clear();
				
				for(Entity entity : world.getEntities()) {
					if(!(entity instanceof Player)) {
						entity.remove();
					}
				}
				
				subTask.runTaskLater(Main.getPlugin(), 5);
				
			}	
		};
		
		task.runTaskLater(Main.getPlugin(), 200);
		
	}
	
	public String newTeam(String name, String color) {
		
		if(getTeam(name) != null) {
			return ChatColor.RED + "A team with that name already exists.";
		}
		
		Boolean isColor = false;
		
		for(DyeColor enumColor : DyeColor.values()) {
			if(enumColor.toString().equals(color.toUpperCase())) {
				isColor = true;
				break;
			}
		}
		
		if(isColor) {
			teams.add(new GameTeam(name, color.toUpperCase(), this));
			config.set("teams", teams);
			saveConfig();
			return ChatColor.GREEN + "Team created successfully.";
		}else {
			return ChatColor.RED + "Please enter a valid dye color (use underscores for spaces).";
		}
		
	}
	
	public String removeTeam(String name) {
		
		if(state.equals(GameState.DISABLED)) {
			if(getTeam(name) == null) {
				return ChatColor.RED + "A team with that name doesn't exist.";
			}
		
			teams.remove(getTeam(name));
			config.set("teams", teams);
			saveConfig();
			return ChatColor.GREEN + "Team removed successfully.";
		}else {
			return ChatColor.RED + "Please disable the game first.";
		}
		
	}
	
	public GameTeam getTeam(String name) {
		
		for(GameTeam team : teams) {
			if(team.getName().equals(name)) {
				return team;
			}
		}
		return null;
		
	}
	
	public Merchant getMerchant(String name) {
		
		for(Merchant merchant : merchants) {
			if(merchant.getName().equals(name)) {
				return merchant;
			}
		}
		return null;
		
	}
	
	public GameTeam getTeamOf(Player player) {
		
		for(GameTeam team : teams) {
			if(team.getPlayers().contains(player)) {
				return team;
			}
		}
		return null;
		
	}
	
	public String newMerchant(String name, LazyLocation location) {

		if(getMerchant(name) != null) {
			return ChatColor.RED + "A Merchant with that name already exists.";
		}
		
		merchants.add(new Merchant(location, name));
		config.set("merchants", merchants);
		saveConfig();
		return ChatColor.GREEN + "Merchant created successfully.";

	}
	
	public String removeMerchant(String name) {

		if(state.equals(GameState.DISABLED)) {
			if(getMerchant(name) == null) {
				return ChatColor.RED + "A Merchant with that name doesn't exist.";
			}
		
			merchants.remove(getMerchant(name));
			config.set("merchants", merchants);
			saveConfig();
			return ChatColor.GREEN + "Merchant removed successfully.";
		}else {
			return ChatColor.RED + "Please disable the game first.";
		}
		
	}
	
	public String newCaptain(String name, LazyLocation location) {

		if(getCaptain(name) != null) {
			return ChatColor.RED + "A Captain with that name already exists.";
		}
		
		captains.add(new Captain(location, name));
		config.set("captains", captains);
		saveConfig();
		return ChatColor.GREEN + "Captain created successfully.";

	}
	
	public String removeCaptain(String name) {

		if(state.equals(GameState.DISABLED)) {
			if(getCaptain(name) == null) {
				return ChatColor.RED + "A Captain with that name doesn't exist.";
			}
		
			captains.remove(getCaptain(name));
			config.set("captains", captains);
			saveConfig();
			return ChatColor.GREEN + "Captain removed successfully.";
		}else {
			return ChatColor.RED + "Please disable the game first.";
		}
		
	}
	
	public Captain getCaptain(String name) {
		
		for(Captain captain : captains) {
			if(captain.getName().equals(name)) {
				return captain;
			}
		}
		return null;
		
	}
	
	public String setQueueLocation(LazyLocation location) {
		
		queueLocation = location;
		config.set("queueLocation", location);
		saveConfig();
		return ChatColor.GREEN + "Queue location set successfully";
		
	}
	
	public String setLobbyEntity(LazyLocation location, String name) {
		
		if(lobbyEntity != null) {
			lobbyEntity.remove();
		}
		
		lobbyEntity = new LobbyEntity(this, location, name);
		
		config.set("lobbyEntity", lobbyEntity);
		saveConfig();
		
		return ChatColor.GREEN + "Lobby entity set successfully.";
		
	}
	
	public String setLobbyLocation(LazyLocation location) {
		
		if(!location.getWorld().equals(world)) {
			lobbyLocation = location;
			config.set("lobbyLocation", location);
			saveConfig();
			return ChatColor.GREEN + "Lobby location set successfully";
		}else {
			return ChatColor.RED + "The lobby cannot be in the same world as the game.";
		}
		
	}
	
	public String orePlaceMode(Player player) {
		
		if(state == GameState.DISABLED) {
			if(!orePlaceListenerMap.containsKey(player.getUniqueId())) {
				if(player.getWorld().equals(world)) {	
					orePlaceListenerMap.put(player.getUniqueId(), new OrePlaceListener(this, player));
					Bukkit.getServer().getPluginManager().registerEvents(orePlaceListenerMap.get(player.getUniqueId()), Main.getPlugin());
					oreBreakListenerMap.put(player.getUniqueId(), new OreBreakListener(this, player));
					Bukkit.getServer().getPluginManager().registerEvents(oreBreakListenerMap.get(player.getUniqueId()), Main.getPlugin());
					return ChatColor.AQUA + "Ore Place Mode has been " + ChatColor.BOLD + "ENABLED" + ChatColor.RESET + ChatColor.AQUA + " for game: " + name;
				}else {
					return ChatColor.RED + "You are in the wrong world, please go to " + world.getName() + " to use Ore Place Mode for that game.";
				}
			}else {
				HandlerList.unregisterAll(orePlaceListenerMap.get(player.getUniqueId()));
				orePlaceListenerMap.remove(player.getUniqueId());
				HandlerList.unregisterAll(oreBreakListenerMap.get(player.getUniqueId()));
				oreBreakListenerMap.remove(player.getUniqueId());
				try {
					saveWorld();
				} catch (IOException e) {
					e.printStackTrace();
					return ChatColor.RED + "Something went wrong while trying to save the world, check the console for more details.";
				}
				return ChatColor.AQUA + "Ore Place Mode has been " + ChatColor.BOLD + "DISABLED" + ChatColor.RESET + ChatColor.AQUA + " and the world has been saved for game: " + name;
			}
		}else {
			return ChatColor.RED + "You must disable the game before you can use ore place mode.";
		}
		
	}
	
	public Ore getOre(Block block) {
		
		if(ores != null) {
			for(Ore ore : ores) {
				if(ore.getLocation().equals(block.getLocation())) {
					return ore;
				}
			}
		}
		
		return null;
		
	}
	
	public boolean isOreBlock(Block block) {
		
		if(ores != null) {
			for(Ore ore : ores) {
				if(ore.getLocation().toLocation().equals(block.getLocation())) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public String updateWorld() {
		
		if(state.equals(GameState.DISABLED)) {
			
			try {
				saveWorld();
			} catch (IOException e) {
				e.printStackTrace();
				return ChatColor.RED + "Something went wrong while updating the world, check the console for more details.";
			}
			
			return ChatColor.GREEN + "World updated successfully";
			
		}else {
			return ChatColor.RED + "Please disable the game first.";
		}
		
	}
	
	public void saveWorld() throws IOException {
		
		FileUtils.copyDirectory(world.getWorldFolder(), new File(Main.getPlugin().getDataFolder() + File.separator + "worlds" + File.separator + world.getName()));
		
	}
	
	public String delete() {
		
		if(state.equals(GameState.DISABLED)) {
			
			File worldFile = new File(Main.getPlugin().getDataFolder() + File.separator + "worlds" + File.separator + world.getName());
			Utils.deleteFile(worldFile);
			Utils.deleteFile(file);
			Main.getGames().remove(this);
			
			return ChatColor.GREEN + "Game deleted successfully.";
					
		}else {
			return ChatColor.RED + "Please disable the game first.";
		}
		
	}
	
	public static String newGame(String nameParam, String playersPerTeamParam, String durationParam, String maxPointsParam, World world) {
		
		if(Main.getGame(nameParam) != null) {
			return ChatColor.RED + "A game with that name already exists.";
		}
		
		int playersPerTeam;
		double duration;
		int maxPoints;
		
		try {
		playersPerTeam = Integer.parseInt(playersPerTeamParam);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return ChatColor.RED + "Players per team must be a valid integer.";
		}
		
		try {
			maxPoints = Integer.parseInt(maxPointsParam);
			} catch(NumberFormatException e) {
				e.printStackTrace();
				return ChatColor.RED + "Maximum points must be a valid integer.";
			}
		
		try {
			duration = Double.parseDouble(durationParam);
			} catch(NumberFormatException e) {
				e.printStackTrace();
				return ChatColor.RED + "Game duration must be a valid number.";
			}
		
		if(duration - Math.floor(duration) > .59) {
			return ChatColor.RED + "Game duration must be a valid time.";
		}
		
		if(playersPerTeam < 1) {
			return ChatColor.RED + "Players per team must be greater than 0.";
		}
		
		if(world.equals(Bukkit.getWorld("world"))) {
			return ChatColor.RED + "You cannot make a game in your main world.";
		}
		
		File newGameFile = new File(Main.getPlugin().getDataFolder() + File.separator + "games" + File.separator + nameParam + ".yml");
		FileConfiguration newGameConfig = YamlConfiguration.loadConfiguration(newGameFile);
		
		try {
			newGameFile.createNewFile();
		} catch (IOException e) {
			Main.getPlugin().getLogger().info("Error while creating game: Could not create game YAML file.");
			e.printStackTrace();
			return ChatColor.RED + "Something went wrong, check the console for more details";
		}
		
		newGameConfig.set("world", world.getName());
		newGameConfig.set("duration", duration);
		newGameConfig.set("playersPerTeam", playersPerTeam);
		newGameConfig.set("maxPoints", maxPoints);
		
		try {
			newGameConfig.save(newGameFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			Main.getPlugin().getLogger().info("Error while creating game: Could not save game YAML file.");
			return ChatColor.RED + "Something went wrong, check the console for more details";
		}
		
		Main.getGames().add(new Game(nameParam));
		
		try {
			Main.getGame(nameParam).saveWorld();
		} catch (IOException e) {
			Main.getPlugin().getLogger().info("Error while creating game: Could not copy backup of world file, the game file was still created. Try manually copying world file or contact support.");
			e.printStackTrace();
			return ChatColor.RED + "Something went wrong, check the console for more details.";
		}
		
		return ChatColor.GREEN + "Game created successfully.";
		
	}
	
	public static enum GameState{
		QUEUE,
		ACTIVE,
		RESETING,
		DISABLED
	}
	
	public static enum EndReason{
		WIN,
		CLOCK,
		DISABLE
	}
	
}
