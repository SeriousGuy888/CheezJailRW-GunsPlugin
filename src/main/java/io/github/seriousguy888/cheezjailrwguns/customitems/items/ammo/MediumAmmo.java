package io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo;

import org.bukkit.Material;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class MediumAmmo extends AbstractAmmo {
  public MediumAmmo(String customItemId) {
    super(customItemId);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.IRON_NUGGET,
        "&rMedium Ammo",
        6600302);
  }
}
