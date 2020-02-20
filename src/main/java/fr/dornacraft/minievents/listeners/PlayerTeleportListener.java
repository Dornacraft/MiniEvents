package fr.dornacraft.minievents.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;
import fr.dornacraft.minievents.events.PlayerElimination;
import fr.dornacraft.minievents.events.PlayerRemaining;
import fr.dornacraft.minievents.events.WhoIsWinner;

public class PlayerTeleportListener implements Listener {

	private Main main;

	public PlayerTeleportListener(Main main) {
		this.main = main;
	}

	////////////////////////////////////////////////////
	// Lorsqu'un joueur se téléporte on vérifie si il
	// se téléporte en dehors du monde d'événement.
	//
	// Si c'est le cas, on le retire de la liste des
	// participants.
	////////////////////////////////////////////////////

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		GameState state = main.getGameState();
		List<UUID> participants = main.getParticipants();

		Location toLoc = event.getTo();
		String name = main.getGameName().name();
		String eventName = ("\n§f[§b" + name.toUpperCase() + "§f] ");

		Player player = event.getPlayer();

		// On vérifie si l'état de l'événement est en PLAYING. Que le joueur se trouve dans la
		// liste des participants. Et que la localisation finale de la téléportation est
		// en dehors du monde d'événement.
		if (state == GameState.PLAYING && participants.contains(player.getUniqueId())
				&& !toLoc.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {

			// Message personnel au joueur qui s'est téléporté.
			player.sendMessage(eventName + "§7Tu as été éliminé, tu t'es téléporté en dehors de l'événement !");

			// Envoit à tous les joueurs présent dans le monde événements, le
			// message d'élimination du joueur qui s'est téléporté.
			for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
				if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName(main))) {
					PlayerElimination.EventElimationMessageOutWorld(pls, player, main);
				}
			}
			// On retire le joueur des participants.
			main.getParticipants().remove(player.getUniqueId());
			// Indique le nombre de joueur restant.
			PlayerRemaining.PlayerLeft(main);
			// Après sa déconnexion, nous regardons si il y a un gagnant dans la partie.
			WhoIsWinner.getWinner(main.getParticipants(), main);
		}
	}
}
