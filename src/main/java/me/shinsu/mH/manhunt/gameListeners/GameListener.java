package me.shinsu.mH.manhunt.gameListeners;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.GamePlayerType;
import me.shinsu.mH.manhunt.GameStage;
import me.shinsu.mH.manhunt.events.GameCreateEvent;
import me.shinsu.mH.manhunt.events.GameEndEvent;
import me.shinsu.mH.manhunt.events.GameStartEvent;
import me.shinsu.mH.manhunt.tasks.EndingTask;
import me.shinsu.mH.manhunt.tasks.StartingTask;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;


public class GameListener implements Listener {

    private MH plugin;

    public GameListener(MH plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameStart(GameStartEvent event)
    {
        if(event.getGame().isTimerEnabled()){
            new StartingTask(plugin, event.getGame(), event.getGame().getTimer()).start();
        }
        else {
            event.getGame().getPlayerList().forEach(gamePlayer -> gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL));
            event.getGame().getPlayerList().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(Colorize.ColorString("&aИгра началась.")));
            event.getGame().getHunters().forEach(gamePlayer -> gamePlayer.getPlayer().getInventory().addItem(MH.customItemMap.get("compass").getItem(1)));
            event.getGame().setStage(GameStage.INGAME);
        }

    }

    @EventHandler
    public void onGameEndEvent(GameEndEvent event){
        event.getGame().setStage(GameStage.ENDING);
        event.getGame().getPlayerList().forEach(gamePlayer -> {
            if(event.getWinner().equals(GamePlayerType.HUNTER)){
                gamePlayer.getPlayer().sendTitle(Colorize.ColorString("&4&lВЫИГРАЛИ ОХОТНИКИ"),Colorize.ColorString("&9&lОНИ СМОГЛИ ЗАЩИТИТЬ ДРАКОНА"));
            }
            else {
                gamePlayer.getPlayer().sendTitle(Colorize.ColorString("&a&lВЫИГРАЛИ СПИДРАНЕРЫ"),Colorize.ColorString("&9&lОНИ СМОГЛИ СПАСТИ МИР"));

            }
        });

         new EndingTask(plugin, event.getGame(), 10).start();
    }

    @EventHandler
    public void onGameCreate(GameCreateEvent event){
        event.getSpeedRunners().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(Colorize.ColorString("&aТы был выбран Спидранером.")));
        event.getHunters().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(Colorize.ColorString("&cТы был выбран Охотником")));


        event.getSpeedRunners().forEach(gamePlayer -> gamePlayer.getPlayer().getInventory().addItem(MH.customItemMap.get("startitem").getItem(1)));
    }

}
