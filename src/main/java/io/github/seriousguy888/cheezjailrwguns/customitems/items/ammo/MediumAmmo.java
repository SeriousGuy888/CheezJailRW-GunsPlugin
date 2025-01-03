package io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class MediumAmmo extends AbstractAmmo {
  public MediumAmmo(String customItemId) {
    super(customItemId);
  }

  @Override
  public ItemStack getBaseItem() {
    return createItemStack(
        Material.IRON_NUGGET,
        "&rMedium Ammo",
        6600302);
  }
}
