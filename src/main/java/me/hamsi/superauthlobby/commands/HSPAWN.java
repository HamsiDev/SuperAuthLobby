package me.hamsi.superauthlobby.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HSPAWN implements CommandExecutor {

    SuperAuthLobby plugin;

    public HSPAWN(SuperAuthLobby plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            World world = Bukkit.getWorld((String) plugin.getConfigManager().getLocationConfig().get("SpawnLocation.World"));
            int x = plugin.getConfigManager().getLocationConfig().getInt("SpawnLocation.X");
            int y = plugin.getConfigManager().getLocationConfig().getInt("SpawnLocation.Y");
            int z = plugin.getConfigManager().getLocationConfig().getInt("SpawnLocation.Z");
            Float yaw = (Float) plugin.getConfigManager().getLocationConfig().get("SpawnLocation.Yaw");
            Float pitch = (Float) plugin.getConfigManager().getLocationConfig().get("SpawnLocation.Pitch");

            player.teleport(new Location(world, x,y,z, yaw, pitch));
        }

        return false;
    }
}
