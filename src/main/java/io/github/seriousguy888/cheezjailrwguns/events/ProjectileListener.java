package io.github.seriousguy888.cheezjailrwguns.events;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtil;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles.AbstractProjectile;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ProjectileListener extends AbstractEventListener {
  public HashMap<Projectile, AbstractProjectile> projectileMap;

  public ProjectileListener(CheezJailRWGuns plugin) {
    super(plugin);
    projectileMap = new HashMap<>();
  }

  @EventHandler
  public void onInteraction(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Action action = event.getAction();
    ItemStack heldItem = player.getInventory().getItemInMainHand();

    AbstractCustomItem heldCustomItem = CustomItemUtil.getCustomItem(heldItem);
    if (heldCustomItem == null) // If this is not a plugin managed custom item
      return;
    if (!(heldCustomItem instanceof AbstractProjectile projType)) // If this is not a projectile item
      return;

    // If not right click
    if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK))
      return;

    event.setCancelled(true);

    if (!projType.canUseAgain(player)) {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
          TextComponent.fromLegacyText("Item on cooldown"));
      return;
    }

    projType.consume(player);
    projectileMap.put(projType.launch(player), projType);
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    if (!projectileMap.containsKey(projectile))
      return;

    AbstractProjectile custProjectile = projectileMap.get(projectile);
    custProjectile.hit(event);
  }
}
