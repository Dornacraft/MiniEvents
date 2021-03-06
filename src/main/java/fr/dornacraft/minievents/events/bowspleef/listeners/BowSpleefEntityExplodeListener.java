package fr.dornacraft.minievents.events.bowspleef.listeners;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;

public class BowSpleefEntityExplodeListener implements Listener {

	private Main main;

	public BowSpleefEntityExplodeListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		GameName eventName = main.getGameName();
		GameState eventState = main.getGameState();

		if (entity.getLocation().getWorld().getName().equals(GetEventWorld.getName(main))) {
			if (entity.getCustomName().equals("BowSpleefTNT")) {
				if (eventName == GameName.BOWSPLEEF
						&& (eventState == GameState.PLAYING || eventState == GameState.FINISH)) {
					event.setCancelled(true);
					spawnFireWork(entity);
				} else {
					event.setCancelled(true);
				}
			}
		}
	}

	private void spawnFireWork(Entity entity) {
		Firework fw = entity.getLocation().getWorld().spawn(entity.getLocation().clone(), Firework.class);
		FireworkMeta fwm = fw.getFireworkMeta();

		Random r = new Random();

		int rt = r.nextInt(3) + 1;
		Type type = null;
		if (rt == 1)
			type = Type.BALL;
		if (rt == 2)
			type = Type.BALL_LARGE;
		if (rt == 3)
			type = Type.BURST;
		if (rt == 4)
			type = Type.STAR;

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.RED)
				.withFade(Color.BLACK).with(type).trail(false).build();

		fwm.clearEffects();
		fwm.addEffect(effect);

		// Permet l'explosion instantanée:
		Field f;
		try {
			f = fwm.getClass().getDeclaredField("power");
			f.setAccessible(true);
			f.set(fwm, -1);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		fw.setFireworkMeta(fwm);
		fw.setTicksLived(15);
	}
}