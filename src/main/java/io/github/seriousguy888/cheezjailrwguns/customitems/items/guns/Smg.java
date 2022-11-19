package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils.createItemStack;

public class Smg extends AbstractGun {
  public Smg(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_MEDIUM,
        30,
        15,
        140L, // ~1000/7
        60,
        2);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.IRON_HOE,
        "&rSMG",
        6600501);
  }

  @Override
  public void setSounds() {
    shootSound = "smg";
    reloadSound = "generic";
  }
}
