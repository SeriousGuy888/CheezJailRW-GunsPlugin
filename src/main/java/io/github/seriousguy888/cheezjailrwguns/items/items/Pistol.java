package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemManager;
import org.bukkit.inventory.ItemStack;

public class Pistol extends AbstractGun {
  public Pistol(ItemStack item, String customItemId) {
    super(item,
        customItemId,
        CustomItemManager.AMMO_SMALL,
        12,
        750L,
        20,
        6);
  }
}
