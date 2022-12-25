package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class AbstractProjectile extends AbstractCustomItem {
  protected HashMap<Player, Long> lastUsedMap;

  public AbstractProjectile(String customItemId) {
    super(customItemId);
    lastUsedMap = new HashMap<>();
  }

  public boolean canUseAgain(Player player) {
    long lastUsed = lastUsedMap.getOrDefault(player, 0L);
    long currTime = System.currentTimeMillis();
    return currTime - lastUsed > (getCooldownTicks() * 50L);
  }

  /**
   * If the specified player is holding this item, removes one from
   * the player's held itemstack as long as they are not in creative
   * mode.
   *
   * @param player Player who is using the item
   */
  public void consume(Player player) {
    ItemStack heldItem = player.getInventory().getItemInMainHand();
    if (!this.is(heldItem))
      return;

    if (player.getGameMode() != GameMode.CREATIVE) {
      heldItem.setAmount(heldItem.getAmount() - 1);
    }

    lastUsedMap.put(player, System.currentTimeMillis());
    player.setCooldown(item.getType(), getCooldownTicks());
  }

  // How many ticks the cooldown for this projectile item is
  protected abstract int getCooldownTicks();

  public abstract Projectile launch(Player player);

  public abstract void hit(ProjectileHitEvent event);
}
