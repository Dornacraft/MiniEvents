package fr.dornacraft.minievents.events;

import org.bukkit.Bukkit;

public class BroadcastEventAnnouncement {

	public static void BroadcastMessage(String eventName) {
		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage("  §7§l** §6Un événement §b§l" + eventName.toUpperCase() + " §r§6va bientôt démarrer ! §7§l**");
		Bukkit.broadcastMessage(" §eTu veux y participer ? Fait la commande §b/event join §e!");
		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage(" §c(Tu seras téléporté dans un autre monde, sans ton stuff et dans une zone protégée !)");
		Bukkit.broadcastMessage(" ");
	}

}
