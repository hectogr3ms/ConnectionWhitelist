package dev.hectograms.connectionwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ConnectionWhitelist extends JavaPlugin {

    private List<String> whitelistedIPs = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadWhitelistFromConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ServerListPingListener(this), this);

        this.getCommand("connectionwhitelist").setExecutor(new ConnectionWhitelistCommand(this));
        this.getCommand("cwl").setExecutor(new ConnectionWhitelistCommand(this));
    }

    public boolean isWhitelisted(String ip) {
        return whitelistedIPs != null && whitelistedIPs.contains(ip);
    }


    public void addIPToWhitelist(String ip) {
        if (whitelistedIPs == null) {
            whitelistedIPs = new ArrayList<>();
        }
        if (!whitelistedIPs.contains(ip)) {
            whitelistedIPs.add(ip);
            getConfig().set("whitelistedIPs", whitelistedIPs);
            saveConfig();
            reloadConfig();
        }
    }

    public void removeIPFromWhitelist(String ip) {
        if (whitelistedIPs != null && whitelistedIPs.contains(ip)) {
            whitelistedIPs.remove(ip);
            getConfig().set("whitelistedIPs", whitelistedIPs);
            saveConfig();
            reloadConfig();
        }
    }

    public List<String> getWhitelistedIPs() {
        return whitelistedIPs;
    }

    public void loadWhitelistFromConfig() {
        FileConfiguration config = getConfig();
        whitelistedIPs = config.getStringList("whitelistedIPs");
        if (whitelistedIPs == null) {
            whitelistedIPs = new ArrayList<>();
        }
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        loadWhitelistFromConfig();
    }
}
