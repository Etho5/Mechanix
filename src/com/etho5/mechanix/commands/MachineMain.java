package com.etho5.mechanix.commands;

import com.etho5.mechanix.ingredients.IngredientList;
import com.etho5.mechanix.machines.MachineItem;
import com.etho5.mechanix.menus.MenuBook;
import com.etho5.mechanix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MachineMain implements TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("help =)");
            return false;
        } else if (args[0].equalsIgnoreCase("give")) {
            if(!(sender instanceof Player)) return false;
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage(args[1] + " is invalid!");
                return false;
            }
            String name = args[2];

            if(args.length > 3) {
                int amount = 1;
                try {
                    amount = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(args[3] + " is not a valid number!");
                }

                if(MachineItem.fromString(name) != null) {
                    ItemStack machineItem = Utils.getMachineItem(name, amount);
                    target.getInventory().addItem(machineItem);
                    sender.sendMessage("Given " + machineItem.getAmount() + "x of " + name + " to " + target.getName());
                    target.sendMessage("Received " + machineItem.getAmount() + "x of " + name);
                }

                else if(IngredientList.fromString(name) != null) {
                    ItemStack ingItem = Utils.getIngredientItem(name, amount);
                    target.getInventory().addItem(ingItem);
                    sender.sendMessage("Given " + ingItem.getAmount() + "x of " + name + " to " + target.getName());
                    target.sendMessage("Received " + ingItem.getAmount() + "x of " + name);
                }

            } else {
                sender.sendMessage("Please state an amount!");
                return false;
            }
        }

        else if(args[0].equalsIgnoreCase("guide")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                p.getInventory().addItem(new MenuBook());
            }
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        final List<String> results = new ArrayList<>();
        if(args.length == 1) {
            if(sender.hasPermission("mechanix.give")) results.add("give");
            results.add("guide");
            return StringUtil.copyPartialMatches(args[0], results, new ArrayList<>());
        }
        else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("give")) Bukkit.getOnlinePlayers().forEach(p -> results.add(p.getName()));
            return StringUtil.copyPartialMatches(args[1], results, new ArrayList<>());
        }
        else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("give")) {
                for (MachineItem item : MachineItem.values())
                    results.add(item.toString().toLowerCase());
                for(IngredientList list : IngredientList.values())
                    results.add(list.toString().toLowerCase());
                return StringUtil.copyPartialMatches(args[2], results, new ArrayList<>());
            }
        }
        return results;
    }
}
