package me.rayco.raycodiscordbot.discordEvents;

import me.rayco.raycodiscordbot.RayCoDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GuildVoiceLeave extends ListenerAdapter {

    public static JDA jda;
    public static User leavingUser;

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event){
        jda = RayCoDiscordBot.jda;
        leavingUser = event.getMember().getUser();
        String userString = leavingUser.getName();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String allTogetherNow = userString + " has left the call at " + dtf.format(now);

        DisplayOnDiscord.displayOnDiscord(allTogetherNow, jda, Color.red);

    }
}

