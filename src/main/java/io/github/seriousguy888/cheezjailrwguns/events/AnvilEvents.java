package io.github.seriousguy888.cheezjailrwguns.events;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItem;
import io.github.seriousguy888.cheezjailrwguns.items.CustomItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilEvents implements Listener {
//  @EventHandler
//  public void onPrepareAnvil(PrepareAnvilEvent event) {
//    AnvilInventory anvilInventory = event.getInventory();
//    ItemStack inputItem = anvilInventory.getItem(0);
//    ItemStack additionalItem = anvilInventory.getItem(1);
//
//    CustomItem customItem = CustomItemUtils.getCustomItem(inputItem);
//    if(customItem == null)
//      return;
//
//    String renameText = anvilInventory.getRenameText();
//    if(renameText == null)
//      return;
//
//    if(additionalItem == null && renameText.isBlank()) {
//      // Allow players to rename their custom items or reset it to the default state.
//      event.setResult(customItem.getItemStack());
//    }
//  }
}
