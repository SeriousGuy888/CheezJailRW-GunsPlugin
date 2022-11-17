package io.github.seriousguy888.cheezjailrwguns;

import io.github.seriousguy888.cheezjailrwguns.commands.GetGunsCommand;
import io.github.seriousguy888.cheezjailrwguns.events.AnvilEvents;
import io.github.seriousguy888.cheezjailrwguns.events.GunEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class CheezJailRWGuns extends JavaPlugin {
  private static CheezJailRWGuns plugin;

  public static CheezJailRWGuns getPlugin() {
    return plugin;
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
    pm.registerEvents(new AnvilEvents(), this);
  }
}
