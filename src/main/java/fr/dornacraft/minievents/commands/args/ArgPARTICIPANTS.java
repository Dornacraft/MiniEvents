package fr.dornacraft.minievents.commands.args;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class ArgPARTICIPANTS {
	public static void ArgParticipants(Main main, Player player) {
		if (!(main.getGameState() == GameState.NOTSTARTED)) {
			if (main.getParticipants().size() >= 1) {

				String participantsList = "";
				for (int i = 0; i < main.getParticipants().size(); i++) {
					Player participant = Bukkit.getPlayer(main.getParticipants().get(i));
					if (i == 0) {
						participantsList = ("§f" + participant.getName());
					} else {
						participantsList = (participantsList + "§7, §f" + participant.getName());
					}
				}
				player.sendMessage(main.prefix + "§7Liste des participants : " + participantsList);
			} else {
				player.sendMessage(main.prefix + "§cLa liste des participants est vide.");
			}
		} else {
			player.sendMessage(main.prefix + "§cAucun événement est en cours, aucune liste de participants.");
		}
	}
}
