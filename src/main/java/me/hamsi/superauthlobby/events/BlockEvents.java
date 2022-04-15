package me.hamsi.superauthlobby.events;

import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class BlockEvents implements Listener {

    private SuperAuthLobby plugin;

    public BlockEvents(SuperAuthLobby plugin){
        this.plugin = plugin;
    }

    /*@EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage((String) plugin.getConfigManager().getMessagesConfig().get("Welcome"));
    }*/

    @EventHandler
    public void HBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission(plugin.getConfigManager().getSettingsConfig().getString("BypassBlockBreakPerm"))) return;
        if (plugin.getConfigManager().getSettingsConfig().getList("DisableBlockBreak").contains(player.getWorld().getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void HBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission(plugin.getConfigManager().getSettingsConfig().getString("BypassBlockPlacePerm"))) return;
        if (plugin.getConfigManager().getSettingsConfig().getList("DisableBlockPlace").contains(player.getWorld().getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void NoWeather(WeatherChangeEvent event){
        if (plugin.getConfigManager().getSettingsConfig().getBoolean("DisableWeather")){
            event.setCancelled(true);
        }
    }
}
