package fr.dornacraft.minievents.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class CooldownOnStart extends BukkitRunnable {

	private int cooldown = 15;
	private Main main;

	public CooldownOnStart(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		List<UUID> participants = main.getParticipants();
		String eventPrefix = ("§f[§b" + main.getGameName().name().toUpperCase() + "§f] ");

		if (main.getGameState() == GameState.STARTING) {

			// Si la tailel de la liste des participants est différente de 0 on affiche les
			// messages
			// aux joueurs participants sinon on envoit rien. (mais le cooldown se
			// décrémente encore)
			if (participants.size() > 0) {
				// ****************** Messages pour les joueurs ****************************//
				for (UUID playerUUID : participants) {
					Player player = Bukkit.getPlayer(playerUUID);
					player.setLevel(cooldown);
					if (cooldown == 15 || cooldown == 10 || cooldown == 5 || cooldown == 4 || cooldown == 3
							|| cooldown == 2) {
						player.sendMessage(eventPrefix + "§7Le jeu commence dans §b" + cooldown + " §7secondes !");
						if (cooldown == 15) {
							OnStartEndOfEvent.CooldownStartOnStart(player, main);
						}
					}
					if (cooldown == 1) {
						player.sendMessage(eventPrefix + "§7Le jeu commance dans §b" + cooldown + " §7seconde !");
					}
					if (cooldown == 0) {
						player.sendMessage(eventPrefix + "§7Le jeu commence ! À vos pelles et bonne chance à tous !");
						OnStartEndOfEvent.CooldownStartOnEnd(player, main);
					}
				}
				// *************************************************************************//
				if (cooldown == 0 && participants.size() > 0) {
					/*if (participants.size() <= 1) {
						WhoIsWinner.getWinner(participants, main);
						cancel();
					} else {*/
						main.setGameState(GameState.PLAYING); // On met le status de jeu en "PLAYING"

						WhichEventIs.SelectEvent(main, "StartPlayingEvent");
						cancel();
					//}
				}
			} else {
				main.setGameState(GameState.WAITING); // On remet le status de jeu en "WAITING".
				// On envoit un petit message à tous les joueurs du serveur.
				Bukkit.broadcastMessage(main.prefix + "§cIl n'y a plus assez de joueurs pour commencer l'événement.");
				Bukkit.broadcastMessage("  §7Tu peux faire la commande : §b/event join §7pour rejoindre l'event §b"
						+ main.getGameName().name().toUpperCase());
				// Et on enleve le cooldown.
				cancel();
			}
		} else {
			// Si cependant l'état du jeu est différent de "STARTING" on annule directement
			// cette boucle
			cancel();
		}
		cooldown--;
	}
}