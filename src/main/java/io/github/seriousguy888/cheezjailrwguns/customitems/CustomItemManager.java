package io.github.seriousguy888.cheezjailrwguns.customitems;

import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.MediumAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.SmallAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.AssaultRifle;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.CompactSmg;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.Pistol;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.Smg;

import java.util.ArrayList;

public class CustomItemManager {
  // Todo: make all ammo load before guns or something like that
  public final static SmallAmmo AMMO_SMALL = new SmallAmmo("ammo_small");
  public final static MediumAmmo AMMO_MEDIUM = new MediumAmmo("ammo_medium");
  public final static Pistol PISTOL = new Pistol("pistol");
  public final static AssaultRifle ASSAULT_RIFLE = new AssaultRifle("assault_rifle");
  public final static Smg SMG = new Smg("smg");
  public final static CompactSmg COMPACT_SMG = new CompactSmg("compact_smg");


  public final static ArrayList<AbstractCustomItem> items = new ArrayList<>() {{
    add(AMMO_SMALL);
    add(AMMO_MEDIUM);
    add(PISTOL);
    add(ASSAULT_RIFLE);
    add(SMG);
    add(COMPACT_SMG);
  }};
}