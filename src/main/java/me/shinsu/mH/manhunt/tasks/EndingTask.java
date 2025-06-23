package me.shinsu.mH.manhunt.tasks;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitTask;

public class EndingTask extends TaskTimer{

    private Game game;
    private long time;
    private MH plugin;

    public EndingTask(MH plugin, Game game, long time) {
        super(plugin);
        this.plugin =plugin;
        this.game = game;
        this.time = time;
    }

    @Override
    public void execute() {
        if(time == 0){
            game.getPlayerList().forEach(gamePlayer -> gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL));
            plugin.getManager().removeGame(game);
            game.getGameTask().forEach(BukkitTask::cancel);
            cancel();
        }


        time--;
    }
}
