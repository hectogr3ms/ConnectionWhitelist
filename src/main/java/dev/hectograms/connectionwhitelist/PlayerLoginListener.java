package dev.hectograms.connectionwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.net.InetAddress;

public class PlayerLoginListener implements Listener {

    private final ConnectionWhitelist plugin;

    public PlayerLoginListener(ConnectionWhitelist plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final InetAddress addr = event.getRealAddress();
        if (addr == null) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "Unable to determine your connection address.");
            return;
        }

        String playerIP = addr.getHostAddress();

        if (plugin.getConfig().getBoolean("debug", false)) {
            System.out.println("Player " + event.getPlayer().getName() + " is connecting with IP: " + playerIP);
        }

        if (!plugin.isWhitelisted(playerIP)) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', "You are not allowed to join this server."));
        }
    }
}
