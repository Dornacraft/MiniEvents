package fr.dornacraft.minievents.events.enclume.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;
import fr.dornacraft.minievents.events.PlayerElimination;
import fr.dornacraft.minievents.events.WhoIsWinner;
import fr.dornacraft.minievents.events.enclume.EnclumeActions;

public class EnclumeEntityDamageListener implements Listener {

	private Main main;

	public EnclumeEntityDamageListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		GameName name = main.getGameName();
		GameState state = main.getGameState();
		String eventPrefix = ("§f[§b" + main.getGameName().name().toUpperCase() + "§f] ");
		
		if (name == GameName.ENCLUME && state == GameState.PLAYING) {
			Entity defender = event.getEntity();
			if (defender instanceof Player) {
				Player player = (Player) defender;
				UUID playerUUID = player.getUniqueId();
				if (main.getParticipants().contains(playerUUID)) {
					if (event.getCause() == DamageCause.FALLING_BLOCK) {
						Entity damager = event.getDamager();
						FallingBlock fallB = (FallingBlock) damager;
						if (fallB.getMaterial() == Material.ANVIL ) {
							event.setCancelled(true);
							player.sendMessage(eventPrefix + "§7Tu as été éliminé, tu as été touché par une enclume !");
							PlayerElimination.EventEliminationTP(player, main);
							
							for (Player players : Bukkit.getServer().getOnlinePlayers()) {
								if (players.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
									EnclumeActions.EventElimationMessage(players, player, main);
								}
							}
							main.getParticipants().remove(playerUUID);
							
							WhoIsWinner.getWinner(main.getParticipants(), main);
						}
					}
				}
			}
		}
	}
}
