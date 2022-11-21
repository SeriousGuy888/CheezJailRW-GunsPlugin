package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class CompactSmg extends AbstractGun {
  public CompactSmg(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_SMALL,
        30,
        15,
        80L, // ~1000/12
        60,
        5);
  }

  @Override
  protected ItemStack getItem() {
    return createItemStack(
        Material.IRON_HOE,
        "&rCompact SMG",
        6600601);
  }

  @Override
  public void setSounds() {
    shootSound = "compact_smg";
    reloadSound = "generic";
  }
}
