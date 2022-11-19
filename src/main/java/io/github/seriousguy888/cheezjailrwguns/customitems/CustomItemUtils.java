package io.github.seriousguy888.cheezjailrwguns.customitems;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomItemUtils {
  /**
   * @param item input item
   * @return whether the input item is a custom item managed by the plugin
   */
  public static boolean isCustomItem(ItemStack item) {
    return Arrays
        .stream(CustomItemManager.items.toArray())
        .anyMatch(customItem -> ((AbstractCustomItem) customItem).is(item));
  }

  /**
   * @param item input item
   * @return the custom item that this item is.
   */
  public static AbstractCustomItem getCustomItem(ItemStack item) {
    var matches = Arrays
        .stream(CustomItemManager.items.toArray())
        .filter(customItem -> ((AbstractCustomItem) customItem).is(item));
    var match = matches.findFirst();

    return (AbstractCustomItem) match.orElse(null);
  }

  public static AbstractCustomItem getCustomItem(String itemId) {
    var results = CustomItemManager.items.stream().filter(e -> e.customItemId.equals(itemId));
    return results.findFirst().orElse(null);
  }

//  public static boolean hasCustomItem(Inventory inventory, ItemStack itemStack) {
//    if (!isCustomItem(itemStack))
//      return false;
//    BaseCustomItem custItem = getCustomItem(itemStack);
//    return hasCustomItem(inventory, custItem);
//  }

  public static boolean hasCustomItem(Inventory inventory, AbstractCustomItem customItem) {
    return Arrays.stream(inventory.getStorageContents()).anyMatch(customItem::is);
  }

  public static ItemStack getCustomItemStack(Inventory inventory, AbstractCustomItem customItem) {
    return Arrays.stream(inventory.getStorageContents()).filter(customItem::is).findFirst().orElse(null);
  }

  public static ItemStack createItemStack(Material material, String name, Integer customModelData) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return null;

    meta.setCustomModelData(customModelData);
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    item.setItemMeta(meta);

//    ListMultimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
//    attributeMap.get(Attribute.GENERIC_ATTACK_DAMAGE).set(0);
//    meta.setAttributeModifiers(attributeMap);

    return item;
  }

  public static NamespacedKey getNamespacedKey(String key) {
    return new NamespacedKey(CheezJailRWGuns.getPlugin(), key);
  }
}
