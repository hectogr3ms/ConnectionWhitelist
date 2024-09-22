package dev.hectograms.connectionwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ConnectionWhitelistCommand implements CommandExecutor {

    private final ConnectionWhitelist plugin;

    public ConnectionWhitelistCommand(ConnectionWhitelist plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("cwl.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /connectionwhitelist [add/remove/reload] [ip]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /connectionwhitelist add [ip]");
                    return true;
                }
                String ipToAdd = args[1];
                plugin.addIPToWhitelist(ipToAdd);
                sender.sendMessage(ChatColor.GREEN + "IP " + ipToAdd + " added to whitelist.");
                plugin.reloadConfig();
                break;

            case "remove":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /connectionwhitelist remove [ip]");
                    return true;
                }
                String ipToRemove = args[1];
                plugin.removeIPFromWhitelist(ipToRemove);
                sender.sendMessage(ChatColor.GREEN + "IP " + ipToRemove + " removed from whitelist.");
                plugin.reloadConfig();
                break;

            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
                break;

            case "list":
                List<String> whitelistedIPs = plugin.getWhitelistedIPs();
                if (whitelistedIPs.isEmpty()) {
                    sender.sendMessage(ChatColor.YELLOW + "No IPs are currently whitelisted.");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "Whitelisted IPs:");
                    for (String ip : whitelistedIPs) {
                        sender.sendMessage(ChatColor.YELLOW + "- " + ip);
                    }
                }
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Usage: /connectionwhitelist [add/remove/reload] [ip]");
                break;
        }

        return true;
    }
}