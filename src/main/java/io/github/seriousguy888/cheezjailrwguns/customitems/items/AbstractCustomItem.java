package io.github.seriousguy888.cheezjailrwguns.customitems.items;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.customitems.PersistentDataUtil;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCustomItem {
  public ItemStack item;
  public String customItemId;

  public AbstractCustomItem(String customItemId) {
    this.customItemId = customItemId;

    item = getItem();
    PersistentDataUtil.setString(item,
        CustomItemProperty.CUSTOM_ITEM_ID,
        customItemId);
  }

  protected abstract ItemStack getItem();

  public boolean is(ItemStack compareItem) {
    if (compareItem == null)
      return false;

    String compareCustomItemId = PersistentDataUtil.getString(compareItem, CustomItemProperty.CUSTOM_ITEM_ID);

    if (compareCustomItemId == null)
      return false;
    return compareCustomItemId.equals(customItemId);
  }
}
