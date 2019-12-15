package fr.dornacraft.minievents.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.spleef.SpleefActions;

public class OnStartEndOfEvent {

	// Quand le chrono démarre
	public static void CooldownStart15(Player player, Main main) {
		main.setGameState(GameState.STARTING);
		player.teleport(GetSpawnsParameters.LocationSpawnArene(main));
		PlayerSettings.setSettings(player);
		if (main.getGameName() == GameName.SPLEEF) {
			player.getInventory().addItem(SpleefActions.getSpleefShovel());
		}
	}

	public static void CooldownStart0(Player player, Main main) {
		player.setGameMode(GameMode.SURVIVAL);
	}

	public static void EventFinish(Main main) {
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

	public static void EventFinishGame(Main main) {
		for (UUID playerUUID : main.getParticipants()) {
			Player player = Bukkit.getPlayer(playerUUID);
			player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
			PlayerSettings.setSettings(player);
		}

		main.getParticipants().clear();
		main.getLeaveDuringEvent().clear();
	}

	public static void EventStop(Main main) {
		Bukkit.broadcastMessage(main.prefix + "§cL'événement §c§l" + main.getGameName().name().toUpperCase() + "§r§c a été arrêté manuellement !");
		
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

		main.setGameName(GameName.NONE);
		main.setGameState(GameState.NOTSTARTED);
		main.getParticipants().clear();
		main.getLeaveDuringEvent().clear();
	}
}
