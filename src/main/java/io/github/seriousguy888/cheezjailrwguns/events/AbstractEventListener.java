package io.github.seriousguy888.cheezjailrwguns.events;

import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class AbstractEventListener implements Listener {
  protected final CheezJailRWGuns plugin;

  public AbstractEventListener(CheezJailRWGuns plugin) {
    this.plugin = plugin;
    Bukkit.getServer()
        .getPluginManager()
        .registerEvents(this, plugin);
  }
}
