package io.github.seriousguy888.cheezjailrwguns.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public enum CustomItem {
  PISTOL(
      createItemStack(
          Material.CARROT_ON_A_STICK,
          "&rPistol",
          60026001),
      "pistol"
  ),
  AMMO_SMALL(
      createItemStack(
          Material.IRON_NUGGET,
          "&rSmall Ammo",
          60036001),
      "ammo_small"
  );

  public ItemStack item;
  public String customItemId;

  CustomItem(ItemStack item, String customItemId) {
    this.item = item;
    this.customItemId = customItemId;

    CustomItemUtils.setProperty(item,
        CustomItemProperty.CUSTOM_ITEM_ID,
        PersistentDataType.STRING,
        customItemId);
  }

  private static ItemStack createItemStack(Material material, String name, Integer customModelData) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return null;

    meta.setCustomModelData(customModelData);
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    item.setItemMeta(meta);

    return item;
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