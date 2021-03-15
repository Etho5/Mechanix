package com.etho5.mechanix.listeners;

import com.etho5.mechanix.machines.Machine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

public class AsyncPlayerChat implements Listener {

    public static Map<Player, Machine> addingPlayers = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        if (addingPlayers.containsKey(p)) {
            e.setCancelled(true);
            addingPlayers.get(p).addAllowedPlayers(Bukkit.getPlayer(e.getMessage()).getUniqueId());
            addingPlayers.get(p).getOwnerAsPlayer().getPlayer().sendMessage(ChatColor.GREEN + "Added " + ChatColor.AQUA + e.getMessage() + ChatColor.GREEN + " to your machine!");
            addingPlayers.remove(p);
        }
    }
}
