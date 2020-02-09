package fr.dornacraft.minievents.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;
import fr.dornacraft.minievents.events.PlayerElimination;
import fr.dornacraft.minievents.events.PlayerRemaining;
import fr.dornacraft.minievents.events.WhoIsWinner;

public class PlayerQuitListener implements Listener {

	private Main main;

	public PlayerQuitListener(Main main) {
		this.main = main;
	}

	////////////////////////////////////////////////////
	// Lorsqu'un joueur se deconnecte on vérifie si il
	// est dans la liste des participants.
	//
	// On le met ensuite dans la liste des déconnectés.
	////////////////////////////////////////////////////
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		// Information sur l'état du jeu.
		GameState state = main.getGameState();

		// Information sur l'événement de déconnexion du joueur.
		UUID playerUUID = event.getPlayer().getUniqueId();
		if (state == GameState.WAITING || state == GameState.STARTING || state == GameState.PLAYING
				|| state == GameState.FINISH) {
			if (main.getParticipants().contains(playerUUID)) {

				Player disconnectedPlayer = Bukkit.getPlayer(playerUUID);
				main.getLeaveDuringEvent().add(playerUUID); // On l'ajoute dans liste des déconnectés.
				main.getParticipants().remove(playerUUID); // On le supprime de la liste des participants.

				if (state == GameState.WAITING) {
					String eventName = main.getGameName().name().toLowerCase();
					Integer maxPlayers = main.getConfig().getInt("config-event." + eventName + ".players.max-players");
					
					// Broadcast d'annonce, annonçant que le joueur à rejoint l'événement.
					Bukkit.broadcastMessage(disconnectedPlayer.getName() + "§7 s'est déconnecté §7(§c"
							+ main.getParticipants().size() + "§7/§c" + maxPlayers + "§7).");
				}
				// Boucle qui envoit à tous les joueurs présent dans le monde événements, le
				// message d'élimination du joueur qui s'est déconnecté.
				if (state == GameState.PLAYING) {
					for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
						if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
							PlayerElimination.EventElimationMessageQuit(pls, disconnectedPlayer, main);
						}
					}
					PlayerRemaining.PlayerLeft(main);
					
					// On regarde si après l'avoir supprimé de la liste des participants il y a un
					// gagnant dans l'événement en cours.
					WhoIsWinner.getWinner(main.getParticipants(), main);
				}

			}
		}
	}
}
