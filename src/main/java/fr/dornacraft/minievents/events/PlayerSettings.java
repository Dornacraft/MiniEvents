package fr.dornacraft.minievents.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerSettings {
	public static void setSettings(Player player) {
		player.setGameMode(GameMode.ADVENTURE);
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setLevel(0);
		player.setInvulnerable(false);
		player.setFallDistance(0);
		player.getInventory().clear();
	}
}
