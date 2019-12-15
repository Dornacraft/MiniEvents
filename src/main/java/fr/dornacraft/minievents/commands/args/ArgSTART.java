package fr.dornacraft.minievents.commands.args;

import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.BroadcastEventAnnouncement;

public class ArgSTART {

	public static void ArgStart(Player player, Main main, GameName eventName) {
		GameState state = main.getGameState();
		String prefix = main.prefix;

		if (state == GameState.STARTING || state == GameState.PLAYING || state == GameState.FINISH
				|| state == GameState.WAITING) {
			player.sendMessage(prefix + "§cUn événement est déjà en cours, impossible d'en lancer un autre.");
		}

		if (state == GameState.NOTSTARTED) {
			main.setGameState(GameState.WAITING); // On met l'évenement en WAITING (attente de joueurs).
			main.setGameName(eventName); // On dit que l'événement sera celui qu'on a écrit dans le commande.

			BroadcastEventAnnouncement.BroadcastMessage(main.getGameName().name()); // Message d'annonce dans le chat.
		}
	}
}
