package fr.dornacraft.minievents.events;

import org.bukkit.Bukkit;
import org.bukkit.World;

import fr.dornacraft.minievents.Main;

public class GetEventWorld {

	public static String getName(Main main) {
		String eventWorldName = main.getConfig().getString("config-event.event-world.name");
		return eventWorldName;
	}

	public static World getWorld(Main main) {
		World eventWorld = Bukkit.getWorld(getName(main));
		return eventWorld;
	}
}
