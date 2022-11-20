package io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo;

import org.bukkit.Material;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class ShotgunAmmo extends AbstractAmmo {
  public ShotgunAmmo(String customItemId) {
    super(customItemId);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.IRON_NUGGET,
        "&rShotgun Ammo",
        6600304);
  }
}
