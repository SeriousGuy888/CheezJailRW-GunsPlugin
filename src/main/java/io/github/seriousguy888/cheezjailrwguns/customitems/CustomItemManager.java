package io.github.seriousguy888.cheezjailrwguns.customitems;

import io.github.seriousguy888.cheezjailrwguns.customitems.items.*;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.MediumAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.SmallAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.AssaultRifle;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.Pistol;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.Smg;
import org.bukkit.Material;

import java.util.ArrayList;

import static io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils.createItemStack;

public class CustomItemManager {
  // Todo: make all ammo load before guns or something like that
  public final static SmallAmmo AMMO_SMALL = new SmallAmmo(
      createItemStack(
          Material.IRON_NUGGET,
          "&rSmall Ammo",
          6600301),
      "ammo_small");
  public final static MediumAmmo AMMO_MEDIUM = new MediumAmmo(
      createItemStack(
          Material.IRON_NUGGET,
          "&rMedium Ammo",
          6600302),
      "ammo_medium");
  public final static Pistol PISTOL = new Pistol(
      createItemStack(
          Material.CARROT_ON_A_STICK,
          "&rPistol",
          6600201),
      "pistol");
  public final static AssaultRifle ASSAULT_RIFLE = new AssaultRifle(
      createItemStack(
          Material.IRON_HOE,
          "&rAssault Rifle",
          6600401),
      "assault_rifle");
  public final static Smg SMG = new Smg(
      createItemStack(
          Material.IRON_HOE,
          "&rSMG",
          6600501),
      "smg");


  public final static ArrayList<AbstractCustomItem> items = new ArrayList<>() {{
    add(AMMO_SMALL);
    add(AMMO_MEDIUM);
    add(PISTOL);
    add(ASSAULT_RIFLE);
    add(SMG);
  }};
}