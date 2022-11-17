package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils;
import org.bukkit.inventory.ItemStack;

public class Pistol extends BaseCustomItem {
  public Pistol(ItemStack item, String customItemId, int maxAmmo) {
    super(item, customItemId);

    CustomItemUtils.setProperty(item, CustomItemProperty.GUN_MAX_AMMO, maxAmmo);
    CustomItemUtils.setProperty(item, CustomItemProperty.GUN_AMMO, 0);
  }
}
