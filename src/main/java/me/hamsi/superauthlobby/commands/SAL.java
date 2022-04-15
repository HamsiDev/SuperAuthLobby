package me.hamsi.superauthlobby.commands;

import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SAL implements CommandExecutor {

    SuperAuthLobby plugin;

    public SAL(SuperAuthLobby plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp()) {
                Player player = (Player)sender;
                for(String mes : plugin.getConfigManager().getMessagesConfig().getStringList("HelpMessage")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', mes));
                }
            } else {
                Player player = (Player)sender;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessagesConfig().getString("PermissionError")));
            }
        }
        return true;
    }
}
