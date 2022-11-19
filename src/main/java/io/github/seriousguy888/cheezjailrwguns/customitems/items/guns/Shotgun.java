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
    // Absolutely disgusting code because i barely understand vector math
    // todo: learn vectors and redo this code

    HashMap<Vector, RayTraceResult> bulletCollisions = new HashMap<>();

    Location locPerpendicular = playerLoc.clone();
    locPerpendicular.setPitch(locPerpendicular.getPitch() + 90f);
    locPerpendicular.setYaw(locPerpendicular.getYaw() + 90f);


    float spread = 0.2f;
    Vector dir = playerDir.clone();
    Random random = new Random();
    for (int i = 0; i < simultaneousFire; i++) {
      Vector offset = locPerpendicular.getDirection().multiply(spread);
      Vector raycastVec = dir.clone()
          .add(offset)
          .rotateAroundAxis(playerDir, random.nextGaussian() * (Math.PI * 2))
          .normalize();

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
