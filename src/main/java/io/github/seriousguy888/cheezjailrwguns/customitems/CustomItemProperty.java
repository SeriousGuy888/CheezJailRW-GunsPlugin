package io.github.seriousguy888.cheezjailrwguns.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public enum CustomItemProperty {
  CUSTOM_ITEM_ID("custom_item", PersistentDataType.STRING),
  GUN_AMMO("gun_ammo", PersistentDataType.INTEGER);

  NamespacedKey namespacedKey;
  PersistentDataType<?, ?> dataType;

  <T, Z> CustomItemProperty(String propertyKey, PersistentDataType<T, Z> dataType) {
    this.namespacedKey = CustomItemUtils.getNamespacedKey(propertyKey);
    this.dataType = dataType;
  }
}
