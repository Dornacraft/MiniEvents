package fr.dornacraft.minievents.events.bowspleef.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fr.dornacraft.minievents.GameName;
import fr.dornacraft.minievents.GameState;
import fr.dornacraft.minievents.Main;
import fr.dornacraft.minievents.events.GetEventWorld;

public class BowSpleefArrowHitListener implements Listener {

	private Main main;

	public BowSpleefArrowHitListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onHit(ProjectileHitEvent event) {
		Block blockHit = event.getHitBlock();
		Entity entityHit = event.getHitEntity();
		Projectile projectile = event.getEntity();
		GameName eventName = main.getGameName();
		GameState eventState = main.getGameState();

		if (eventName == GameName.BOWSPLEEF) {
			if (eventState == GameState.PLAYING) {
				if (projectile.getType() == EntityType.ARROW) {
					if (blockHit != null && blockHit.getLocation().getWorld() == GetEventWorld.getWorld(main)) {
						if (blockHit.getType() == Material.TNT) {
							main.getBlockLoc().add(blockHit.getLocation());
							event.getHitBlock().setType(Material.AIR);
							event.getEntity().remove();

							double posX = blockHit.getLocation().getX() + 0.5;
							double posY = blockHit.getLocation().getY();
							double posZ = blockHit.getLocation().getZ() + 0.5;
							Location spawnLoc = new Location(blockHit.getWorld(), posX, posY, posZ, 0f, 0f);

							TNTPrimed tnt = spawnLoc.getWorld().spawn(spawnLoc, TNTPrimed.class);
							tnt.setFuseTicks(20);
							tnt.setCustomName("BowSpleefTNT");
							tnt.setCustomNameVisible(false);
							tnt.setVelocity(new Vector(0, 1, 0));
							
							for (UUID playerUUID : main.getParticipants()) {
								Player player = Bukkit.getPlayer(playerUUID);
								int blockPosX = (int) posX;
								int blockPosY = (int) posY;
								int blockPosZ = (int) posZ;
								
								Location playerLocation = player.getLocation();
								if (playerLocation.getBlockX() == blockPosX) {
									if (playerLocation.getBlockY() >= blockPosY && playerLocation.getBlockY() < blockPosY+2) {
										if (playerLocation.getBlockZ() == blockPosZ) {
											player.setVelocity(new  Vector(player.getVelocity().getX(), tnt.getVelocity().getY(), player.getVelocity().getZ()));
											player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10*20, 1));
										}
									}
								}
							}
							return;
						} else {
							event.getEntity().remove();
							return;
						}
					}
					else if (entityHit != null && entityHit.getLocation().getWorld() == GetEventWorld.getWorld(main)) {
						if (entityHit.getType() == EntityType.PLAYER) {
							Player player = (Player) entityHit;
							if (main.getParticipants().contains(player.getUniqueId())) {
								if (player.hasPotionEffect(PotionEffectType.LEVITATION)) {
									Vector playerVelocity = new Vector(projectile.getVelocity().getX()/3, 0, projectile.getVelocity().getZ()/3);
									player.setVelocity(playerVelocity);
									event.getEntity().remove();
									return;
								} 
								else if (player.hasPotionEffect(PotionEffectType.GLOWING) && !player.hasPotionEffect(PotionEffectType.LEVITATION)) {
									Vector playerVelocity = new Vector(projectile.getVelocity().getX()/3, 0, projectile.getVelocity().getZ()/3);
									player.setVelocity(playerVelocity);
									return;
								}
								else {
									player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5*20, 1));
									player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10*20, 1));
									Vector playerVelocity = new Vector(projectile.getVelocity().getX()/2, player.getVelocity().getY(), projectile.getVelocity().getZ()/2);
									player.setVelocity(playerVelocity);
									event.getEntity().remove();
									return;
								}
							}
						} else {
							return;
						}
					} else {
						return;
					}
				} else {
					return;
				}
			}
		}
	}
}
