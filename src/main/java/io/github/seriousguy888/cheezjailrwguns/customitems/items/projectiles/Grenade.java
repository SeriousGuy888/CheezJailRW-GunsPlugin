package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class Grenade extends AbstractProjectile {
  public Grenade(String customItemId) {
    super(customItemId);
  }

  @Override
  protected ItemStack getBaseItem() {
    return createItemStack(Material.SPRUCE_SIGN, "&rGrenade", 6601101);
  }

  @Override
  protected int getCooldownTicks() {
    return 30;
  }

  @Override
  public Projectile launch(Player player) {
    Snowball snowball = player.launchProjectile(Snowball.class);
    ItemStack snowballTexture = createItemStack(Material.BIRCH_SIGN, "a", 6601102);
    if (snowballTexture != null)
      snowball.setItem(snowballTexture);

    return snowball;
  }

  @Override
  public void hit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    Location loc = projectile.getLocation();
    World world = projectile.getWorld();

    world.createExplosion(loc, 2f, false, false, (Entity) projectile.getShooter());
  }
}

