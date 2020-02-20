package fr.dornacraft.minievents.events;

import org.bukkit.entity.Player;

import fr.dornacraft.minievents.Main;

public class PlayerElimination {

	// Téléporte le joueur au spawn de l'événement et le "réinitialise".
	public static void EventEliminationTP(Player player, Main main) {
		if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
			player.teleport(GetSpawnsParameters.LocationEventSpawn(main));
			PlayerSettings.setSettings(player);
		}
	}

	// Message d'élimination lorsqu'un joueur se téléporte en dehors du monde des
	// événements.
	public static void EventElimationMessageOutWorld(Player player, Player playerOut, Main main) {
		player.sendMessage("§f[§b" + main.getGameName().name().toUpperCase() + "§f] §c" + playerOut.getName()
				+ " §7n'est plus dans la zone de jeu !");

	}
	
	// Message d'élimination lorsqu'un joueur se déconnecte durant un état d'événement précis.
	public static void EventElimationMessageQuit(Player player, Player playerQuit, Main main) {
		player.sendMessage("§f[§b" + main.getGameName().name().toUpperCase() + "§f] §c" + playerQuit.getName()
				+ " §7a quitté l'événement !");
	}
}
