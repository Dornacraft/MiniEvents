package fr.dornacraft.minievents.commands.args;

import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.CooldownOnStart;

public class ArgFORCERUN {

	public static void ArgForceRun(Player player, Main main) {
		GameState state = main.getGameState();
		GameName name = main.getGameName();
		String prefix = main.prefix;

		if (state == GameState.STARTING || state == GameState.PLAYING || state == GameState.FINISH) {
			player.sendMessage(prefix + "§cUn événement est déjà en cours, impossible d'en lancer un autre.");
		} else if (state == GameState.NOTSTARTED) {
			player.sendMessage(prefix
					+ "§cAucun événement en cours, fait la commande §b/event start <§7event§b>§c pour lancer un événement.");
		} else if (state == GameState.WAITING) {
			if (name == GameName.SPLEEF || name == GameName.ENCLUME) {
				if (main.getParticipants().size() >= 1) {
					main.setGameState(GameState.STARTING);

					CooldownOnStart start = new CooldownOnStart(main);
					start.runTaskTimer(main, 0, 20);
				} else {
					if (main.getParticipants().size() == 0) {
						player.sendMessage(prefix + "§cPour forcer le début d'une partie, il faut minimum 1 joueur.");
					}
				}
			}
		}

	}

}
