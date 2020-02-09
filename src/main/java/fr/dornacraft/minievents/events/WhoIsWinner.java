package fr.dornacraft.minievents.events;

import java.util.List;
import java.util.UUID;

import fr.dornacraft.minievents.Main;

public class WhoIsWinner{
	
	public static void getWinner(List<UUID> participants, Main main) {
		if (participants.size() == 1) {
			OnStartEndOfEvent.EventStateFinish(main);
		}
		if (participants.size() == 0) {
			OnStartEndOfEvent.EventEndAndRAZ(main); //Si jamais il y a 0 vainqueur, on RAZ l'event
		}
	}
}
