package fr.dornacraft.minievents.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class CooldownOnEnd extends BukkitRunnable {

	private int cooldown = 10; // (en secondes)
	private Main main;

	public CooldownOnEnd(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		String eventPrefix = ("§f[§b" + main.getGameName().getName().toUpperCase() + "§f] ");
		List<UUID> participants = main.getParticipants();

		// Si l'état du jeu est en FINISH
		if (main.getGameState() == GameState.FINISH) {
			// ****************** Messages pour les joueurs ****************************//
			for (UUID playerUUID : participants) {
				Player player = Bukkit.getPlayer(playerUUID);
				// Quand le cooldown est égal à 5 secondes on affiche ce message :
				if (cooldown == 5) {
					player.sendMessage("§f[§b" + eventPrefix + "§f] §7Téléportation hors de la zone dans §b" + cooldown
							+ " §7secondes !");
				}
				// Quand le cooldown se termine, on affiche ce message :
				if (cooldown == 0) {
					player.sendMessage("§f[§b" + eventPrefix + "§f] §7Téléportation hors de la zone !");
					player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
					PlayerSettings.setSettings(player);
				}
			}
			// *************************************************************************//
			// Si le cooldown arrive à 0, on remet à 0 l'événement en arrêtant cette tâche.
			if (cooldown == 0) {
				OnStartEndOfEvent.EventEndAndRAZ(main);
				cancel();
			}

			// On décrémente la valeur "cooldown", pour permettre l'avancement de ce dernier
			// pour la finition de l'événement.
			cooldown--;
		}
		// Si l'état du jeu est différent de STARTING, on arrête cette tâche
		// (Simplement une sécurité)
		else {
			cancel();
		}
	}
}
