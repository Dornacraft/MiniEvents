package fr.dornacraft.minievents.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class PlayerRemaining {
	public static void PlayerLeft(Main main) {
		String eventPrefix = ("§f[" + main.getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

		// Permet d'afficher, après l'élimination d'un joueur le nombre restant de
		// joueurs encore en jeu.
		if (main.getGameState() == GameState.STARTING || main.getGameState() == GameState.PLAYING) {
			// Permet juste d'éviter d'avoir le message :
			// "Il reste désormais 1 joueur" après l'élimination du dernier joueur avant la
			// fin de l'événement.
			if (main.getParticipants().size() >= 2) {
				for (UUID playerUUID : main.getParticipants()) {
					Player players = Bukkit.getPlayer(playerUUID);
					players.sendMessage(eventPrefix + "§7Il reste désormais §b" + (main.getParticipants().size()) + " joueurs !");
				}
			}
		}
	}
}
