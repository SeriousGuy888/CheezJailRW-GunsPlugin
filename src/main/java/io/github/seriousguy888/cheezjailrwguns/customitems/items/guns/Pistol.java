package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.inventory.ItemStack;

public class Pistol extends AbstractGun {
  public Pistol(ItemStack item, String customItemId) {
    super(item,
        customItemId,
        CustomItemManager.AMMO_SMALL,
        12,
        25,
        750L,
        20,
        6);
  }

  @Override
  public void setSounds() {
    shootSound = "pistol";
    reloadSound = "pistol";
  }
}
