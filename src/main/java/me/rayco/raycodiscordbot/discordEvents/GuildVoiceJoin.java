package me.rayco.raycodiscordbot.discordEvents;

import me.rayco.raycodiscordbot.RayCoDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GuildVoiceJoin extends ListenerAdapter {

    public static JDA jda;
    public static User joinedUser;

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
        jda = RayCoDiscordBot.jda; //Puts bot in class
        joinedUser = event.getMember().getUser(); //gets user that joined
        String userString = joinedUser.getName(); //gets name of use that joined
                                                    // both could be combined into one

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"); //sets date pattern
        LocalDateTime now = LocalDateTime.now();//sets date

        String allTogetherNow = userString + " has joined the call at " + dtf.format(now); //Put together in one string

        DisplayOnDiscord.displayOnDiscord(allTogetherNow, jda, Color.green); //Send to bot to display ond discord(custom method)
    }
}

