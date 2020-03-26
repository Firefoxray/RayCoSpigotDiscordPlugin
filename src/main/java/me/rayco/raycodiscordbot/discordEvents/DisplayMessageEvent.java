package me.rayco.raycodiscordbot.discordEvents;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class DisplayMessageEvent {
    public static JDA api;
    public static TextChannel serverChannel;

    public static void displaySpigotMessage(String message, JDA jda){
        api = jda;
        serverChannel = api.getTextChannelById("692117341021929522");
        System.out.println(serverChannel);
        if ((serverChannel != null) && (message != null)){
            serverChannel.sendMessage(message).queue();
        }
    }
}
