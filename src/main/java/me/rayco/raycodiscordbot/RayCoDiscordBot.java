package me.rayco.raycodiscordbot;

import me.rayco.raycodiscordbot.discordEvents.DisplayMessageEvent;
import me.rayco.raycodiscordbot.discordEvents.ServerInfoEvent;
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

//TODO
//Think about removing discord sleep messages
//Add Server Info Command (Add Prefix)
//Make Player Count command
//Add Config (Auto Generate after first time)
//Add Changeable Tokens and Channel ID (Into Config)
//Make prescence display player count




public final class RayCoDiscordBot extends JavaPlugin implements Listener {
    public static JDA jda;
    private World world;



    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Ray Co. Spigot Bot Loaded");
        try {
            botOnline();
        } catch (Exception e){
            //Enabled Discord API Bot
        }
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                DisplayMessageEvent.displaySpigotMessage("Server Started", jda);
            }
        }); //Send Start Message
        getServer().getPluginManager().registerEvents(this,this);
    }

    public void botOnline() throws Exception{
        this.jda = new JDABuilder(AccountType.BOT)
                .setToken("NjkyMjU1MjM0NTI5MTY1MzIy.Xnr3Lw.1Ug7Ee9uqfFo7frFTzaSRdNcqsA")
                .addEventListeners(new ServerInfoEvent())
                .setAutoReconnect(true).build();
        jda.getPresence().setActivity(Activity.playing("Ray Co. Quarantine Server"));
        System.out.println("Discord API Online");
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        String joinedPlayer = event.getPlayer().getDisplayName();
        String joinPlayerMessage = (joinedPlayer + " Has Logged In");
        DisplayMessageEvent.displaySpigotMessage(joinPlayerMessage, jda);
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        String quitingPlayer = event.getPlayer().getDisplayName();
        String quittingPlayerMessage = (quitingPlayer + " Has Left");
        DisplayMessageEvent.displaySpigotMessage(quittingPlayerMessage, jda);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        String deathMessage = event.getDeathMessage();
        DisplayMessageEvent.displaySpigotMessage(deathMessage, jda);
    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event){
        Player player = event.getPlayer();
        String bedPlayer = event.getPlayer().getDisplayName();
        String botMessage = (bedPlayer + " Is Now In Bed.");
        Bukkit.broadcastMessage(ChatColor.GOLD + botMessage);
        BukkitScheduler scheduler = getServer().getScheduler();

        while (player.isSleeping()){
            scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "It Is Now Morning.");
                    player.getLocation().getWorld().setTime(0);
                }
            }, 100L);
        }


        DisplayMessageEvent.displaySpigotMessage(botMessage, jda);
    }

    @EventHandler
    public void setSpawnPointBed(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getClickedBlock().getType() == Material.BED_BLOCK){
                //event.getPlayer().setBedSpawnLocation(event.getPlayer().getLocation(), true);
                event.getPlayer().setBedSpawnLocation(event.getClickedBlock().getLocation(), true);
                event.getPlayer().sendMessage(ChatColor.GOLD + "Your spawnpoint has been set");
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Ray Co. Discord Bot Unloaded");
    }
}
