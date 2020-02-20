package fr.dornacraft.minievents;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.dornacraft.minievents.events.bowspleef.BowSpleefActions;
import fr.dornacraft.minievents.events.spleef.SpleefActions;

// Liste des événement disponible
//
// Ce sont aussi les événements qui seront marqués quand la commande affichant 
// la liste des évenement sera éxécutée.

public enum GameName {
	NONE("NONE", "Aucun", false, 2, null), 
	SPLEEF("SPLEEF", "Spleef", true, 0, "pelle"), 
	ENCLUME("ENCLUME", "Enclume", false, 2, null), 
	BOWSPLEEF("BOWSPLEEF", "BowSpleef", true, 2, "arc"), 
	//LAINE(), 
	//TNT_RUN(), 
	//DAC(), 
	//SMASH()
	;
	
	private String name;
	private boolean invulnerable;
	private int gamemode;
	private String itemName;
	
	private GameName(final String realName, final String name, final boolean invulnerable, final int gamemode, final String itemName) {
		this.name = name;
		this.invulnerable = invulnerable;
		this.gamemode = gamemode;
		this.itemName = itemName;
	}
	
	//PLUS TARD
	/*public static GameName getByName(final String name) {
	        GameName gameType = null;
	        int i = 0;
	        while (gameType == null && i < values().length) {
	            if (values()[i].getName().equals(name)) {
	                gameType = values()[i];
	            }
	            else {
	                ++i;
	            }
	        }
	        return gameType;
	    }*/
	 
	public String getName() {
	    return this.name;
	}
	
	public boolean getVulnerable() {
        return this.invulnerable;
    }
	
	public GameMode getGamemode() {
		if (this.gamemode == 0)
			return GameMode.SURVIVAL;
		else if (this.gamemode == 1)
			return GameMode.CREATIVE;
		else if (this.gamemode == 2)
			return GameMode.ADVENTURE;
		else if (this.gamemode == 3)
			return GameMode.SPECTATOR;
		else
			return null;
    }
	
	public void getSpecificItem(Player player) {
		if (this.itemName == null) {
			return;
		}
		else if (this.itemName.equals("pelle")) {
			player.getInventory().addItem(SpleefActions.getSpleefShovel());
		}
		else if (this.itemName.equals("arc")) {
			player.getInventory().addItem(BowSpleefActions.getFireBow());
		}
		else {
			return;
		}
	}
}
