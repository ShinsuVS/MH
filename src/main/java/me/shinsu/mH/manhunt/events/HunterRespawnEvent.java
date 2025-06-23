package me.shinsu.mH.manhunt.events;


import lombok.Getter;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GamePlayer;
import me.shinsu.mH.manhunt.GamePlayerType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HunterRespawnEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private Game game;
    private GamePlayer gamePlayer;

    public HunterRespawnEvent(Game game, GamePlayer hunter){
        this.game = game;
        this.gamePlayer = hunter;
    }


    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
    public @NotNull static HandlerList getHandlerList() {
        return handlerList;
    }

    public GamePlayer getHunter() {
        return gamePlayer;
    }
}
