package fr.dornacraft.minievents.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class PlayerRemaining {
	public static void PlayerLeft(Main main) {
		
		if (main.getGameState() == GameState.PLAYING || main.getGameState() == GameState.WAITING) {
			String eventPrefix = ("§f[§b" + main.getGameName().name().toUpperCase() + "§f] ");
			if (main.getParticipants().size() >= 2) {
				for (UUID playerUUID : main.getParticipants()) {
					Player players = Bukkit.getPlayer(playerUUID);
					players.sendMessage(eventPrefix + "§7Il reste désormais " + (main.getParticipants().size()));
				}
			}
		}
	}
}
