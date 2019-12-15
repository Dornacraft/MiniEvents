package fr.dornacraft.minievents.events;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.enclume.EnclumeActions;
import fr.dornacraft.minievents.events.spleef.tasks.SpleefDetection;

public class WhichEventIs {
	public static void SelectEvent(Main main, String actionPath) {

		if (actionPath.equalsIgnoreCase("StartPlayingEvent")) {
			if (main.getGameName() == GameName.SPLEEF) {
				SpleefDetection start = new SpleefDetection(main);
				start.runTaskTimer(main, 0, 1);
			}
			if (main.getGameName() == GameName.ENCLUME) {
				EnclumeActions.StartingWaves(main);
			}
		}
	}
}
