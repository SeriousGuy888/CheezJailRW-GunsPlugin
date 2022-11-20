package io.github.seriousguy888.cheezjailrwguns.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ScopeUtil {
  public static final ArrayList<Player> scopingPlayers = new ArrayList<>();

  public static boolean isPlayerScoping(Player player) {
    return scopingPlayers.contains(player);
  }
}
