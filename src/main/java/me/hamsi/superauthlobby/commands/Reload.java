package me.hamsi.superauthlobby.commands;

import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {

    SuperAuthLobby plugin;

    public Reload(SuperAuthLobby plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission(plugin.getConfigManager().getSettingsConfig().getString("ReloadPerm"))){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessagesConfig().getString("Reload")));
                plugin.getConfigManager().reloadConfig();
            }
            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessagesConfig().getString("PermissionError")));
            }
        }
        else {
            plugin.getConfigManager().getMessagesConfig().getString("Reload");
            plugin.getConfigManager().reloadConfig();
        }
        return true;
    }
}
