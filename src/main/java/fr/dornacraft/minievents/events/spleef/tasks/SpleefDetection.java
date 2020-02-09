package fr.dornacraft.minievents.events.spleef.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;
import fr.dornacraft.minievents.events.PlayerElimination;
import fr.dornacraft.minievents.events.PlayerRemaining;
import fr.dornacraft.minievents.events.WhoIsWinner;
import fr.dornacraft.minievents.events.spleef.SpleefActions;

public class SpleefDetection extends BukkitRunnable {

	private Main main;

	public SpleefDetection(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		GameName name = main.getGameName();
		GameState state = main.getGameState();
		Integer waterLayer = main.getConfig().getInt("config-event.spleef.water-detection.y");

		String eventPrefix = ("§f[§b" + main.getGameName().name().toUpperCase() + "§f] ");

		if (name == GameName.SPLEEF && state == GameState.PLAYING) {
			if (main.getParticipants().size()>0) {
				for (UUID playerUUID : main.getParticipants()) {
					Player player = Bukkit.getPlayer(playerUUID);
	
					if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
						if (player.getLocation().getBlockY() <= waterLayer) {
							player.sendMessage(eventPrefix + "§7Tu as été éliminé, tu es tombé dans l'eau !");
							PlayerElimination.EventEliminationTP(player, main);
							
							// On supprime le joueur des participants et on regarde si il y a un gagnant.
							main.getParticipants().remove(player.getUniqueId());
							
							// On envoit à tous les joueurs dans le monde d'événement le message
							// d'élimination.
							for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
								if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
									SpleefActions.EventElimationMessageWater(pls, player, main);
								}
							}
							PlayerRemaining.PlayerLeft(main);
							
							WhoIsWinner.getWinner(main.getParticipants(), main);
						}
					}
				}
			}
			else {
				cancel();
			}
		} else {
			// Si l'état du jeu n'est plus en "PLAYING", alors on éteint cette boucle de
			// détection.
			cancel();
		}
	}
}
