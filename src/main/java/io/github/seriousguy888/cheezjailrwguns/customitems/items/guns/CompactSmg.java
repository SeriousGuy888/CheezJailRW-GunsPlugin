package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.Material;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils.createItemStack;

public class CompactSmg extends AbstractGun {
  public CompactSmg(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_MEDIUM,
        30,
        15,
        90L, // ~1000/11
        60,
        5);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
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
