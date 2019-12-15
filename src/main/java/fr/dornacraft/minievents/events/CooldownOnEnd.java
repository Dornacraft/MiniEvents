package fr.dornacraft.minievents.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class CooldownOnEnd extends BukkitRunnable {

	private int cooldown = 10;
	private Main main;

	public CooldownOnEnd(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		GameName name = main.getGameName();
		List<UUID> participants = main.getParticipants();
		if (main.getGameState() == GameState.FINISH) {
			for (UUID playerUUID : participants) {
				Player player = Bukkit.getPlayer(playerUUID);
				// Quand le cooldown est égal à 5 secondes on affiche ce message :
				if (cooldown == 5) {
					player.sendMessage("§f[§b" + name.name().toUpperCase()
							+ "§f] §7Téléportation hors de la zone dans §b" + cooldown + " §7secondes !");
				}
				// Quand le cooldown se termine, on affiche ce message :
				if (cooldown == 0) {
					player.sendMessage("§f[§b" + name.name().toUpperCase() + "§f] §7Téléportation hors de la zone !");
					OnStartEndOfEvent.EventFinishGame(main); // Action à effectuer à la fin du cooldown de fin
				}
			}
			if (cooldown == 0) {
				main.setGameState(GameState.NOTSTARTED); // Le status de jeu change en "NOTSTARTED", l'event se termine
				main.setGameName(GameName.NONE); // Le jeu passe en "NONE" pour dire que aucun jeu n'a démarré.
				cancel();
			}
			cooldown--;
		} else {
			cancel();
		}
	}
}
