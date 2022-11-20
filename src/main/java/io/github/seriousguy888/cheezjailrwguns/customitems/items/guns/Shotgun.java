package io.github.seriousguy888.cheezjailrwguns.customitems.items.guns;

import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils.createItemStack;

public class Shotgun extends AbstractGun {
  public Shotgun(String customItemId) {
    super(customItemId,
        CustomItemManager.AMMO_SHOTGUN,
        6,
        7,
        750L,
        100,
        2);
  }

  @Override
  protected void setItem() {
    item = createItemStack(
        Material.WOODEN_HOE,
        "&rShotgun",
        6600701);
  }

  @Override
  public HashMap<Vector, RayTraceResult> doShotRaycasts(Player player, Location playerLoc, Vector playerDir) {
    HashMap<Vector, RayTraceResult> bulletCollisions = new HashMap<>();


    Random random = new Random();
    Vector perpVec = getPerpendicularDir(playerLoc);

    for (int i = 0; i < simultaneousFire; i++) {
      Vector raycastVec = randomlyRotateVector(playerDir.clone(), perpVec, random, 15, 15);
      bulletCollisions.put(raycastVec, castRay(player, playerLoc, raycastVec));
    }

    return bulletCollisions;
  }

  @Override
  protected void setSounds() {
    shootSound = "shotgun";
    reloadSound = null;
  }

  @Override
  protected void setSimultaneousFire() {
    simultaneousFire = 7;
  }
}
