package fr.dornacraft.minievents.commands.args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dornacraft.minievents.GameName;

public class ArgTELEPORT {
	public static void ArgTeleport(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, "§0Menu des événements");

		List<GameName> eventNames = new ArrayList<>(Arrays.asList(GameName.values()));
		eventNames.remove(GameName.NONE);

		for (int i = 0; i < eventNames.size(); i++) {
			String eventName = eventNames.get(i).name().toUpperCase();
			if (eventNames.get(i) == GameName.SPLEEF) {
				inv.setItem(i, getSpecificItem(Material.DIAMOND_SPADE, 0, true, "§fEvent:§f§l " + eventName));

			} else if (eventNames.get(i) == GameName.ENCLUME) {
				inv.setItem(i, getSpecificItem(Material.ANVIL, 0, false, "§fEvent:§f§l " + eventName));

			} else {
				inv.setItem(i, getBasicItem(Material.INK_SACK, 8, "§fEvent:§f§l " + eventName));
			}
		}
		player.openInventory(inv);

	}

	private static ItemStack getSpecificItem(Material material, int damage, boolean enchant, String customName) {
		if (damage < 0)
			damage = 0;
		ItemStack item = new ItemStack(material, 1, (short) damage);
		ItemMeta itemMeta = item.getItemMeta();
		if (customName != null)
			itemMeta.setDisplayName(customName);
		if (enchant) {
			itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(itemMeta);
		return item;
	}

	private static ItemStack getBasicItem(Material material, int damage, String customName) {
		ItemStack item = new ItemStack(material, 1, (short) damage);
		ItemMeta itemMeta = item.getItemMeta();
		if (customName != null)
			itemMeta.setDisplayName(customName);
		item.setItemMeta(itemMeta);
		return item;
	}
}
