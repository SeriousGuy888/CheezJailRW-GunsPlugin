package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.inventory.ItemStack;

public class Smg extends AbstractGun {
  public Smg(ItemStack item, String customItemId) {
    super(item,
        customItemId,
        CustomItemManager.AMMO_MEDIUM,
        30,
        15,
        140L, // ~1000/7
        60,
        2);
  }

  @Override
  public void setSounds() {
    shootSound = "smg";
    reloadSound = "generic";
  }
}
