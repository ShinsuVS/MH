package me.shinsu.mH.tests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MetaListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
           // event.getPlayer().sendMessage(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().toString());
        }
    }
}
