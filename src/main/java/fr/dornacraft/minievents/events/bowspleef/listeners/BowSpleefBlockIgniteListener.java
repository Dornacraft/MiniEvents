package fr.dornacraft.minievents.events.bowspleef.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

import fr.dornacraft.minievents.Main;

public class BowSpleefBlockIgniteListener implements Listener {

	private Main main;
	
	public BowSpleefBlockIgniteListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onIgnite(BlockIgniteEvent event) {
		if (event.getIgnitingEntity().getType() ==  EntityType.ARROW) {
			if (event.getBlock().getType() == Material.TNT) {
				main.getBlockLoc().add(event.getBlock().getLocation());
			}
			else {
				event.setCancelled(true);
			}
		}
	}
}
