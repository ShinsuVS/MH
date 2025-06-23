package me.shinsu.mH.manhunt.gameListeners;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.GamePlayerType;
import me.shinsu.mH.manhunt.events.GameEndEvent;
import me.shinsu.mH.manhunt.events.HunterRespawnEvent;
import me.shinsu.mH.manhunt.events.SpeedRunnerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GamePlayersListener implements Listener {



    @EventHandler
    public void onSpeedRunnerDeathEvent(SpeedRunnerDeathEvent event){
        event.getSpeedRunner().getPlayer().setGameMode(GameMode.SPECTATOR);
        event.getSpeedRunner().setGamePlayerType(GamePlayerType.SPECTATOR);

        if(event.getGame().getSpeedRunners().isEmpty()){
            Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(event.getGame(), GamePlayerType.HUNTER));
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onHunterDeathEvent(HunterRespawnEvent event){
        event.getHunter().getPlayer().getInventory().setItem(0 , MH.customItemMap.get("compass").getItem(1));
        event.setCancelled(true);
    }
}
