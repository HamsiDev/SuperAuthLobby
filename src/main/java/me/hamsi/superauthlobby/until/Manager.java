package me.hamsi.superauthlobby.until;

import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Manager {
    public Plugin plugin;
    FileConfiguration fclset, fcsset, locfc;

    public Manager(Plugin plugin){
        this.plugin = plugin;
        ManagerConfig();
    }


    public void ManagerConfig(){
        File ssettings = new File(plugin.getDataFolder(), "settings.yml");

        if (!ssettings.exists()){
            plugin.saveResource("settings.yml", true);
        }
        this.fcsset = YamlConfiguration.loadConfiguration(ssettings);
        File llocation = new File(plugin.getDataFolder(), "location.yml");
        if (!llocation.exists()){
            plugin.saveResource("location.yml", true);
        }
        this.locfc = YamlConfiguration.loadConfiguration(llocation);

        String lang = fcsset.getString("Language");
        File flang = new File(plugin.getDataFolder(), "language/language_" + lang + ".yml");

        if (!flang.exists()){
            switch (lang){
                case "tr":
                    plugin.saveResource("language/language_tr.yml", true);
                    break;
                case "en":
                    plugin.saveResource("language/language_en.yml", true);
                    break;
                default:
                    try {
                        copyResource(flang, "language/language_en.yml");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
        this.fclset = YamlConfiguration.loadConfiguration(flang);
    }

    public void reloadConfig(){
        ManagerConfig();
    }

    public void saveConfig(){
        File llocation = new File(plugin.getDataFolder(), "location.yml");
        try {
            getLocationConfig().save(llocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getSettingsConfig() {
        if (this.fcsset == null)
            reloadConfig();
        return this.fcsset;
    }
    public FileConfiguration getLocationConfig() {
        if (this.locfc == null)
            reloadConfig();
        return this.locfc;
    }

    public FileConfiguration getMessagesConfig() {
        if (this.fclset == null)
            reloadConfig();
        return this.fclset;
    }

    public void copyResource(final File file, final String resource) throws IOException {

        final InputStream in = plugin.getResource(resource);

        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();

    }
}
