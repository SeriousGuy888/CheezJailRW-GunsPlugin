package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class Molotov extends AbstractProjectile {
  public Molotov(String customItemId) {
    super(customItemId);
  }

  @Override
  protected ItemStack getBaseItem() {
    return createItemStack(Material.BIRCH_SIGN, "&rMolotov Cocktail", 6601001);
  }

  @Override
  protected int getCooldownTicks() {
    return 10;
  }

  @Override
  public Projectile launch(Player player) {
    Snowball snowball = player.launchProjectile(Snowball.class);
    ItemStack snowballTexture = createItemStack(Material.BIRCH_SIGN, "a", 6601002);
    if (snowballTexture != null)
      snowball.setItem(snowballTexture);

    return snowball;
  }

  @Override
  public void hit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    Location loc = projectile.getLocation();
    World world = projectile.getWorld();
    float radius = 3f;

    world.spawnParticle(Particle.FLAME, loc, 50, 1, 1, 1);


    List<LivingEntity> nearbyEntities = projectile
        .getNearbyEntities(radius, radius, radius)
        .stream()
        .filter(e -> e instanceof LivingEntity)
        .map(e -> (LivingEntity) e).toList();


    nearbyEntities.forEach(e -> {
      e.setFireTicks(5 * 20);
      e.damage(0.5, (Entity) projectile.getShooter());
    });

    world.playSound(loc, Sound.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1, 1);
  }
}

