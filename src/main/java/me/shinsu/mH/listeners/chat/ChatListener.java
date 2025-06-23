package me.shinsu.mH.listeners.chat;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.GamePlayerType;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private MH plugin;

    public ChatListener(MH plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
            if(plugin.getManager().getActivePlayerGame(event.getPlayer()) == null){
                String message = Colorize.ColorString("&8[NONE] "  +  event.getPlayer().getDisplayName() + " : &7" + event.getMessage());
                sendMessage(message);
            }
            if(plugin.getManager().isGameActive(event.getPlayer()))
            {
                GamePlayerType type = plugin.getManager().getActivePlayerGame(event.getPlayer()).getGamePlayer(event.getPlayer()).getGamePlayerType();
                if(type.equals(GamePlayerType.SPEEDRUNNER)){
                    String message = Colorize.ColorString("&2[SPEEDRUNNER] "  +  event.getPlayer().getDisplayName() + " : &a" + event.getMessage());
                    sendMessage(message);
                }
                if(type.equals(GamePlayerType.HUNTER)){
                    String message = Colorize.ColorString("&4[HUNTER] "  +  event.getPlayer().getDisplayName() + " : &c" + event.getMessage());
                    sendMessage(message);
                }
            }



            event.setCancelled(true);
    }


    public void sendMessage(String message){
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
    }
}
