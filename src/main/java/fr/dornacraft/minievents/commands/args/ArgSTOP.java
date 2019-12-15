package fr.dornacraft.minievents.commands.args;


import org.bukkit.entity.Player;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.OnStartEndOfEvent;

public class ArgSTOP {
	public static void ArgStop(Player player, Main main) {
		GameState state = main.getGameState();
		
		if (!(state == GameState.NOTSTARTED)) {
			OnStartEndOfEvent.EventStop(main);
		}
		else {
			player.sendMessage(main.prefix + "§cAucun événement est en cours.");
		}
	}
}
