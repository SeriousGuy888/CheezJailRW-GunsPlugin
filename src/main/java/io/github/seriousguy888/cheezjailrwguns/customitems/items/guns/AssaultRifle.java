package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class AssaultRifle extends AbstractGun {
  public AssaultRifle(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_MEDIUM,
        30,
        25,
        100L,
        40,
        4);
  }

  @Override
  public ItemStack getBaseItem() {
    return createItemStack(
        Material.IRON_HOE,
        "&rAssault Rifle",
        6600401);
  }

  @Override
  public void setSounds() {
    shootSound = "assault_rifle";
    reloadSound = "assault_rifle";
  }
}