package io.github.seriousguy888.cheezjailrwguns.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.function.Function;

public class CustomItemData {
  public ItemStack item;
  public String customItemId;

  public CustomItemData(ItemStack item, String customItemId, @Nullable Function<ItemStack, ItemStack> initFunc) {
    this.item = item;
    this.customItemId = customItemId;

    CustomItemUtils.setProperty(item,
        CustomItemProperty.CUSTOM_ITEM_ID,
        PersistentDataType.STRING,
        customItemId);

    if (initFunc != null)
      this.item = initFunc.apply(item);
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
    return compareCustomItemId.equals(this.customItemId);
  }
}
