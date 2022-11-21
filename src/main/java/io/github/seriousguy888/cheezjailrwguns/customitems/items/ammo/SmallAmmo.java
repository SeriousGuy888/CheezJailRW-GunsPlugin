package io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class SmallAmmo extends AbstractAmmo {
  public SmallAmmo(String customItemId) {
    super(customItemId);
  }

  @Override
  public ItemStack getBaseItem() {
    return createItemStack(
        Material.IRON_NUGGET,
        "&rSmall Ammo",
        6600301);
  }
}
