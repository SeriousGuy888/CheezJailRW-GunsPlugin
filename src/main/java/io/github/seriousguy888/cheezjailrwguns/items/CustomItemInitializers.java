package io.github.seriousguy888.cheezjailrwguns.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomItemInitializers {
  public static ItemStack initGun(ItemStack itemStack) {
    if (itemStack == null)
      return null;

    CustomItemUtils.setProperty(itemStack,
        CustomItemProperty.GUN_AMMO,
        PersistentDataType.INTEGER,
        0);
    CustomItemUtils.setProperty(itemStack,
        CustomItemProperty.GUN_MAX_AMMO,
        PersistentDataType.INTEGER,
        0);

    return itemStack;
  }
}
