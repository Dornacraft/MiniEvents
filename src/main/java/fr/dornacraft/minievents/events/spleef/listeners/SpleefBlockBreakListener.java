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

public class SpleefBlockBreakListener implements Listener {

	private Main main;

	public SpleefBlockBreakListener(Main main) {
		this.main = main;
	}

	// Evénement enregistrant chaque blocs cassés lors d'un SPLEEF
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		GameName name = main.getGameName();
		GameState state = main.getGameState();
		if (name == GameName.SPLEEF && state == GameState.PLAYING) {
			Player player = event.getPlayer();
			UUID playerUUID = player.getUniqueId();
			if (main.getParticipants().contains(playerUUID)) {
				Block block = event.getBlock();
				Material material = block.getType();
				if (material == Material.SNOW_BLOCK) {
					main.getBlockLoc().add(block.getLocation());
					event.setDropItems(false);
				} else {
					event.setCancelled(true);
				}
			}
		}
	}
}
