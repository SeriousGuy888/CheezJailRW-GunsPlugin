package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class Pistol extends AbstractGun {
  public Pistol(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_SMALL,
        12,
        25,
        750L,
        20,
        6);
  }

  @Override
  public ItemStack getBaseItem() {
    return createItemStack(
        Material.CARROT_ON_A_STICK,
        "&rPistol",
        6600201);
  }

  @Override
  public void setSounds() {
    shootSound = "pistol";
    reloadSound = "pistol";
  }
}
