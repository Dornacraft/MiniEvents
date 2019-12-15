package fr.dornacraft.minievents.events;

import org.bukkit.entity.Player;

import fr.dornacraft.minievents.Main;

public class PlayerElimination {
	public static void EventEliminationTP(Player player, Main main) {
		if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
			player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
			PlayerSettings.setSettings(player);
		}
	}
	
	public static void EventElimationMessageOutWorld(Player player, Player playerOut, Main main) {
		player.sendMessage("§f[§b" + main.getGameName().name().toUpperCase() + "§f] §c" + playerOut.getName()
				+ " §7n'est plus dans la zone de jeu !");
		PlayerRemaining.PlayerLeft(main);
	}
	public static void EventElimationMessageQuit(Player player, Player playerQuit, Main main) {
		player.sendMessage("§f[§b" + main.getGameName().name().toUpperCase() + "§f] §c" + playerQuit.getName()
				+ " §7a quitté l'événement !");
		PlayerRemaining.PlayerLeft(main);
	}
}
