package io.github.seriousguy888.cheezjailrwguns;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.seriousguy888.cheezjailrwguns.commands.GetGunsCommand;
import io.github.seriousguy888.cheezjailrwguns.events.GunListener;
import io.github.seriousguy888.cheezjailrwguns.events.ProjectileListener;
import io.github.seriousguy888.cheezjailrwguns.events.ScopeListener;
import io.github.seriousguy888.cheezjailrwguns.managers.CustomEntityManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CheezJailRWGuns extends JavaPlugin {
  private static CheezJailRWGuns plugin;
  private static ProtocolManager protocolManager;
  private static CustomEntityManager entityManager;

  int everyTickSchedule;

  public static CheezJailRWGuns getPlugin() {
    return plugin;
  }

  public static ProtocolManager getProtocolManager() {
    return protocolManager;
  }

  public static CustomEntityManager getEntityManager() {
    return entityManager;
  }

  public void onLoad() {
    protocolManager = ProtocolLibrary.getProtocolManager();
    entityManager = new CustomEntityManager();
  }

  @Override
  public void onEnable() {
    plugin = this;

    registerCommands();
    registerEvents();

    if(!Bukkit.getScheduler().isCurrentlyRunning(everyTickSchedule)) {
      everyTickSchedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
        entityManager.tickAllEntities();
      }, 1, 1);
    }
  }

  private void registerCommands() {
    this.getCommand("getguns").setExecutor(new GetGunsCommand());
  }

  private void registerEvents() {
    new GunListener(this);
    new ScopeListener(this);
    new ProjectileListener(this);
  }
}
