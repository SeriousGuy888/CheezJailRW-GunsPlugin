package io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo;

import org.bukkit.Material;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils.createItemStack;

public class HeavyAmmo extends AbstractAmmo {
  public HeavyAmmo(String customItemId) {
    super(customItemId);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.IRON_NUGGET,
        "&rHeavy Ammo",
        6600303);
  }
}
