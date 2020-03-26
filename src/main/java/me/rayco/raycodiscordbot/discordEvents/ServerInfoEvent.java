package me.rayco.raycodiscordbot.discordEvents;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ServerInfoEvent extends ListenerAdapter {


    public void onMessageRecieved(MessageReceivedEvent event){
        //Guild guild = event.getGuild();
        //TextChannel mineChannel = guild.getTextChannelsByName("minecraftserver", true).get(0);
        //mineChannel.sendMessage("Tester");

        //System.out.println("Guild: " + guild);
        //System.out.println("Channel: " + mineChannel);

        event.getAuthor();


        /**
        String messageSent = event.getMessage().getContentRaw();
        System.out.println("Guild Message Recieved");
        if(messageSent.equalsIgnoreCase("get channel")){
            event.getChannel().sendMessage("Correct Channel Set").queue();
            System.out.println("Get Channel Entered");
        }
         **/
    }


}
