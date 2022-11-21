package io.github.seriousguy888.cheezjailrwguns;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.seriousguy888.cheezjailrwguns.commands.GetGunsCommand;
import io.github.seriousguy888.cheezjailrwguns.events.GunEvents;
import io.github.seriousguy888.cheezjailrwguns.events.ScopeEvents;
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
    var pm = getServer().getPluginManager();

    pm.registerEvents(new GunEvents(), this);
    pm.registerEvents(new ScopeEvents(), this);
  }
}
