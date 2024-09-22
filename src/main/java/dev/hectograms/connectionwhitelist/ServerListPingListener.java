package dev.hectograms.connectionwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.net.InetAddress;

public class ServerListPingListener implements Listener {

    private final ConnectionWhitelist plugin;

    public ServerListPingListener(ConnectionWhitelist plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (plugin.getConfig().getBoolean("DisableServerPinging")) {
            event.setMotd(null);
            event.setMaxPlayers(0);
            return;
        }

        InetAddress addr = event.getAddress();
        String playerIP = addr.getHostAddress();

        if (plugin.isWhitelisted(playerIP)) {
            event.setMaxPlayers(0);
            event.setMotd("");
        }
    }
}