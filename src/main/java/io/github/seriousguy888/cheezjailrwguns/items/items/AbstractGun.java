package io.github.seriousguy888.cheezjailrwguns.items.items;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils;
import io.github.seriousguy888.cheezjailrwguns.items.PersistentDataUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public abstract class AbstractGun extends AbstractCustomItem {
  protected final AbstractAmmo ammoType;
  protected final long cooldownMs;

  public AbstractGun(ItemStack item,
                     String customItemId,
                     int maxAmmo,
                     AbstractAmmo ammoType,
                     long cooldownMs) {
    super(item, customItemId);
    this.ammoType = ammoType;
    this.cooldownMs = cooldownMs;

    PersistentDataUtil.setInt(item, CustomItemProperty.GUN_MAX_AMMO, maxAmmo);
    setAmmo(item, maxAmmo);
  }

  public int getAmmo(ItemStack item) {
    return PersistentDataUtil.getInt(item, CustomItemProperty.GUN_AMMO);
  }

  public void setAmmo(ItemStack item, int newAmmo) {
    if (!this.is(item))
      return;
    PersistentDataUtil.setInt(item, CustomItemProperty.GUN_AMMO, newAmmo);
  }

  public int getMaxAmmo(ItemStack item) {
    return PersistentDataUtil.getInt(item, CustomItemProperty.GUN_MAX_AMMO);
  }

  public long getCooldownMs() {
    return cooldownMs;
  }

  public void updateAmmoDisplay(ItemStack item) {
    if (!this.is(item))
      return;
    if (!(item.getItemMeta() instanceof Damageable meta))
      return;

    int ammo = getAmmo(item);
    int maxAmmo = getMaxAmmo(item);

    short maxDurability = item.getType().getMaxDurability();
    float durabilityPercentage = (float) ammo / maxAmmo;
    int damage = Math.min(
        Math.round((1 - durabilityPercentage) * maxDurability),
        maxDurability);

    meta.setDamage(damage);
//    meta.setLore(new ArrayList<>() {{
//      add(ChatColor.GRAY + "Ammo: " + ammo + "/" + maxAmmo);
//    }});

    item.setItemMeta(meta);
  }

  public void reloadGun(ItemStack item, int amount) {
    if (!this.is(item))
      return;

    setAmmo(item, getAmmo(item) + amount);
    updateAmmoDisplay(item);
  }

  public ItemStack getCorrectAmmoStack(ItemStack item, Inventory inventory) {
    if (!this.is(item))
      return null;
    return CustomItemUtils.getCustomItemStack(inventory, ammoType);
  }
}
