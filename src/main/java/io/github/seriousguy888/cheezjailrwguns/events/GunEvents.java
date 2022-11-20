package io.github.seriousguy888.cheezjailrwguns.events;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.AbstractGun;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class GunEvents implements Listener {
  private final CheezJailRWGuns plugin;
  private final HashMap<Player, BukkitRunnable> reloadTasks;
  private final HashMap<Player, Long> lastFiredGun;

  public GunEvents() {
    plugin = CheezJailRWGuns.getPlugin();
    reloadTasks = new HashMap<>();
    lastFiredGun = new HashMap<>();
  }


  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Action action = event.getAction();
    ItemStack heldItem = player.getInventory().getItemInMainHand();

    AbstractCustomItem heldCustomItem = CustomItemUtils.getCustomItem(heldItem);
    if (heldCustomItem == null) // If this is not a plugin managed custom item
      return;
    if (!(heldCustomItem instanceof AbstractGun heldGunType)) // If this is not a gun
      return;

    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
      event.setCancelled(true);

      if (reloadTasks.containsKey(player))
        return;
      if (!gunNotInCooldown(player, heldGunType))
        return;
      if (!gunHasAmmo(player, heldItem, heldGunType))
        return;

      player.getWorld().playSound(
          player.getLocation(),
          heldGunType.getSoundString(AbstractGun.GunSoundType.SHOOT),
          SoundCategory.PLAYERS,
          1,
          1
      );

      lastFiredGun.put(player, System.currentTimeMillis());

      float range = heldGunType.getRange();
      Location playerLoc = player.getEyeLocation();
      Vector playerDir = playerLoc.getDirection().normalize();
      var bulletCollisions = heldGunType.doShotRaycasts(player, playerLoc, playerDir);

      for (Vector rayDir : bulletCollisions.keySet()) {
        var ray = bulletCollisions.get(rayDir);
        drawBulletParticleLine(player, ray, playerLoc, rayDir, range);
        damageHitEntity(player, ray, heldGunType);
      }
    } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
      event.setCancelled(true);
      tryReloadGun(player, heldItem, heldGunType);
    }
  }


  private boolean gunNotInCooldown(Player player,
                                   AbstractGun heldGunType) {
    long playerLastFired = lastFiredGun.getOrDefault(player, 0L);
    long diff = System.currentTimeMillis() - playerLastFired;
    return diff > heldGunType.getCooldownMs();
  }

  private boolean gunHasAmmo(Player player,
                             ItemStack heldItem,
                             AbstractGun heldGunType) {
    int ammo = heldGunType.getAmmo(heldItem);
    if (ammo <= 0) {
      player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5f, 1);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
          TextComponent.fromLegacyText(ChatColor.RED + "Weapon has no ammo!"));
      return false;
    }

    heldGunType.setAmmo(heldItem, ammo - 1);
    heldGunType.updateStatsDisplay(heldItem);
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
        TextComponent.fromLegacyText("Ammo: " + (ammo - 1) + "/" + heldGunType.getMaxAmmo()));
    return true;
  }

  private void damageHitEntity(Player player,
                               RayTraceResult traceResult,
                               AbstractGun heldGunType) {
    if (traceResult == null)
      return;
    if (!(traceResult.getHitEntity() instanceof LivingEntity hitEntity))
      return;

    float dmg = heldGunType.getDamage();

    boolean isHeadshot = detectHeadshot(hitEntity, traceResult);
    if (isHeadshot) {
      dmg *= 1.25;
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
          TextComponent.fromLegacyText("Headshot"));
    }

    hitEntity.damage(dmg, player);
    hitEntity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.PROJECTILE, dmg));
    hitEntity.setNoDamageTicks(0);
  }

  private boolean detectHeadshot(LivingEntity hitEntity,
                                 RayTraceResult traceResult) {
    Location eyeLoc = hitEntity.getEyeLocation();
    double eyeHeight = hitEntity.getLocation().getY() + hitEntity.getEyeHeight() + 0.175;
    Location hitLoc = traceResult.getHitPosition().toLocation(hitEntity.getWorld());

    double locDist = eyeLoc.distance(hitLoc);
    double eyeHeightDist = Math.abs(hitLoc.getY() - eyeHeight);

    return (locDist < 1.25 && eyeHeightDist < 0.25);
  }

  private void drawBulletParticleLine(Player player,
                                      RayTraceResult rayResult,
                                      Location playerLoc,
                                      Vector rayDir,
                                      float range) {
    // Use hit location if the raycast hit something, otherwise draw the line to the
    // maximum range that it could have hit something.
    Location particleLineEnd = (
        rayResult == null ?
            rayDir.multiply(range).add(playerLoc.toVector()) :
            rayResult.getHitPosition()
    ).toLocation(player.getWorld());

    Location particleLineStart = playerLoc.clone();
    Vector particleLineDir = particleLineEnd.toVector().subtract(playerLoc.toVector()).normalize();
    double particleLineStep = 0.5;
    double distBetween = particleLineEnd.distance(playerLoc);

    // Draw a line of particles to illustrate where the bullet travelled
    for (int i = 1; i < distBetween / particleLineStep; i++) {
      particleLineDir.multiply(i * particleLineStep);
      particleLineStart.add(particleLineDir);
      player.getWorld().spawnParticle(
          Particle.CRIT, particleLineStart, 1, 0, 0, 0, 0);
      particleLineStart.subtract(particleLineDir);
      particleLineDir.normalize();
    }

    player.getWorld().spawnParticle(
        Particle.SMOKE_NORMAL, playerLoc, 5, 0.2, 0.2, 0.2, 0.1);
  }


  private void tryReloadGun(Player player,
                            ItemStack heldItem,
                            AbstractGun heldGunType) {
    // No double reloading
    if (reloadTasks.containsKey(player))
      return;

    // Don't run reloading code if gun is already at full ammo
    if (heldGunType.getAmmo(heldItem) >= heldGunType.getMaxAmmo())
      return;

    ItemStack ammoStack = heldGunType.getCorrectAmmoStack(heldItem, player.getInventory());
    if (ammoStack == null) {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
          TextComponent.fromLegacyText(ChatColor.RED + "No compatible ammo!"));
    }

    player.getWorld().playSound(
        player.getLocation(),
        heldGunType.getSoundString(AbstractGun.GunSoundType.RELOAD),
        1,
        1
    );


    int ticksPerBullet = heldGunType.getReloadTicks() / heldGunType.getMaxAmmo();

    BukkitRunnable reloadTask = new BukkitRunnable() {
      @Override
      public void run() {
        ItemStack currHeldItem = player.getInventory().getItemInMainHand();

        int ammo = heldGunType.getAmmo(heldItem);
        int maxAmmo = heldGunType.getMaxAmmo();
        ItemStack ammoStack = heldGunType.getCorrectAmmoStack(currHeldItem, player.getInventory());
        if (ammo + 1 >= maxAmmo || ammoStack == null) {
          cancel();
          player.setCooldown(heldItem.getType(), 0);
          reloadTasks.remove(player);

          if (ammo >= maxAmmo || ammoStack == null)
            return;
        }

        ammoStack.setAmount(ammoStack.getAmount() - 1);
        heldGunType.reloadGun(currHeldItem, 1);

        if (!this.isCancelled())
          player.setCooldown(heldItem.getType(), ticksPerBullet);
      }
    };

    reloadTasks.put(player, reloadTask);
    player.setCooldown(heldItem.getType(), ticksPerBullet);
    reloadTask.runTaskTimer(plugin, ticksPerBullet, ticksPerBullet);
  }

  @EventHandler
  public void onItemSwitch(PlayerItemHeldEvent event) {
    Player player = event.getPlayer();

    if (reloadTasks.containsKey(player)) {
      reloadTasks.get(player).cancel();
      reloadTasks.remove(player);

      ItemStack prevHeldItem = player.getInventory().getItem(event.getPreviousSlot());
      if (prevHeldItem != null)
        player.setCooldown(prevHeldItem.getType(), 0);
    }
  }
}

