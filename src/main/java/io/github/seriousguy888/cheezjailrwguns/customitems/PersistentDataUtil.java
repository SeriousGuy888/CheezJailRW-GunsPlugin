package io.github.seriousguy888.cheezjailrwguns.customitems;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataUtil {
  private static ItemMeta getMeta(ItemStack itemStack) {
    if (itemStack == null)
      return null;
    return itemStack.getItemMeta();
  }

  public static void setString(ItemStack itemStack, CustomItemProperty property, String value) {
    ItemMeta meta = getMeta(itemStack);
    if (meta == null)
      return;

    meta.getPersistentDataContainer()
        .set(property.namespacedKey, PersistentDataType.STRING, value);
    itemStack.setItemMeta(meta);
  }

  public static String getString(ItemStack itemStack, CustomItemProperty property) {
    ItemMeta meta = getMeta(itemStack);
    if (meta == null)
      return null;

    return meta
        .getPersistentDataContainer()
        .get(property.namespacedKey, PersistentDataType.STRING);
  }

  public static void setInt(ItemStack itemStack, CustomItemProperty property, Integer number) {
    setString(itemStack, property, number.toString());
  }

  public static int getInt(ItemStack itemStack, CustomItemProperty property) {
    String str = getString(itemStack, property);
    if (str == null)
      return 0;
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
