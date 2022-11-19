package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemManager;
import org.bukkit.inventory.ItemStack;

public class AssaultRifle extends AbstractGun {
  public AssaultRifle(ItemStack item, String customItemId) {
    super(item,
        customItemId,
        CustomItemManager.AMMO_SMALL,
        30,
        100L,
        40,
        4);
  }
}