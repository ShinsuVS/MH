package me.shinsu.mH.commands.data;

import me.shinsu.mH.MH;
import me.shinsu.mH.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;



public class GetItemsCommand extends SubCommand<Player> {

    public GetItemsCommand(MH plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player sender, String[] args) {
         getCustomItemMapKeysAsList().forEach(k -> {
             sender.getInventory().addItem(MH.customItemMap.get(k).getItem(1));
         });
    }

    public static List<String> getCustomItemMapKeysAsList() {
        return new ArrayList<>(MH.customItemMap.keySet());
    }


    @Override
    public String getName() {
        return "getItems";
    }
}
