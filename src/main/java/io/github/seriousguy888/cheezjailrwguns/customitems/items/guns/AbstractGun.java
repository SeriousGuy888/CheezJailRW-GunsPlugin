package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils;
import io.github.seriousguy888.cheezjailrwguns.customitems.PersistentDataUtil;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.AbstractAmmo;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;

public abstract class AbstractGun extends AbstractCustomItem {
  protected final AbstractAmmo ammoType;
  protected final int maxAmmo;
  protected final float range;
  protected final long cooldownMs;
  protected final int reloadTicks;
  protected final float damage;
  protected String shootSound;
  protected String reloadSound;

  public AbstractGun(ItemStack item,
                     String customItemId,
                     AbstractAmmo ammoType,
                     int maxAmmo,
                     float range,
                     long cooldownMs,
                     int reloadTicks,
                     float damage) {
    super(item, customItemId);
    this.ammoType = ammoType;
    this.maxAmmo = maxAmmo;
    this.range = range;
    this.cooldownMs = cooldownMs;
    this.reloadTicks = reloadTicks;
    this.damage = damage;

    setSounds();

    setAmmo(item, maxAmmo);
  }

  public float getRange() {
    return range;
  }

  // Overridden by children to set their own sounds.
  protected void setSounds() {
    shootSound = "generic";
    reloadSound = "generic";
  }

  public String getSoundString(GunSoundType soundType) {
    String soundSet = "generic";
    switch (soundType) {
      case SHOOT -> soundSet = shootSound;
      case RELOAD -> soundSet = reloadSound;
    }

    return "cheezjail.guns." + soundType.name().toLowerCase() + "." + soundSet;
  }

  public int getReloadTicks() {
    return reloadTicks;
  }

  public float getDamage() {
    return damage;
  }

  public int getAmmo(ItemStack item) {
    return PersistentDataUtil.getInt(item, CustomItemProperty.GUN_AMMO);
  }

  public void setAmmo(ItemStack item, int newAmmo) {
    if (!this.is(item))
      return;
    PersistentDataUtil.setInt(item, CustomItemProperty.GUN_AMMO, newAmmo);
  }

  public int getMaxAmmo() {
    return maxAmmo;
  }

  public long getCooldownMs() {
    return cooldownMs;
  }

  public void updateStatsDisplay(ItemStack item) {
    if (!this.is(item))
      return;
    if (!(item.getItemMeta() instanceof Damageable meta))
      return;

    int ammo = getAmmo(item);

    short maxDurability = item.getType().getMaxDurability();
    float durabilityPercentage = (float) ammo / maxAmmo;
    int durabilityDamage = Math.min(
        Math.round((1 - durabilityPercentage) * maxDurability),
        maxDurability);

    meta.setDamage(durabilityDamage);
    meta.setLore(new ArrayList<String>() {{
      add("&7Ammo: &f" + ammo + "&7/" + maxAmmo);
      add("&7Range: &f" + range);
      add("&7DMG: &f" + damage);
      add(String.format("&7Reload: &f%.1fs", (float) reloadTicks / 20));
      add(String.format("&7Fire Rate: &f%.1f/s", (float) 1000 / cooldownMs));
    }}
        .stream()
        .map(e -> ChatColor.translateAlternateColorCodes('&', e))
        .toList());

    item.setItemMeta(meta);
  }

  public void reloadGun(ItemStack item, int amount) {
    if (!this.is(item))
      return;

    setAmmo(item, getAmmo(item) + amount);
    updateStatsDisplay(item);
  }

  public ItemStack getCorrectAmmoStack(ItemStack item, Inventory inventory) {
    if (!this.is(item))
      return null;
    return CustomItemUtils.getCustomItemStack(inventory, ammoType);
  }

  public enum GunSoundType {
    SHOOT,
    RELOAD
  }
}
