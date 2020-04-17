package me.rayco.raycodiscordbot;

import me.rayco.raycodiscordbot.discordEvents.DisplayOnDiscord;
import me.rayco.raycodiscordbot.discordEvents.GuildMessageReceived;
import me.rayco.raycodiscordbot.discordEvents.GuildVoiceJoin;
import me.rayco.raycodiscordbot.discordEvents.GuildVoiceLeave;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.awt.Color;

//TODO
//***Think about removing discord sleep messages
//Add Server Info Command (Add Prefix)
//***Make Player Count command
//***Add Config (Auto Generate after first time)
//***Add Changeable Tokens and Channel ID (Into Config)
//Make prescence display player count

public final class RayCoDiscordBot extends JavaPlugin implements Listener {
    public static JDA jda;
    private World world;
    public static String token;
    public static String channelID;
    public static String botPrefix;
    public static int loadMsgCount = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Ray Co. Spigot Bot Loaded");

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        token = getConfig().getString("botToken");
        channelID = getConfig().getString("channelID");
        botPrefix = getConfig().getString("botPrefix");

        try {
            botOnline();
        } catch (Exception e) {
            //Enabled Discord API Bot
        }
        getServer().getPluginManager().registerEvents(this,this);
    }

    public void botOnline() throws Exception{
        this.jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setAutoReconnect(true).build();
        jda.addEventListener(new GuildMessageReceived());
        jda.addEventListener(new GuildVoiceJoin());
        jda.addEventListener(new GuildVoiceLeave());
        jda.getPresence().setActivity(Activity.playing("Ray Co. Quarantine Server"));
        System.out.println("Discord API Online");
    }

    @EventHandler
    public void serverLoaded(WorldLoadEvent event){
        if (loadMsgCount == 0){
            DisplayOnDiscord.displayOnDiscord("Server Started", jda, Color.cyan);
            loadMsgCount = 1;
        }
    }
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        if(getConfig().getBoolean("playerJoinLeaveBroadcast")){
            String joinedPlayer = event.getPlayer().getDisplayName();
            String joinPlayerMessage = (joinedPlayer + " Has Logged In");
            DisplayOnDiscord.displayOnDiscord(joinPlayerMessage, jda, Color.green);
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        if(getConfig().getBoolean("playerJoinLeaveBroadcast")){
            String quitingPlayer = event.getPlayer().getDisplayName();
            String quittingPlayerMessage = (quitingPlayer + " Has Left");
            DisplayOnDiscord.displayOnDiscord(quittingPlayerMessage, jda, Color.red);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(getConfig().getBoolean("playerDeathBroadcast")){
            String deathMessage = event.getDeathMessage();
            DisplayOnDiscord.displayOnDiscord(deathMessage, jda, Color.red);
        }
    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event){
        if (getConfig().getBoolean("sleepFix")){
            Player player = event.getPlayer();
            String bedPlayer = event.getPlayer().getDisplayName();
            String botMessage = (bedPlayer + " Is Now In Bed.");
            Bukkit.broadcastMessage(ChatColor.GOLD + botMessage);
            BukkitScheduler scheduler = getServer().getScheduler();

            scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    if(player.isSleeping()){
                        Bukkit.broadcastMessage(ChatColor.GOLD + "It Is Now Morning.");
                        player.setHealth(50.0);
                        player.setFoodLevel(20);
                        player.sendMessage(ChatColor.GOLD + "Health and Food Has Been Restored");
                        player.getLocation().getWorld().setTime(0); //Do everything before this
                    }
                }
            }, 100L);
        }
    }

    @EventHandler
    public void setSpawnPointBed(PlayerInteractEvent event){
        if(getConfig().getBoolean("spawnpointFix")){
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getClickedBlock().getType() == Material.BED_BLOCK){
                    event.getPlayer().setBedSpawnLocation(event.getClickedBlock().getLocation(), true);
                    event.getPlayer().sendMessage(ChatColor.GOLD + "Your spawnpoint has been set");
                }
            }
        }
    }

    @Override
    public void onDisable() {
        DisplayOnDiscord.displayOnDiscord("Server Shutdown", jda, Color.cyan);
        System.out.println("Ray Co. Discord Bot Unloaded");
    }
}
