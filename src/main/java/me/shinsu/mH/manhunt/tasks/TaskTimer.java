package me.shinsu.mH.manhunt.tasks;

import lombok.Getter;
import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import org.bukkit.scheduler.BukkitTask;

public abstract class TaskTimer {

    @Getter
    private final MH plugin;

    @Getter
    private long delay = 20;
    private BukkitTask task;


    public TaskTimer(MH plugin) {
        this.plugin = plugin;
    }
    public TaskTimer(MH plugin , long delay) {
        this.plugin = plugin;
        this.delay = delay;
    }

    public abstract void execute();

    public void start() {
        task = plugin.getServer().getScheduler().runTaskTimer(plugin , this::execute, delay , delay);
    }

    protected void cancel() {
        plugin.getServer().getScheduler().cancelTask(task.getTaskId());
    }

    public boolean isStarted() {
        return task != null;
    }

}
