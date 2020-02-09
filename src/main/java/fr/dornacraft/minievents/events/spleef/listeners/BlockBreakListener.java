package fr.dornacraft.minievents.events.spleef.listeners;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class BlockBreakListener implements Listener {
	
	private Main main;
	public BlockBreakListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		GameName name = main.getGameName();
		GameState state = main.getGameState();
		System.out.println("block cassé");
		if (name == GameName.SPLEEF && state == GameState.PLAYING) {
			Player player = event.getPlayer();
			UUID playerUUID = player.getUniqueId();
			if (main.getParticipants().contains(playerUUID)) {
				Block block = event.getBlock();
				Material material = block.getType();
				if (material == Material.SNOW_BLOCK) {
					  main.getBlockLoc().add(block.getLocation());
				}
				else {
					event.setCancelled(true);
				}
			}
		}
	}
}
