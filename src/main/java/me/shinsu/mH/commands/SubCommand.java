package me.shinsu.mH.commands;

import me.shinsu.mH.MH;
import org.bukkit.command.CommandSender;

public abstract class SubCommand<T extends CommandSender> {

    private MH plugin;

    public SubCommand(MH plugin){
        this.plugin =  plugin;
    }
    public abstract void execute(T sender, String[] args);

    public abstract String getName();

    public MH getPlugin() {
        return plugin;
    }
}
