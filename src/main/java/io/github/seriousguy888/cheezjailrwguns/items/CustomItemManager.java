package io.github.seriousguy888.cheezjailrwguns.items;

import io.github.seriousguy888.cheezjailrwguns.items.items.BaseCustomItem;
import io.github.seriousguy888.cheezjailrwguns.items.items.GenericCustomItem;
import io.github.seriousguy888.cheezjailrwguns.items.items.Pistol;
import org.bukkit.Material;

import java.util.ArrayList;

import static io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils.createItemStack;

public class CustomItemManager {
  public final static Pistol PISTOL = new Pistol(
      createItemStack(
          Material.CARROT_ON_A_STICK,
          "&rPistol",
          60026001),
      "pistol",
      12);
  public final static BaseCustomItem AMMO_SMALL = new GenericCustomItem(
      createItemStack(
          Material.IRON_NUGGET,
          "&rSmall Ammo",
          60036001),
      "ammo_small");


  public final static ArrayList<BaseCustomItem> items = new ArrayList<>() {{
    add(PISTOL);
    add(AMMO_SMALL);
  }};
}