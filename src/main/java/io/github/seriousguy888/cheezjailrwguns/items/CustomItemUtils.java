package io.github.seriousguy888.cheezjailrwguns.items;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.items.items.BaseCustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomItemUtils {
  public static NamespacedKey customItemKey = getNamespacedKey("custom_item");

  /**
   * @param item input item
   * @return whether the input item is a custom item managed by the plugin
   */
  public static boolean isCustomItem(ItemStack item) {
    return Arrays
        .stream(CustomItemManager.items.toArray())
        .anyMatch(customItem -> ((BaseCustomItem) customItem).is(item));
  }

  /**
   * @param item input item
   * @return the custom item that this item is.
   */
  public static BaseCustomItem getCustomItem(ItemStack item) {
    var matches = Arrays
        .stream(CustomItemManager.items.toArray())
        .filter(customItem -> ((BaseCustomItem) customItem).is(item));
    var match = matches.findFirst();

    return (BaseCustomItem) match.orElse(null);
  }

  public static BaseCustomItem getCustomItem(String itemId) {
    var results = CustomItemManager.items.stream().filter(e -> e.customItemId.equals(itemId));
    return results.findFirst().orElse(null);
  }

  public static boolean hasCustomItem(Inventory inventory, ItemStack itemStack) {
    if (!isCustomItem(itemStack))
      return false;
    BaseCustomItem custItem = getCustomItem(itemStack);
    return hasCustomItem(inventory, custItem);
  }

  public static boolean hasCustomItem(Inventory inventory, BaseCustomItem customItem) {
    return Arrays.stream(inventory.getStorageContents()).anyMatch(customItem::is);
  }

  public static ItemStack createItemStack(Material material, String name, Integer customModelData) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return null;

    meta.setCustomModelData(customModelData);
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    item.setItemMeta(meta);

    return item;
  }

  public static NamespacedKey getNamespacedKey(String key) {
    return new NamespacedKey(CheezJailRWGuns.getPlugin(), key);
  }

  public static <T> void setProperty(ItemStack itemStack, CustomItemProperty property, T value) {
    if (itemStack == null)
      return;
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null)
      return;

    meta.getPersistentDataContainer()
        .set(property.namespacedKey, property.dataType, value);
    itemStack.setItemMeta(meta);
  }

  public static <T> T getProperty(ItemStack itemStack, CustomItemProperty property) {
    if (itemStack == null)
      return null;
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null)
      return null;

    var output = meta.getPersistentDataContainer()
        .get(property.namespacedKey, property.dataType);

    return (T) output;
  }
}
