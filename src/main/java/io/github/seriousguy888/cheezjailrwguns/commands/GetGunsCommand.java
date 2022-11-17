package io.github.seriousguy888.cheezjailrwguns.commands;

import io.github.seriousguy888.cheezjailrwguns.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetGunsCommand implements TabExecutor {
  public final List<String> validItems;

  public GetGunsCommand() {
    validItems = Arrays
        .stream(CustomItem.values())
        .map(Enum::name)
        .map(String::toUpperCase)
        .collect(Collectors.toList());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player player))
      return false;

    if (args.length == 0) {
      player.sendMessage("Specify an item from this plugin");
      return true;
    }

    String itemName = args[0].toUpperCase();

    int amount = 1;
    if (args.length >= 2) {
      try {
        amount = Integer.parseInt(args[1]);
      } catch (NumberFormatException ignored) {
      }
    }


    if (validItems.contains(itemName)) {
      ItemStack item = CustomItem.valueOf(itemName).item;
      item.setAmount(amount);

      player.getInventory().addItem(item);
    } else {
      player.sendMessage(ChatColor.RED + "Invalid item name");
    }
    return true;
  }

  @Nullable
  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 1) {
      return validItems;
    } else if (args.length == 2) {
      return new ArrayList<>() {{
        add("8");
        add("16");
        add("32");
        add("64");
      }};
    }

    return null;
  }
}
