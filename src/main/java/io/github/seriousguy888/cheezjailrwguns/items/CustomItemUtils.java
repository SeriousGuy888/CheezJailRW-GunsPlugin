package io.github.seriousguy888.cheezjailrwguns.items;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class CustomItemUtils {
  public static NamespacedKey customItemKey = getNamespacedKey("custom_item");

  /**
   * @param item input item
   * @return whether the input item is a custom item managed by the plugin
   */
  public static boolean isCustomItem(ItemStack item) {
    return Arrays.stream(CustomItem.values()).anyMatch(customItem -> customItem.is(item));
  }

  /**
   * @param item input item
   * @return the custom item that this item is.
   */
  public static CustomItem getCustomItem(ItemStack item) {
    var matches = Arrays.stream(CustomItem.values()).filter(customItem -> customItem.is(item));
    var match = matches.findFirst();

    return match.orElse(null);
  }

  public static boolean hasCustomItem(Inventory inventory, ItemStack itemStack) {
    if (!isCustomItem(itemStack))
      return false;
    CustomItem custItem = getCustomItem(itemStack);
    return hasCustomItem(inventory, custItem);
  }

  public static boolean hasCustomItem(Inventory inventory, CustomItem customItem) {
    return Arrays.stream(inventory.getStorageContents()).anyMatch(customItem::is);
  }

  public static NamespacedKey getNamespacedKey(String key) {
    return new NamespacedKey(CheezJailRWGuns.getPlugin(), key);
  }

  public static <T, Z> void setProperty(ItemStack itemStack,
                                        CustomItemProperty property,
                                        PersistentDataType<T, Z> dataType,
                                        Z value) {
    if (itemStack == null)
      return;
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null)
      return;

    meta.getPersistentDataContainer()
        .set(property.namespacedKey, dataType, value);
    itemStack.setItemMeta(meta);
  }
}
