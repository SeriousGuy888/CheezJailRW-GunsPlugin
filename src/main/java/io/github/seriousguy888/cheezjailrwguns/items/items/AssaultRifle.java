package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemManager;
import org.bukkit.inventory.ItemStack;

public class AssaultRifle extends AbstractGun {
  public AssaultRifle(ItemStack item, String customItemId, int maxAmmo) {
    super(item,
        customItemId,
        maxAmmo,
        CustomItemManager.AMMO_SMALL,
        100L,
        20); // 2 ticks
  }
}