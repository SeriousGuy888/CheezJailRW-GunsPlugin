package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.interfaces.IScopedGun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class Dmr extends AbstractGun implements IScopedGun {
  public Dmr(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_MEDIUM,
        30,
        30,
        333L,
        200,
        6);
  }

  @Override
  public ItemStack getBaseItem() {
    return createItemStack(
        Material.IRON_HOE,
        "&rDMR",
        6600901);
  }

  @Override
  public void setSounds() {
    shootSound = "dmr";
    reloadSound = "generic";
  }
}
