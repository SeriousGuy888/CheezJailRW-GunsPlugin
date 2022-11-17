package io.github.seriousguy888.cheezjailrwguns.items;

import org.bukkit.NamespacedKey;

public enum CustomItemProperty {
  CUSTOM_ITEM_ID("custom_item"),
  GUN_AMMO("gun_ammo"),
  GUN_MAX_AMMO("gun_max_ammo");

  NamespacedKey namespacedKey;

  CustomItemProperty(String propertyKey) {
    this.namespacedKey = CustomItemUtils.getNamespacedKey(propertyKey);
  }
}
