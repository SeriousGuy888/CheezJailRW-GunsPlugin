package io.github.seriousguy888.cheezjailrwguns.managers;

import io.github.seriousguy888.cheezjailrwguns.customentities.PrimedGrenade;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


public class CustomEntityManager {
  private final HashMap<Entity, PrimedGrenade> activeEntities;

  public CustomEntityManager() {
    activeEntities = new HashMap<>();
  }

  public void tickAllEntities() {
    activeEntities.values().forEach(PrimedGrenade::tick);
  }

  public void spawn(Class<PrimedGrenade> customEntityClass, Location location, Player owner) {
    PrimedGrenade custEntityInstance;
    try {
      custEntityInstance = customEntityClass
          .getDeclaredConstructor()
          .newInstance();
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
      return;
    }
    var entity = custEntityInstance.spawn(location, owner);
    activeEntities.put(entity, custEntityInstance);
  }

  public void remove(Entity entity) {
    activeEntities.remove(entity);
  }
}
