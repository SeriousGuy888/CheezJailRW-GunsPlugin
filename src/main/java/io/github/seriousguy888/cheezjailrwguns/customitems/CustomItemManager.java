package io.github.seriousguy888.cheezjailrwguns.customitems;

import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.HeavyAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.MediumAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.ShotgunAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.ammo.SmallAmmo;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.*;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles.Flashbang;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles.Grenade;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.projectiles.Molotov;

import java.util.ArrayList;

public class CustomItemManager {
  public final static SmallAmmo AMMO_SMALL = new SmallAmmo("ammo_small");
  public final static MediumAmmo AMMO_MEDIUM = new MediumAmmo("ammo_medium");
  public final static HeavyAmmo AMMO_HEAVY = new HeavyAmmo("ammo_heavy");
  public final static ShotgunAmmo AMMO_SHOTGUN = new ShotgunAmmo("ammo_shotgun");

  public final static Pistol PISTOL = new Pistol("pistol");
  public final static AssaultRifle ASSAULT_RIFLE = new AssaultRifle("assault_rifle");
  public final static Smg SMG = new Smg("smg");
  public final static CompactSmg COMPACT_SMG = new CompactSmg("compact_smg");
  public final static Shotgun SHOTGUN = new Shotgun("shotgun");
  public final static Sniper SNIPER = new Sniper("sniper");
  public final static Dmr DMR = new Dmr("dmr");

  public final static Flashbang FLASHBANG = new Flashbang("flashbang");
  public final static Molotov MOLOTOV = new Molotov("molotov");
  public final static Grenade GRENADE = new Grenade("grenade");


  public final static ArrayList<AbstractCustomItem> items = new ArrayList<>() {{
    add(AMMO_SMALL);
    add(AMMO_MEDIUM);
    add(AMMO_HEAVY);
    add(AMMO_SHOTGUN);
    add(PISTOL);
    add(ASSAULT_RIFLE);
    add(SMG);
    add(COMPACT_SMG);
    add(SHOTGUN);
    add(SNIPER);
    add(DMR);
    add(FLASHBANG);
    add(MOLOTOV);
    add(GRENADE);
  }};
}