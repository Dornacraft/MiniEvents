package fr.dornacraft.minievents.events.enclume;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;

public class EnclumeCalculs {

	public static int getLowestPointX(int x1, int x2) {
		if (x1 <= x2) {
			int lowestPointX = x1;
			return lowestPointX;
		} else {
			int lowestPointX = x2;
			return lowestPointX;
		}
	}

	public static int getLowestPointZ(int z1, int z2) {
		if (z1 <= z2) {
			int lowestPointZ = z1;
			return lowestPointZ;
		} else {
			int lowestPointZ = z2;
			return lowestPointZ;
		}

	}

	public static int getHighestPointX(int x1, int x2) {
		if (x1 <= x2) {
			int highestPointX = x2;
			return highestPointX;
		} else {
			int highestPointX = x1;
			return highestPointX;
		}
	}

	public static int getHighestPointZ(int z1, int z2) {
		if (z1 <= z2) {
			int highestPointZ = z2;
			return highestPointZ;
		} else {
			int highestPointZ = z1;
			return highestPointZ;
		}
	}

	public static Integer getTheGround(Main main) {
		String pathToParameters = ("config-event.enclume.spawning-area.");
		int x1 = main.getConfig().getInt(pathToParameters + "first-point.x");
		int z1 = main.getConfig().getInt(pathToParameters + "first-point.z");
		int yPos = main.getConfig().getInt(pathToParameters + ".y");

		while (yPos > 0) {
			World worldName = GetEventWorld.getWorld(main);
			Integer xPos = x1;
			Integer zPos = z1;
			Location blockLoc = new Location(worldName, xPos, yPos, zPos);

			if (blockLoc.getBlock().getType() == Material.AIR || blockLoc.getBlock().getType() == Material.ANVIL) {
				yPos--;
			} else {
				int groundY = yPos + 1;
				return groundY;
			}
		}
		return 0;
	}
}
