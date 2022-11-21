package io.github.seriousguy888.cheezjailrwguns.customitems.items;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.customitems.PersistentDataUtil;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCustomItem {
  private final String customItemId;
  protected ItemStack item;

  public AbstractCustomItem(String customItemId) {
    this.customItemId = customItemId;

    item = getBaseItem();
    PersistentDataUtil.setString(item,
        CustomItemProperty.CUSTOM_ITEM_ID,
        customItemId);
  }

  public String getCustomItemId() {
    return customItemId;
  }

  public ItemStack getItem() {
    return item;
  }

  /**
   * Used for setup by children of this class. This is the ItemStack
   * used to set up the version of the item with its custom ID in
   * perisistent storage.
   *
   * @return The base ItemStack that represents the custom item.
   */
  protected abstract ItemStack getBaseItem();

  public boolean is(ItemStack compareItem) {
    if (compareItem == null)
      return false;

    String compareCustomItemId = PersistentDataUtil.getString(compareItem, CustomItemProperty.CUSTOM_ITEM_ID);

    if (compareCustomItemId == null)
      return false;
    return compareCustomItemId.equals(customItemId);
  }
}
