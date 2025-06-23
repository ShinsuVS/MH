package me.shinsu.mH.manhunt.events;

import lombok.Getter;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GamePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpeedRunnerDeathEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    @Getter
    private Game game;
    @Getter
    private GamePlayer speedRunner;

    public SpeedRunnerDeathEvent(Game game, GamePlayer speedRunner){
        this.game = game;
        this.speedRunner = speedRunner;
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
}
