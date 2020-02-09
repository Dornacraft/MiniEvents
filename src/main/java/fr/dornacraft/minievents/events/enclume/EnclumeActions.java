package fr.dornacraft.minievents.events.enclume;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;
import fr.dornacraft.minievents.events.PlayerRemaining;
import fr.dornacraft.minievents.events.enclume.tasks.EnclumeAnvilClear;
import fr.dornacraft.minievents.events.enclume.tasks.EnclumeMiniWavesTimer;

public class EnclumeActions {

	public static void StartingWaves(Main main) {
		int waveNumber = 1;

		EnclumeMiniWavesTimer start = new EnclumeMiniWavesTimer(main, waveNumber);
		start.runTaskTimer(main, 0, 1);

		Integer groundY = EnclumeCalculs.getTheGround(main);
		EnclumeAnvilClear.ClearParameters(main, groundY);
	}

	public static void GenerateAnvil(Main main, List<Integer> blockPosX, List<Integer> blockPosZ, int waveNumber) {
		String pathToParameters = ("config-event.enclume.spawning-area.");
		int yPos = main.getConfig().getInt(pathToParameters + ".y");

		for (int i = 0; i < blockPosX.size(); i++) {
			World worldName = GetEventWorld.getWorld(main);
			Integer xPos = blockPosX.get(i);
			Integer zPos = blockPosZ.get(i);
			Location anvilLoc = new Location(worldName, xPos, yPos, zPos);
			anvilLoc.getBlock().setType(Material.ANVIL);
		}
	}

	/*public static void ClearAnvil(Main main, int lowestPointX, int lowestPointZ, int highestPointX, int highestPointZ) {

	}*/

	public static void EventElimationMessage(Player players, Player playerEliminated, Main main) {
		players.sendMessage("§f[§b" + main.getGameName().name().toUpperCase() + "§f] §c" + playerEliminated.getName()
				+ " §7s'est fait écraser !");
		PlayerRemaining.PlayerLeft(main);
	}

}
