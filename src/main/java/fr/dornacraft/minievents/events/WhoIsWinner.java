package fr.dornacraft.minievents.events;

import java.util.List;
import java.util.UUID;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;

public class WhoIsWinner {
	public static void getWinner(List<UUID> participants, Main main) {
		if (participants.size() == 1) {
			OnStartEndOfEvent.EventFinish(main);
		}
		if (participants.size() == 0) {
			main.setGameName(GameName.NONE);
			main.setGameState(GameState.NOTSTARTED);
			main.getParticipants().clear();
			main.getLeaveDuringEvent().clear();
		}
	}
}
