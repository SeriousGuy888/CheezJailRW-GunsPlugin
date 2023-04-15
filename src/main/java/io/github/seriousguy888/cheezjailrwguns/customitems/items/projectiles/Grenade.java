package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.customentities.PrimedGrenade;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

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
    ItemStack snowballTexture = getBaseItem();
    if (snowballTexture != null) {
      snowball.setItem(snowballTexture);
    }
    return snowball;
  }

  @Override
  public void hit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    Location loc = projectile.getLocation();

    CheezJailRWGuns.getEntityManager().spawn(PrimedGrenade.class, loc, (Player) projectile.getShooter());
  }
}

