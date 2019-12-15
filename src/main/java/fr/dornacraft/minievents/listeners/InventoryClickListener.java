package fr.dornacraft.minievents.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetSpawnsParameters;

public class InventoryClickListener implements Listener {

	private Main main;

	public InventoryClickListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		ItemStack currentType = event.getCurrentItem();
		Inventory inv = event.getInventory();
		
		if (currentType == null) {
			return;
		}
		if (inv.getName().equals("§0Menu des événements")) {
			Player player = (Player) event.getWhoClicked();
			String currentName = event.getCurrentItem().getItemMeta().getDisplayName();
			
			event.setCancelled(true);
			List<GameName> eventNames = Arrays.asList(GameName.values());
			for (int i = 0; i < eventNames.size(); i++) {
				String eventName = eventNames.get(i).name();
				if (currentName.equals("§fEvent:§f§l " + eventName.toUpperCase())) {
					messageAndTeleport(main, eventNames.get(i).name(), player);
					return;
				}

			}
		} else {
			return;
		}
	}

	private void messageAndTeleport(Main main, String eventName, Player player) {
		player.closeInventory();
		player.sendMessage(main.prefix + "§fTéléportation sur l'événement §b" + eventName.toUpperCase() + "§f.");
		player.teleport(GetSpawnsParameters.EventTeleportOnSpawn(main, eventName));
	}
}
