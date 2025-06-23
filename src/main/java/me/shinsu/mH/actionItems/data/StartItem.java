package me.shinsu.mH.actionItems.data;

import me.shinsu.mH.MH;
import me.shinsu.mH.actionItems.ActionItemBase;
import me.shinsu.mH.manhunt.events.GameStartEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class StartItem extends ActionItemBase {

    private  MH plugin;

   public StartItem(MH plugin){
       this.plugin = plugin;
   }
    @Override
    public String getName() {
        return "&3Выброси для начала игры";
    }



    @Override
    public Material getMaterial() {
        return Material.NETHER_STAR;
    }

    @Override
    public List<String> getLore() {
        return List.of();
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public ShapedRecipe getRecipe() {
        return null;
    }

    @Override
    public boolean isCustomItem() {
        return false;
    }


    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleEntityDamage(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleActionItemDrop(Player player, ItemStack itemStack, PlayerDropItemEvent event) {
        plugin.getManager().getPlayerGame(player).getSpeedRunners().forEach(gamePlayer -> gamePlayer.getPlayer().getInventory().clear());
        event.getItemDrop().remove();
        plugin.getServer().getPluginManager().callEvent(new GameStartEvent(plugin.getManager().getPlayerGame(player)));
    }

    @Override
    public void onProjectileHitEvent(Player player, Projectile projectile, ProjectileHitEvent event) {

    }

    @Override
    public void onFishEndEvent(Player player, PlayerFishEvent event) {

    }


}
