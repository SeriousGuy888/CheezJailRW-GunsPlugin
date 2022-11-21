package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import org.bukkit.Material;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil.createItemStack;

public class Flashbang extends AbstractProjectile {
  public Flashbang(String customItemId) {
    super(customItemId);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.EGG,
        "&rFlashbang",
        6600110);
  }
}
