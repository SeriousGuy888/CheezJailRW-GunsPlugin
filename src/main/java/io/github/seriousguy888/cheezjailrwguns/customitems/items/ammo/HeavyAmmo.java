package io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class HeavyAmmo extends AbstractAmmo {
  public HeavyAmmo(String customItemId) {
    super(customItemId);
  }

  @Override
  protected ItemStack getItem() {
    return createItemStack(
        Material.IRON_NUGGET,
        "&rHeavy Ammo",
        6600303);
  }
}
