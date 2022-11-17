package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class BaseCustomItem {
  public ItemStack item;
  public String customItemId;

  public BaseCustomItem(ItemStack item, String customItemId) {
    this.item = item;
    this.customItemId = customItemId;

    CustomItemUtils.setProperty(item,
        CustomItemProperty.CUSTOM_ITEM_ID,
        customItemId);
  }

  public boolean is(ItemStack compareItem) {
    if (compareItem == null)
      return false;

    ItemMeta meta = compareItem.getItemMeta();
    if (meta == null)
      return false;

    String compareCustomItemId = meta.getPersistentDataContainer()
        .get(CustomItemUtils.customItemKey, PersistentDataType.STRING);

    if (compareCustomItemId == null)
      return false;
    return compareCustomItemId.equals(customItemId);
  }
}
