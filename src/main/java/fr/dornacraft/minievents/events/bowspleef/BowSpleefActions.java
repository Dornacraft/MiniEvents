package fr.dornacraft.minievents.events.bowspleef;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowSpleefActions {

	public static ItemStack getFireBow() {
		ItemStack it = new ItemStack(Material.BOW, 1, (short) 0);
		ItemMeta itM = it.getItemMeta();
		itM.setDisplayName("§cFire Bow");
		itM.addEnchant(Enchantment.ARROW_FIRE, 10, true);
		itM.setUnbreakable(true);
		it.setItemMeta(itM);
		return it;
	}

}
