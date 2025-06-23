package me.shinsu.mH.actionItems;

import io.lumine.mythic.core.skills.mechanics.ShootMechanic;
import me.shinsu.mH.MH;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ActionItemHandler implements Listener {

    private MH plugin;
    public ActionItemHandler(MH plugin){
        this.plugin = plugin;
    }



    @EventHandler(priority = EventPriority.MONITOR)
    public void onActionItemInteract(PlayerInteractEvent event)
    {
        if(event.getItem() != null){
            if(isCustomItem(event.getItem()))
            {
                if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                    ActionItemBase item = MH.customItemMap.get(getItemId(event.getItem()));
                    item.handleLeftClick(event.getPlayer(), event.getItem(), event);
                }
                if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                    ActionItemBase item = MH.customItemMap.get(getItemId(event.getItem()));
                    item.handleRightClick(event.getPlayer(), event.getItem(), event);
                }
            }
        }


    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onActionItemDrop(PlayerDropItemEvent event)
    {
        if(isCustomItem(event.getItemDrop().getItemStack())){
            ActionItemBase actionItem = MH.customItemMap.get(getItemId(event.getItemDrop().getItemStack()));
            actionItem.handleActionItemDrop(event.getPlayer(), event.getItemDrop().getItemStack(), event);
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFishEndEvent(PlayerFishEvent event){
        Player p = event.getPlayer();
       if(isCustomItem(p.getInventory().getItemInMainHand())){
            if(event.getState() == PlayerFishEvent.State.IN_GROUND){
                ActionItemBase actionItem = MH.customItemMap.get(getItemId(p.getInventory().getItemInMainHand()));
                actionItem.onFishEndEvent(p, event);
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void  onProjectileHitEvent(ProjectileHitEvent event){
        Projectile projectile = event.getEntity();
        if(event.getEntity().getType().equals(EntityType.FISHING_BOBBER)){
            Player player = (Player) event.getEntity().getShooter();
            if(isCustomItem(player.getItemInHand())){
                ActionItemBase actionItem = MH.customItemMap.get(getItemId(player.getItemInHand()));
                actionItem.onProjectileHitEvent(player, projectile, event);

            }
        }
    }

    private boolean isCustomItem(ItemStack itemStack) {
        return (itemStack.hasItemMeta() && Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer().has(MH.actionItemKey, PersistentDataType.STRING) );
    }

    private String getItemId(ItemStack itemStack) {
        return Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer().get(MH.actionItemKey, PersistentDataType.STRING);
    }
}
