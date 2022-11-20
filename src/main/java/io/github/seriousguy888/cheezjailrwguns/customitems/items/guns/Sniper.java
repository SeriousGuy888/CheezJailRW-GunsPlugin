package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.interfaces.IScopedGun;
import org.bukkit.Material;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils.createItemStack;

public class Sniper extends AbstractGun implements IScopedGun {
  public Sniper(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_HEAVY,
        3,
        100,
        1000L,
        200,
        15);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.GOLDEN_HOE,
        "&rSniper Rifle",
        6600701);
  }

  @Override
  public void setSounds() {
    shootSound = "shoot";
    reloadSound = "generic";
  }
}
