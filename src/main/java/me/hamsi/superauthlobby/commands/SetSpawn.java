package me.hamsi.superauthlobby.commands;

import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

    SuperAuthLobby plugin;

    public SetSpawn(SuperAuthLobby plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (sender instanceof Player){
            plugin.getConfigManager().getLocationConfig().set("SpawnLocation.World", player.getWorld().getName());
            plugin.getConfigManager().getLocationConfig().set("SpawnLocation.X", player.getLocation().getBlockX());
            plugin.getConfigManager().getLocationConfig().set("SpawnLocation.Y", player.getLocation().getBlockY());
            plugin.getConfigManager().getLocationConfig().set("SpawnLocation.Z", player.getLocation().getBlockZ());
            plugin.getConfigManager().getLocationConfig().set("SpawnLocation.Yaw", player.getLocation().getYaw());
            plugin.getConfigManager().getLocationConfig().set("SpawnLocation.Pitch", player.getLocation().getPitch());
            plugin.getConfigManager().saveConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessagesConfig().getString("SuccesMessage")));
        }
        else {

        }
        return false;
    }
}
