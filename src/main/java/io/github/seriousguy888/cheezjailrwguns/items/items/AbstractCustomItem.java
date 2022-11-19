package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.items.PersistentDataUtil;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCustomItem {
  public ItemStack item;
  public String customItemId;

  public AbstractCustomItem(ItemStack item, String customItemId) {
    this.item = item;
    this.customItemId = customItemId;

    PersistentDataUtil.setString(item,
        CustomItemProperty.CUSTOM_ITEM_ID,
        customItemId);
  }

  public boolean is(ItemStack compareItem) {
    if (compareItem == null)
      return false;

    String compareCustomItemId = PersistentDataUtil.getString(compareItem, CustomItemProperty.CUSTOM_ITEM_ID);

    if (compareCustomItemId == null)
      return false;
    return compareCustomItemId.equals(customItemId);
  }
}
