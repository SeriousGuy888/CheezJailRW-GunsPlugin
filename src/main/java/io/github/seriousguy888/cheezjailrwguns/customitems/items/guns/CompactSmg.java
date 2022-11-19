package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.inventory.ItemStack;

public class CompactSmg extends AbstractGun {
  public CompactSmg(ItemStack item, String customItemId) {
    super(item,
        customItemId,
        CustomItemManager.AMMO_MEDIUM,
        30,
        15,
        90L, // ~1000/11
        60,
        5);
  }

  @Override
  public void setSounds() {
    shootSound = "compact_smg";
    reloadSound = "generic";
  }
}
