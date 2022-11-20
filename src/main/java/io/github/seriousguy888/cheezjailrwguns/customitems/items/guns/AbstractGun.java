package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemProperty;
import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil;
import io.github.seriousguy888.cheezjailrwguns.customitems.PersistentDataUtil;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.AbstractAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.interfaces.IScopedGun;
import io.github.seriousguy888.cheezjailrwguns.utils.ScopeUtil;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class AbstractGun extends AbstractCustomItem {
  protected final AbstractAmmo ammoType;
  protected final int maxAmmo;
  protected final float range;
  protected final long cooldownMs;
  protected final int reloadTicks;
  protected final float damage;
  protected int simultaneousFire; // how many bullets it fires at once (optional; used by shotguns)
  protected String shootSound;
  protected String reloadSound;

  public AbstractGun(String customItemId,
                     AbstractAmmo ammoType,
                     int maxAmmo,
                     float range,
                     long cooldownMs,
                     int reloadTicks,
                     float damage) {
    super(customItemId);
    this.ammoType = ammoType;
    this.maxAmmo = maxAmmo;
    this.range = range;
    this.cooldownMs = cooldownMs;
    this.reloadTicks = reloadTicks;
    this.damage = damage;

    setSimultaneousFire();
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

  // Overridden by guns such as shotguns
  protected void setSimultaneousFire() {
    simultaneousFire = 1;
  }

  public String getSoundString(GunSoundType soundType) {
    String soundSet = "generic";
    switch (soundType) {
      case SHOOT -> soundSet = shootSound;
      case RELOAD -> soundSet = reloadSound;
    }

    // Some guns may not have a reload sound at all
    if (soundSet == null)
      return "";

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
      add("&7DMG: &f" + damage + (simultaneousFire != 1 ? " × " + simultaneousFire : ""));
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

  // Might be overridden for guns with special shots (e.g. shotgun)
  public HashMap<Vector, RayTraceResult> doShotRaycasts(Player player, Location playerLoc, Vector playerDir) {
    Vector raycastVec = playerDir.clone();

    if (this instanceof IScopedGun && !ScopeUtil.isPlayerScoping(player)) {
      Random random = new Random();
      Vector perpVec = getPerpendicularDir(playerLoc);
      raycastVec = randomlyRotateVector(raycastVec, perpVec, random, 5, 5);
    }

    RayTraceResult result = castRay(player, playerLoc, raycastVec);

    return new HashMap<>() {{
      put(playerDir, result);
    }};
  }

  protected Vector randomlyRotateVector(Vector vectorToRotate,
                                        Vector perpendicularVector,
                                        Random random,
                                        float verVariationDeg,
                                        float horFariationDeg) {
    // scuffed math that is just good enough to work

    // Vertical rotation
    Vector offset = perpendicularVector.multiply(
        Math.toRadians((random.nextGaussian() - 0.5) * verVariationDeg));

    // Horizontal rotation (rotated about the perpendicular axis provided)
    double angle = Math.toRadians((random.nextGaussian() - 0.5) * horFariationDeg);

    return vectorToRotate
        .add(offset)
        .rotateAroundAxis(perpendicularVector, angle)
        .normalize();
  }

  protected Vector getPerpendicularDir(Location loc) {
    Location perp = loc.clone();
    perp.setPitch(perp.getPitch() + 90f);
//    perp.setYaw(perp.getYaw() + 90f);
    return perp.getDirection();
  }

  @Nullable
  protected RayTraceResult castRay(Player player,
                                   Location startingLocation,
                                   Vector direction) {
    return player.getWorld().rayTrace(
        startingLocation,
        direction,
        range,
        FluidCollisionMode.NEVER,
        true,
        0.2,
        entity -> !entity.equals(player)
    );
  }


  public ItemStack getCorrectAmmoStack(ItemStack item, Inventory inventory) {
    if (!this.is(item))
      return null;
    return CustomItemUtil.getCustomItemStack(inventory, ammoType);
  }

  public enum GunSoundType {
    SHOOT,
    RELOAD
  }
}
