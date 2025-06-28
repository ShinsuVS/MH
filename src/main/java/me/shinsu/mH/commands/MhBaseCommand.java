package me.shinsu.mH.commands;

import me.shinsu.mH.MH;
import me.shinsu.mH.commands.data.CreateCommand;
import me.shinsu.mH.commands.data.GetCompassCommand;
import me.shinsu.mH.commands.data.GetItemsCommand;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;

public class MhBaseCommand implements CommandExecutor {

    private final MH plugin;
    private ArrayList<SubCommand<? extends CommandSender>> subCommands = new java.util.ArrayList<>();


    public MhBaseCommand(MH plugin){
        this.plugin = plugin;
        subCommands.add(new CreateCommand(plugin));
        subCommands.add(new GetItemsCommand(plugin));
        subCommands.add(new GetCompassCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Colorize.ColorString("&bПривет! Вот список доступных команд: "));
            subCommands.forEach(sub -> sender.sendMessage(Colorize.ColorString("&c/mh ") + Colorize.ColorString("&a" + sub.getName())  ));
            return true;
        }

        String subStr = args[0];
        SubCommand<? extends CommandSender> subCmd = subCommands.stream().filter(sub -> sub.getName().equalsIgnoreCase(subStr)).findAny().orElse(null);

        if(subCmd != null) {
            ParameterizedType type = (ParameterizedType) subCmd.getClass().getGenericSuperclass();
            if(type.getActualTypeArguments()[0] == Player.class) {
                SubCommand<Player> subPlayer = (SubCommand<Player>) subCmd;
                if(!(sender instanceof Player)) {
                    sender.sendMessage( Colorize.ColorString("&4" + "This command only for players ->  /mh " + subCmd.getName() ));
                    return true;
                }
                subPlayer.execute((Player) sender, Arrays.copyOfRange(args, 1, args.length));
                return true;
            }
            SubCommand<CommandSender> subSender = (SubCommand<CommandSender>) subCmd;
            subSender.execute(sender, Arrays.copyOfRange(args, 1, args.length));
            return true;
        }

        sender.sendMessage("Неправильная команда!");
        return false;
    }
}
