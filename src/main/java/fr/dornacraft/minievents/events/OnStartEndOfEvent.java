package fr.dornacraft.minievents.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.spleef.SpleefActions;

public class OnStartEndOfEvent {

	// Cooldown de départ mais quand ce dernier commence.
	public static void CooldownStartOnStart(Player player, Main main) {
		main.setGameState(GameState.STARTING);
		player.teleport(GetSpawnsParameters.LocationSpawnArene(main));
		PlayerSettings.setSettings(player);
		if (main.getGameName() == GameName.SPLEEF) {
			player.getInventory().addItem(SpleefActions.getSpleefShovel());
		}
	}

	// Cooldown de départ mais quand ce dernier se termine.
	public static void CooldownStartOnEnd(Player player, Main main) {
		player.setGameMode(GameMode.SURVIVAL);
	}

	// Quand l'événement commence à se termine normalement avec 1 joueur restant
	// GameState.FINISH
	public static void EventStateFinish(Main main) {
		UUID playerWhoWinUUID = main.getParticipants().get(0);
		Player playerWhoWin = Bukkit.getPlayer(playerWhoWinUUID);
		String winner = playerWhoWin.getName();

		// Message pour tous les joueurs
		Bukkit.broadcastMessage("\n§f[§b" + main.getGameName().name().toUpperCase()
				+ "§f] §7Bravo à tous les participants de l'événement !");
		Bukkit.broadcastMessage("\n§f[§b" + main.getGameName().name().toUpperCase()
				+ "§f] §7Le grand vainqueur de cette événement n'est autre que le joueur §6§l" + winner + "§7 !\n ");

		playerWhoWin.setGameMode(GameMode.ADVENTURE);
		main.setGameState(GameState.FINISH);

		CooldownOnEnd start = new CooldownOnEnd(main);
		start.runTaskTimer(main, 0, 20);
	}

	// Quand l'événement se termine réellement (R.A.Z.)
	public static void EventEndAndRAZ(Main main) {
		EventRAZ(main);
	}

	// Quand un membre du personnel décide de faire la commande /event stop
	public static void EventStop(Main main) {
		Bukkit.broadcastMessage(main.prefix + "§cL'événement §c§l" + main.getGameName().name().toUpperCase()
				+ "§r§c a été arrêté manuellement !");

		if (main.getParticipants().size() >= 1) {
			for (UUID playerUUID : main.getParticipants()) {
				Player player = Bukkit.getPlayer(playerUUID);

				if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
					player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
					PlayerSettings.setSettings(player);
				}
				player.sendMessage(main.prefix + "§7Téléportation de tous les joueurs !");
			}
		}
		EventRAZ(main);
	}
	
	
//////////////////////////////////////////////////////////////////////////////
	private static void EventRegeneration(Main main) {
		for (Location blockLocation : main.getBlockLoc()) {
			blockLocation.getBlock().setType(Material.SNOW_BLOCK);
		}
		main.getBlockLoc().clear();
	}
	
	private static void EventRAZ(Main main) {
		main.setGameState(GameState.NOTSTARTED); // Le status de jeu change en "NOTSTARTED", l'event se termine
		main.setGameName(GameName.NONE); // Le jeu passe en "NONE" pour dire que aucun jeu n'a démarré.
		main.getParticipants().clear();
		main.getLeaveDuringEvent().clear();
		EventRegeneration(main); // Régénération de la map
	}
//////////////////////////////////////////////////////////////////////////////
}
