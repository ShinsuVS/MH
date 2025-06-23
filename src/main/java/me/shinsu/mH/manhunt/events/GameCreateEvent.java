package me.shinsu.mH.manhunt.events;

import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GamePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameCreateEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private final Game game;

    public GameCreateEvent(Game game) {
        this.game = game;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }


    public @NotNull static HandlerList getHandlerList() {
        return handlerList;
    }

    public Game getGame() {
        return game;
    }
    public List<GamePlayer> getSpeedRunners(){
        return game.getSpeedRunners();
    }
    public List<GamePlayer> getHunters(){
        return game.getHunters();
    }

    @Override
    public @NotNull  HandlerList getHandlers() {
        return handlerList;
    }
}
