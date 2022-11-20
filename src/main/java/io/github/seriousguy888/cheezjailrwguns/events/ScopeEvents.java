package io.github.seriousguy888.cheezjailrwguns.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.github.seriousguy888.cheezjailrwguns.CheezJailRWGuns;
import io.github.seriousguy888.cheezjailrwguns.customitems.CustomItemUtils;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.AbstractCustomItem;
import io.github.seriousguy888.cheezjailrwguns.customitems.items.guns.AbstractGun;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ScopeEvents implements Listener {
  private final Plugin plugin;
  private final ArrayList<Player> scopingPlayers;

  private final ItemStack scopePumpkin = new ItemStack(Material.CARVED_PUMPKIN);

  public ScopeEvents() {
    plugin = CheezJailRWGuns.getPlugin();
    scopingPlayers = new ArrayList<>();

    ItemMeta pumpkinMeta = scopePumpkin.getItemMeta();
    if (pumpkinMeta != null) {
      pumpkinMeta.setDisplayName(ChatColor.YELLOW + "Scope Pumpkin");
      pumpkinMeta.setLore(new ArrayList<>() {{
        add(":D");
      }});
      scopePumpkin.setItemMeta(pumpkinMeta);
    }

    CheezJailRWGuns.getProtocolManager()
        .addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.WINDOW_ITEMS) {
          @Override
          public void onPacketSending(PacketEvent event) {
            PacketContainer packet = event.getPacket();

            Integer windowId = packet.getIntegers().readSafely(0);
            List<ItemStack> modifiedItems = packet.getItemListModifier().readSafely(0);

            if (scopingPlayers.contains(event.getPlayer()) && windowId == 0) {
              modifiedItems.set(5, scopePumpkin);
              packet.getItemListModifier().writeSafely(0, modifiedItems);
            }
          }
        });
  }

  @EventHandler
  public void onSneak(PlayerToggleSneakEvent event) {
    Player player = event.getPlayer();
    ItemStack heldItem = player.getInventory().getItemInMainHand();


    AbstractCustomItem heldCustomItem = CustomItemUtils.getCustomItem(heldItem);
    if (heldCustomItem == null) // If this is not a plugin managed custom item
      return;
    if (!(heldCustomItem instanceof AbstractGun)) // If this is not a gun
      return;

    updateScoping(player, event.isSneaking());
  }

  private void updateScoping(Player player, boolean isScoping) {
    PacketContainer helmetPacket = new PacketContainer(PacketType.Play.Server.SET_SLOT);

    helmetPacket.getIntegers()
        .write(0, 0)  // Window id
        .write(1, 1)  // Amount
        .write(2, 5); // Slot (helmet)

    if (isScoping) {
      scopingPlayers.add(player);
      helmetPacket.getItemModifier().write(0, scopePumpkin);
    } else {
      scopingPlayers.remove(player);
      helmetPacket.getItemModifier().write(0, player.getInventory().getHelmet());
    }

    try {
      CheezJailRWGuns.getProtocolManager().sendServerPacket(player, helmetPacket);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}