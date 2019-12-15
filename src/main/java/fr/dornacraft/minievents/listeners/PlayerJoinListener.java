package fr.dornacraft.minievents.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;
import fr.dornacraft.minievents.events.PlayerElimination;
import fr.dornacraft.minievents.events.PlayerSettings;

public class PlayerJoinListener implements Listener {

	private Main main;

	public PlayerJoinListener(Main main) {
		this.main = main;
	}

	////////////////////////////////////////////////////
	// Lorsqu'un joueur se connecte on vérifie si il
	// est dans la liste de ceux qui ont quitté
	// l'événement en cours de jeu.
	//
	// Selon l'état du jeu on le rajoute dans le jeu ou
	// pas.
	////////////////////////////////////////////////////

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// Informations sur l'état et nom du jeu et la liste des participants et de ceux
		// qui ont quitté l'événement.
		List<UUID> leaveDuringEvent = main.getLeaveDuringEvent();
		List<UUID> participants = main.getParticipants();
		GameName name = main.getGameName();
		GameState state = main.getGameState();

		// On récupère le joueur qui s'est connecté.
		Player player = event.getPlayer();

		if ((!(name == GameName.NONE)) && leaveDuringEvent.contains(player.getUniqueId())) {

			// Joueur se reconnecte quand l'événement est en PLAYING ou en FINISH:
			if (state == GameState.PLAYING || state == GameState.FINISH) {
				PlayerElimination.EventEliminationTP(player, main);
				player.sendMessage(main.prefix
						+ "§7Désolé mais l'événement est en cours, tu as été téléporté au spawn de l'événement.");
			}

			// Joueur se reconnecte quand l'événement est en WAITING:
			if (state == GameState.WAITING) {
				String eventName = main.getGameName().name().toLowerCase();
				Integer maxPlayers = main.getConfig().getInt("config-event." + eventName + ".players.max-players");
				if (participants.size() < maxPlayers) {
					participants.add(player.getUniqueId()); // On le rajoute dans la liste des participants
					leaveDuringEvent.remove(player.getUniqueId()); // On le retire de la liste de ceux qui ont quitté

					// Broadcast d'annonce, annonçant que le joueur à rejoint l'événement.
					Bukkit.broadcastMessage(player.getName() + "§7 s'est reconnecté §7(§a"
							+ main.getParticipants().size() + "§7/§a" + maxPlayers + "§7).");
				} else {
					PlayerElimination.EventEliminationTP(player, main);
					player.sendMessage(main.prefix
							+ "§7Désolé mais lors de ta déconnexion, la liste d'attente est devenue pleine. Tu as été téléporté au spawn de l'événement.");
				}
			}
			if (state == GameState.STARTING) {
				participants.add(player.getUniqueId()); // On le rajoute dans la liste des participants
				leaveDuringEvent.remove(player.getUniqueId()); // On le retire de la liste de ceux qui ont quitté
				player.sendMessage(main.prefix
						+ "§7Tu t'es reconnecté juste avant le départ de l'événement, tu peux y participer.");
			}

		}
		// Le joueur s'est déconnecté dans le monde d'événement et que pendant ce temps
		// l'événement s'est terminé et qu'il ne se trouve plus dans aucune liste
		if (name == GameName.NONE && player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))
				&& (!leaveDuringEvent.contains(player.getUniqueId())) && (!player.isOp())) {
			Location loc = new Location(Bukkit.getWorld("Event_World"), 0.5, 95.6, -0.5, 0f, 0f);
			player.teleport(loc);
			PlayerSettings.setSettings(player);
		}

	}
}
