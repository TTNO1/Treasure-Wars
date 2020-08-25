package me.ttno1.treasurewars;

import java.lang.reflect.Constructor;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Dissolver {

	private Game game;
	private Block block;
	private BukkitRunnable task;
	private byte stage;
	private Random random;
	private Object position;
	private final static int dissolveTime = Main.getPlugin().getConfig().getInt("dissolveTime");
	
	Dissolver(Game game, Block block) {
		
		this.game = game;
		this.block = block;
		this.random = new Random();
		stage = 1;
		
		try {
			Constructor<?> blockPositionConstructor = Utils.getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class);
			position = blockPositionConstructor.newInstance(block.getX(), block.getY(), block.getZ());
		} catch(Exception e) {
			Main.getPlugin().getLogger().info("An error occurred while trying to dissolve wool (Block Position)");
			e.printStackTrace();
		}
		
		task = new BukkitRunnable() {
			@Override
			public void run() {
				if(!block.getType().toString().endsWith("WOOL")) {
					clear();
				}
				
				try {
					Constructor<?> constructor = Utils.getNMSClass("PacketPlayOutBlockBreakAnimation").getConstructor(int.class, Utils.getNMSClass("BlockPosition"), byte.class);
					Object packet = constructor.newInstance(random.nextInt(), position, stage);
					
					for(Player player : game.getPlayers()) {
						Utils.sendPacket(player, packet);
					}
					
				} catch(Exception e) {
					Main.getPlugin().getLogger().info("An error occurred while trying to dissolve wool (Packet)");
					e.printStackTrace();
				}
				
				if(stage == 9) {
					block.setType(Material.AIR);
					clear();
				}else {
					stage++;
				}
				
			}
		};
		
		task.runTaskTimer(Main.getPlugin(), 0, (dissolveTime / 9) * 20);
		
	}
	
	public void cancel() {
		
		task.cancel();
		
	}
	
	public void clear() {
		
		cancel();
		game.getDissolvers().remove(this);
		
	}
	
	public Block getBlock() {
		return block;
	}
	
}
