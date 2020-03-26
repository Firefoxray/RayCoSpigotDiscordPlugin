package me.rayco.raycodiscordbot.discordEvents;

import me.rayco.raycodiscordbot.RayCoDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.Color;

public class GuildMessageReceived extends ListenerAdapter {

    public static JDA jda;
    String botPrefix = RayCoDiscordBot.botPrefix;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        jda = RayCoDiscordBot.jda;

        String messageSent = event.getMessage().getContentRaw();
        String playerList = "";

        if (messageSent.equalsIgnoreCase(botPrefix + "PlayerList")){
            int onlinePlayerCount = Bukkit.getServer().getOnlinePlayers().size(); //Gets Amount of Players
            String onlinePlayers = "Online Players: " + onlinePlayerCount;
            DisplayOnDiscord.displayOnDiscord(onlinePlayers, jda, Color.cyan);

            if (onlinePlayerCount != 0){
                for (Player player : Bukkit.getOnlinePlayers()){
                    playerList = playerList + player.getDisplayName() + ", ";
                }
            }
            if (onlinePlayerCount == 0){
                playerList = "No Active Players";
            }
            DisplayOnDiscord.displayOnDiscord(playerList, jda, Color.cyan);
            playerList = "";
        }

    }
}
