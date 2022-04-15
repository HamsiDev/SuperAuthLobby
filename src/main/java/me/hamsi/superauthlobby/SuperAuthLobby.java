package me.hamsi.superauthlobby;

import me.hamsi.superauthlobby.commands.Reload;
import me.hamsi.superauthlobby.commands.SAL;
import me.hamsi.superauthlobby.commands.SetSpawn;
import me.hamsi.superauthlobby.commands.HSPAWN;
import me.hamsi.superauthlobby.events.BlockEvents;
import me.hamsi.superauthlobby.events.PlayerEvents;
import me.hamsi.superauthlobby.until.Manager;
import me.hamsi.superauthlobby.until.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperAuthLobby extends JavaPlugin {

    public static Manager manager;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
        setConfigFileManager(new Manager(this));

        this.getCommand("sal-reload").setExecutor(new Reload(this));
        this.getCommand("sal").setExecutor(new SAL(this));
        this.getCommand("sal-spawn").setExecutor(new HSPAWN(this));
        this.getCommand("sal-setspawn").setExecutor(new SetSpawn(this));
        int pluginId = 14910;
        Metrics metrics = new Metrics(this, pluginId);

        System.out.println("----------------");
        System.out.println("SuperAuthLobby Openned");
        System.out.println("Version: 1.0");
        System.out.println("----------------");
    }

    @Override
    public void onDisable() {
        System.out.println("----------------");
        System.out.println("SuperAuthLobby Closed");
        System.out.println("Version: 1.0");
        System.out.println("----------------");
    }

    public Manager getConfigManager(){
        return manager;
    }

    public void setConfigFileManager(Manager configFileManager) {
        SuperAuthLobby.manager = configFileManager;
    }
}
