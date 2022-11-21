package io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles;

import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractProjectile extends AbstractCustomItem {
  public AbstractProjectile(String customItemId) {
    super(customItemId);
  }

  /**
   * If the specified player is holding this item, removes one from
   * the player's held itemstack as long as they are not in creative
   * mode.
   *
   * @param player Player who is using the item
   */
  public void consume(Player player) {
    if (player.getGameMode() == GameMode.CREATIVE)
      return;

    ItemStack heldItem = player.getInventory().getItemInMainHand();
    if (!this.is(heldItem))
      return;

    heldItem.setAmount(heldItem.getAmount() - 1);
  }
}
