package fr.dornacraft.minievents;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dornacraft.minievents.commands.CommandEVENT;
import fr.dornacraft.minievents.commands.TabComplete;
import fr.dornacraft.minievents.events.enclume.listeners.EnclumeEntityDamageListener;
import fr.dornacraft.minievents.events.spleef.listeners.BlockBreakListener;
import fr.dornacraft.minievents.listeners.InventoryClickListener;
import fr.dornacraft.minievents.listeners.PlayerJoinListener;
import fr.dornacraft.minievents.listeners.PlayerQuitListener;
import fr.dornacraft.minievents.listeners.PlayerTeleportListener;

public class Main extends JavaPlugin {

	public String prefix = "§7[§6MiniEvents§7] "; // Prefix pour les messages dans le chat.
	private List<UUID> participants = new ArrayList<>(); // Liste des participants de l'événement.
	private List<UUID> leaveDuringEvent = new ArrayList<>(); // Liste des joueurs déconnectés durant un événement.
	private List<Location> blockLocation = new ArrayList<>(); // Liste de location de block
	private GameState state; // État de l'événement.
	private GameName name; // Nom de l'événement.

	@Override
	public void onEnable() {
		saveDefaultConfig();

		setGameState(GameState.NOTSTARTED);
		setGameName(GameName.NONE);
		activeCommands();
		activeListeners();
	}

	private void activeCommands() {
		getCommand("event").setExecutor(new CommandEVENT(this));
		getCommand("event").setTabCompleter(new TabComplete());
	}

	private void activeListeners() {
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

		// Listeners ENCLUME:
		getServer().getPluginManager().registerEvents(new EnclumeEntityDamageListener(this), this);
	}

	// *****************************************************************************************//
	// Méthode permettant de modifier l'état de l'event.
	public void setGameState(GameState state) {
		this.state = state;
	}

	// Méthode permettant de savoir l'état de l'event.
	public GameState getGameState() {
		return this.state;
	}
	// *****************************************************************************************//

	// *****************************************************************************************//
	// Méthode permettant de récuperer la liste des joueurs voulant jouer ou qui
	// sont entrain de jouer.
	public List<UUID> getParticipants() {
		return participants;
	}

	// Méthode permettant de récuperer la liste des joueurs déconnectés durant
	// l'événement.
	public List<UUID> getLeaveDuringEvent() {
		return leaveDuringEvent;
	}

	// Méthode permettant de récuperer une liste de locations de blocks
	public List<Location> getBlockLoc() {
		return blockLocation;
	}
	// *****************************************************************************************//

	// *****************************************************************************************//
	// Méthode permettant de modifier le type d'événement
	public void setGameName(GameName name) {
		this.name = name;
	}

	// Méthode permettant de savoir l'événement en cours de process.
	public GameName getGameName() {
		return this.name;
	}
	// *****************************************************************************************//
}
