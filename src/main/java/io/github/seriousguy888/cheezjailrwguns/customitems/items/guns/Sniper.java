package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.interfaces.IScopedGun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

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
  protected ItemStack getItem() {
    return createItemStack(
        Material.GOLDEN_HOE,
        "&rSniper Rifle",
        6600801);
  }

  @Override
  public void setSounds() {
    shootSound = "sniper";
    reloadSound = "generic";
  }
}
