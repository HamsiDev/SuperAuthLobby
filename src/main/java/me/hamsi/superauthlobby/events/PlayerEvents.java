package me.hamsi.superauthlobby.events;

import me.hamsi.superauthlobby.SuperAuthLobby;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEvents implements Listener {
    private SuperAuthLobby plugin;

    public PlayerEvents(SuperAuthLobby plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void JoinE(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (plugin.getConfigManager().getSettingsConfig().getBoolean("ClearChatOnJoin")){
            for (int i = 0; i < 150; i++) {
                player.sendMessage(" ");
            }
        }

        if (plugin.getConfigManager().getSettingsConfig().getList("DisableDamage").contains(player.getWorld().getName())){
            player.setHealth(20.0);
        }
        if (plugin.getConfigManager().getSettingsConfig().getList("DisableHunger").contains(player.getWorld().getName())){
            player.setFoodLevel(20);
        }

        if (plugin.getConfigManager().getSettingsConfig().getBoolean("FireWorkOnJoin")){
            Firework fw = (Firework)  player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(2);

            if (plugin.getConfigManager().getSettingsConfig().getInt("FireWorkColor") == 1) {
                fwm.addEffect(FireworkEffect.builder().withColor(Color.GREEN).flicker(true).build());
            }
            else if (plugin.getConfigManager().getSettingsConfig().getInt("FireWorkColor") == 2){
                fwm.addEffect(FireworkEffect.builder().withColor(Color.BLUE).flicker(true).build());
            }
            else if (plugin.getConfigManager().getSettingsConfig().getInt("FireWorkColor") == 3){
                fwm.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).build());
            }
            else if (plugin.getConfigManager().getSettingsConfig().getInt("FireWorkColor") == 4){
                fwm.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).flicker(true).build());
            }
            else if (plugin.getConfigManager().getSettingsConfig().getInt("FireWorkColor") == 5){
                fwm.addEffect(FireworkEffect.builder().withColor(Color.BLACK).flicker(true).build());
            }
            else if (plugin.getConfigManager().getSettingsConfig().getInt("FireWorkColor") == 6){
                fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());
            }

            fw.setFireworkMeta(fwm);
            fw.detonate();

            for(int i = 0;i<plugin.getConfigManager().getSettingsConfig().getInt("FireWorkAmmount"); i++){
                Firework fw2 = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                fw2.setFireworkMeta(fwm);
            }

            if (plugin.getConfigManager().getSettingsConfig().getBoolean("AlwaysDay")){
                player.getWorld().setTime(1000);
            }
        }

        if (plugin.getConfigManager().getSettingsConfig().getBoolean("MessageOnJoin")){
            for (String jm : plugin.getConfigManager().getSettingsConfig().getStringList("JoinMessage")){
                jm = jm.replace("{PLAYER}", player.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', jm));
            }

        }

        if (plugin.getConfigManager().getSettingsConfig().getBoolean("VisibleOnJoin")){
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2));
        }

        if (plugin.getConfigManager().getSettingsConfig().getBoolean("CloseJoinMessage")){
            event.setJoinMessage("");
        }

        if (plugin.getConfigManager().getSettingsConfig().getBoolean("TeleportSpawnOnJoin")){
            if (plugin.getConfigManager().getLocationConfig().getString("SpawnLocation.World").isEmpty()) return;
            World world = Bukkit.getWorld((String) plugin.getConfigManager().getLocationConfig().get("SpawnLocation.World"));
            int x = plugin.getConfigManager().getLocationConfig().getInt("SpawnLocation.X");
            int y = plugin.getConfigManager().getLocationConfig().getInt("SpawnLocation.Y");
            int z = plugin.getConfigManager().getLocationConfig().getInt("SpawnLocation.Z");
            Long yaw = plugin.getConfigManager().getLocationConfig().getLong("SpawnLocation.Yaw");
            Long pitch = plugin.getConfigManager().getLocationConfig().getLong("SpawnLocation.Pitch");

            player.teleport(new Location(world, x,y,z, yaw, pitch));
        }
    }

    @EventHandler
    public void LeaveEvent(PlayerQuitEvent event){
        if (plugin.getConfigManager().getSettingsConfig().getBoolean("CloseQuitMessage")){
            event.setQuitMessage("");
        }
    }

    @EventHandler
    public void ChatTalk(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission(plugin.getConfigManager().getSettingsConfig().getString("BypassChatTalk"))) return;
        if (plugin.getConfigManager().getSettingsConfig().getBoolean("CloseChat")){
            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessagesConfig().getString("PermissionError")));
        }
    }

    @EventHandler
    public void AntiFood(FoodLevelChangeEvent event){
        Player player = (Player) event.getEntity();
        if (plugin.getConfigManager().getSettingsConfig().getList("DisableHunger").contains(player.getWorld().getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void AntiEntityDamageEntity(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (player.hasPermission(plugin.getConfigManager().getSettingsConfig().getString("BypassDamagePerm"))) return;

            if (plugin.getConfigManager().getSettingsConfig().getList("DisableDamage").contains(player.getWorld().getName())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ItemDrop(PlayerDropItemEvent event) {
        if (plugin.getConfigManager().getSettingsConfig().getBoolean("DisableDropItem")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void AntiDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (player.hasPermission(plugin.getConfigManager().getSettingsConfig().getString("BypassDamagePerm"))) return;
            if (plugin.getConfigManager().getSettingsConfig().getList("DisableDamage").contains(player.getWorld().getName())){
                event.setCancelled(true);
            }
        }
    }
}
