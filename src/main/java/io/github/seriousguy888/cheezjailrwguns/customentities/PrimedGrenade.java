package io.github.seriousguy888.cheezjailrwguns.customentities;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PrimedGrenade {
  private int fuseTimeTicks;
  private Item entity;
  private Player owner;

  public PrimedGrenade() {
    fuseTimeTicks = 60;
  }

  public Entity spawn(Location location, Player owner) {
    var entityManager = CheezJailRWGuns.getEntityManager();

    World world = location.getWorld();
    if (world == null) {
      return null;
    }

    ItemStack item = new ItemStack(Material.TNT);

    var droppedItem = world.spawn(location, Item.class);
    droppedItem.setItemStack(item);
    droppedItem.setPickupDelay(32767); // infinite
    droppedItem.setCustomNameVisible(true);
    droppedItem.setInvulnerable(true);

    this.owner = owner;
    droppedItem.setThrower(owner.getUniqueId());

    entity = droppedItem;
    updateFuseTimer();
    return entity;
  }

  public void tick() {
    updateFuseTimer();
  }

  public void die() {
    CheezJailRWGuns.getEntityManager().remove(entity);
    entity.remove();
  }

  private void updateFuseTimer() {
    if (fuseTimeTicks <= 0) {
      var world = entity.getWorld();
      world.createExplosion(entity.getLocation(), 2f, false, false, owner);
      die();
      return;
    }

    float secondsRemaining = (float) fuseTimeTicks / 20;
    entity.setCustomName(Float.toString(secondsRemaining) + "s");
    fuseTimeTicks -= 1;
  }
}
