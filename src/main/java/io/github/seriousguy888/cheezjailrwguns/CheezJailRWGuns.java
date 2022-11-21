package io.github.seriousguy888.cheezjailrwguns;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.seriousguy888.cheezjailrwguns.commands.GetGunsCommand;
import io.github.seriousguy888.cheezjailrwguns.events.GunListener;
import io.github.seriousguy888.cheezjailrwguns.events.ProjectileListener;
import io.github.seriousguy888.cheezjailrwguns.events.ScopeListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CheezJailRWGuns extends JavaPlugin {
  private static CheezJailRWGuns plugin;
  private static ProtocolManager protocolManager;

  public static ProtocolManager getProtocolManager() {
    return protocolManager;
  }

  public static CheezJailRWGuns getPlugin() {
    return plugin;
  }

  public void onLoad() {
    protocolManager = ProtocolLibrary.getProtocolManager();
  }

  @Override
  public void onEnable() {
    plugin = this;

    registerCommands();
    registerEvents();
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
