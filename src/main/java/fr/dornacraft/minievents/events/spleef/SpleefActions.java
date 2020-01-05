package fr.dornacraft.minievents.events.spleef;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.PlayerRemaining;

public class SpleefActions {

	// *********************************************************************************************//
	public static void EventElimationMessageWater(Player players, Player playerOnWater, Main main) {
		players.sendMessage("§f[§b" + main.getGameName().name().toUpperCase() + "§f] §c" + playerOnWater.getName()
				+ " §7est tombé à l'eau !");
		PlayerRemaining.PlayerLeft(main);
	}
	// *********************************************************************************************//

	// *********************************************************************************************//

	public static ItemStack getSpleefShovel() {
		ItemStack it = new ItemStack(Material.DIAMOND_SPADE, 1, (short) 0);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName("§bLe Spleefator");
		itM.addEnchant(Enchantment.DIG_SPEED, 10, true);
		itM.setUnbreakable(true);
		it.setItemMeta(itM);
		return it;
	}

	// *********************************************************************************************//
}