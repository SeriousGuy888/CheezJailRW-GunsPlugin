package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

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
    float radius = 5f;

    world.spawnParticle(Particle.EXPLOSION_HUGE, loc, 10);
  }
}

