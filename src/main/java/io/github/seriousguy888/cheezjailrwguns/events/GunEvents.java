package io.github.seriousguy888.cheezjailrwguns.events;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItem;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class GunEvents implements Listener {
  CheezJailRWGuns plugin;

  public GunEvents() {
    plugin = CheezJailRWGuns.getPlugin();
  }


  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Action action = event.getAction();
    ItemStack heldItem = player.getInventory().getItemInMainHand();

    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
      if (CustomItem.PISTOL.is(heldItem)) {

        player.getWorld().playSound(
            player.getLocation(),
            "entity.zombie.attack_iron_door",
            SoundCategory.PLAYERS,
            1,
            0.5f
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
    } else if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
      if (CustomItem.PISTOL.is(heldItem)) {
        if (CustomItemUtils.hasCustomItem(player.getInventory(), CustomItem.AMMO_SMALL)) {
          player.sendMessage("you have more ammo :D");
        } else {
          player.sendMessage("you have no more ammo D:");
        }
      }
    }
  }
}
