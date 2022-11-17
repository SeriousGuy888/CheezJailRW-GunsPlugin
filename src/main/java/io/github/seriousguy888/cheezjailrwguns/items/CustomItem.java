package io.github.seriousguy888.cheezjailrwguns.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomItem {
  PISTOL(new CustomItemData(
      createItemStack(
          Material.CARROT_ON_A_STICK,
          "&rPistol",
          60026001),
      "pistol",
      CustomItemInitializers::initGun
  )),
  AMMO_SMALL(new CustomItemData(
      createItemStack(
          Material.IRON_NUGGET,
          "&rSmall Ammo",
          60036001),
      "ammo_small",
      null
  ));

  private final CustomItemData customItem;

  CustomItem(CustomItemData customItem) {
    this.customItem = customItem;
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

  public ItemStack getItemStack() {
    return customItem.item;
  }

  public boolean is(ItemStack compareItem) {
    return customItem.is(compareItem);
  }
}