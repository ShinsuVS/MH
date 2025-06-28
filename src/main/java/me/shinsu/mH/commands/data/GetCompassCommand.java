package me.shinsu.mH.commands.data;

import me.shinsu.mH.MH;
import me.shinsu.mH.commands.SubCommand;
import me.shinsu.mH.utils.inventory.InventoryUtils;
import org.bukkit.entity.Player;

public class GetCompassCommand extends SubCommand<Player> {

    public GetCompassCommand(MH plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player sender, String[] args) {
       if(InventoryUtils.canAddItemToInventory(sender, MH.customItemMap.get("compass").getItem(1))){
            sender.getInventory().addItem(MH.customItemMap.get("compass").getItem(1));
       }
       else
           sender.getLocation().getWorld().dropItemNaturally(sender.getLocation(), MH.customItemMap.get("compass").getItem(1));
    }

    @Override
    public String getName() {
        return "getCompass";
    }
}
