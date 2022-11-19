package io.github.seriousguy888.cheezjailrwguns.items;

import io.github.seriousguy888.cheezjailrwguns.items.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.items.items.AssaultRifle;
import io.github.seriousguy888.cheezjailrwguns.items.items.Pistol;
import io.github.seriousguy888.cheezjailrwguns.items.items.SmallAmmo;
import org.bukkit.Material;

import java.util.ArrayList;

import static io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils.createItemStack;

public class CustomItemManager {
  // Todo: make all ammo load before guns or something like that
  public final static SmallAmmo AMMO_SMALL = new SmallAmmo(
      createItemStack(
          Material.IRON_NUGGET,
          "&rSmall Ammo",
          60036001),
      "ammo_small");
  public final static Pistol PISTOL = new Pistol(
      createItemStack(
          Material.CARROT_ON_A_STICK,
          "&rPistol",
          60026001),
      "pistol",
      12);
  public final static AssaultRifle ASSAULT_RIFLE = new AssaultRifle(
      createItemStack(
          Material.IRON_HOE,
          "&rAssault Rifle",
          60046001),
      "assault_rifle",
      24);


  public final static ArrayList<AbstractCustomItem> items = new ArrayList<>() {{
    add(AMMO_SMALL);
    add(PISTOL);
    add(ASSAULT_RIFLE);
  }};
}