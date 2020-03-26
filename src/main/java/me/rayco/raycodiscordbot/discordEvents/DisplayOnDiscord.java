package me.rayco.raycodiscordbot.discordEvents;
import me.rayco.raycodiscordbot.RayCoDiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class DisplayOnDiscord {
    public static JDA api;
    public static TextChannel serverChannel;
    public static String channelID;

    public static void displayOnDiscord(String message, JDA jda, Color color){
        api = jda;
        channelID = RayCoDiscordBot.channelID;
        serverChannel = api.getTextChannelById(channelID);
        EmbedBuilder def = new EmbedBuilder();

        if ((serverChannel != null) && (message != null)){
            def.setColor(color);
            def.setDescription(message);
            serverChannel.sendMessage(def.build()).queue();
        }
    }
}
