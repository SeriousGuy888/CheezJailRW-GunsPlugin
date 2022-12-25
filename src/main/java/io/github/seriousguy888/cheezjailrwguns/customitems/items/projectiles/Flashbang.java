package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class Flashbang extends AbstractProjectile {
  private final PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS,
      5 * 20,
      0,
      false,
      false,
      true);

  public Flashbang(String customItemId) {
    super(customItemId);
  }

  @Override
  protected int getCooldownTicks() {
    return 20;
  }

  @Override
  public ItemStack getBaseItem() {
    return createItemStack(
        Material.OAK_SIGN,
        "&rFlashbang",
        6600110);
  }

  @Override
  public Projectile launch(Player player) {
    Snowball snowball = player.launchProjectile(Snowball.class);
    snowball.setItem(this.getBaseItem());

    return snowball;
  }

  @Override
  public void hit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    Location loc = projectile.getLocation();
    World world = projectile.getWorld();
    float radius = 5f;

    world.spawnParticle(Particle.FLASH, loc, 1);

    List<LivingEntity> nearbyEntities = projectile
        .getNearbyEntities(radius, radius, radius)
        .stream()
        .filter(e -> {
          if (!(e instanceof LivingEntity))
            return false;

          Vector dirVec = e.getLocation().subtract(loc).toVector().normalize();

          var trace = world.rayTraceBlocks(
              loc.subtract(projectile.getVelocity()), // subtract velocity to account for proj being inside block
              dirVec,
              radius,
              FluidCollisionMode.NEVER,
              true);
          return (trace == null);
        })
        .map(e -> (LivingEntity) e)
        .toList();

    nearbyEntities.forEach(e -> {
      blindness.apply(e);
      e.damage(1, (Entity) projectile.getShooter());

      if (e instanceof Player player) {
        player.sendTitle("", "FLASHBANG", 5, 20, 5);
      }
    });
  }
}
