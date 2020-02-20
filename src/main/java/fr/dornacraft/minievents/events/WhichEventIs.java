package fr.dornacraft.minievents.events;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.enclume.EnclumeActions;
import fr.dornacraft.minievents.events.spleef.tasks.SpleefDetection;

public class WhichEventIs {

	// Permet de sélectionner l'événement en cours et de le lancer en effectuant la
	// ou les premières actions nécéssaires.

	// Il faut savoir que le joueur passe automatiquement du mode de jeu Aventure au
	// mode de jeu Survie, lors du lancement.
	public static void SelectEvent(Main main) {
		if (main.getGameName() == GameName.SPLEEF) {
			SpleefDetection detection = new SpleefDetection(main);
			detection.runTaskTimer(main, 0, 1);
		}
		if (main.getGameName() == GameName.ENCLUME) {
			EnclumeActions.StartingWaves(main);
		}
		if (main.getGameName() == GameName.BOWSPLEEF) {

		}
	}
}
