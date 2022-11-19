package io.github.seriousguy888.cheezjailrwguns.events;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemManager;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.items.PersistentDataUtil;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class GunEvents implements Listener {
  private final ArrayList<Player> reloadingPlayers;
  CheezJailRWGuns plugin;

  public GunEvents() {
    plugin = CheezJailRWGuns.getPlugin();
    reloadingPlayers = new ArrayList<>();
  }


  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Action action = event.getAction();
    ItemStack heldItem = player.getInventory().getItemInMainHand();


    // No shooting while reloading
    if (reloadingPlayers.contains(player))
      return;

    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
      if (CustomItemManager.PISTOL.is(heldItem)) {
        event.setCancelled(true);

        int ammo = PersistentDataUtil.getInt(heldItem, CustomItemProperty.GUN_AMMO);
        if (ammo <= 0) {
          player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5f, 1);
          player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
              TextComponent.fromLegacyText(ChatColor.RED + "Weapon has no ammo!"));
          return;
        }

        PersistentDataUtil.setInt(heldItem, CustomItemProperty.GUN_AMMO, ammo - 1);
        CustomItemManager.PISTOL.updateAmmoDisplay(heldItem);


        player.getWorld().playSound(
            player.getLocation(),
            "cheezjail.guns.pistol.fire",
            SoundCategory.PLAYERS,
            1,
            1
        );

        Location playerLoc = player.getEyeLocation();
        Vector playerDir = playerLoc.getDirection().normalize();
        double maxRange = 20;
        RayTraceResult result = player.getWorld().rayTrace(
            playerLoc,
            playerDir,
            maxRange,
            FluidCollisionMode.NEVER,
            true,
            0.2,
            entity -> !entity.equals(player)
        );


        // Use hit location if the raycast hit something, otherwise draw the line to the
        // maximum range that it could have hit something.
        Location particleLineEnd = (
            result == null ?
                playerDir.clone().multiply(maxRange).add(playerLoc.toVector()) :
                result.getHitPosition()
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


        if (result == null)
          return;
        if (!(result.getHitEntity() instanceof LivingEntity hitEntity))
          return;

        int dmg = 6;
        hitEntity.damage(dmg, player);
        hitEntity.setLastDamageCause(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.PROJECTILE, dmg));
      }
    } else if (event.getAction().equals(Action.LEFT_CLICK_AIR) ||
        event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
      if (CustomItemManager.PISTOL.is(heldItem)) {
        reloadingPlayers.add(player);
        player.getWorld().playSound(
            player.getLocation(),
            "cheezjail.guns.pistol.reload",
            1,
            1
        );

        new BukkitRunnable() {
          @Override
          public void run() {
            ItemStack currHeldItem = player.getInventory().getItemInMainHand();
            if (!heldItem.equals(currHeldItem)) {
              cancel();
              reloadingPlayers.remove(player);
              return;
            }

            int ammo = CustomItemManager.PISTOL.getAmmo(heldItem);
            int maxAmmo = CustomItemManager.PISTOL.getMaxAmmo(heldItem);
            if (ammo >= maxAmmo) {
              cancel();
              reloadingPlayers.remove(player);
              return;
            }

            ItemStack ammoStack = CustomItemManager.PISTOL.getCorrectAmmoStack(currHeldItem, player.getInventory());
            if (ammoStack == null) {
              cancel();
              reloadingPlayers.remove(player);
            } else {
              ammoStack.setAmount(ammoStack.getAmount() - 1);
              CustomItemManager.PISTOL.reloadGun(currHeldItem, 1);

            }

          }
        }.runTaskTimer(plugin, 0, 5);
      }
    }
  }
}

