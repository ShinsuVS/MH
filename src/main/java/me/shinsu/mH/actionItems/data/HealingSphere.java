package me.shinsu.mH.actionItems.data;

import lombok.Getter;
import lombok.val;
import me.shinsu.mH.MH;
import me.shinsu.mH.actionEvents.sphere.Sphere;
import me.shinsu.mH.actionEvents.sphere.SphereEffectType;
import me.shinsu.mH.actionItems.ActionItemBase;
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

public class HealingSphere extends ActionItemBase {
    @Getter
    private MH plugin;

    public HealingSphere(MH plugin){ this.plugin = plugin;}
    @Override
    public String getName() {
        return "&aСфера исцеления";
    }

    @Override
    public Material getMaterial() {
        return Material.BOOK;
    }

    @Override
    public List<String> getLore() {
        return List.of(
                "",
                "&8Древний артефакт &aисцеления",
                "&cПКМ &8для установки сферы исцеления.");
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.getItem(1));
        recipe.shape("X  ", "Y  ","   ");
        recipe.setIngredient('X', Material.BOOK);
        recipe.setIngredient('Y', Material.GOLDEN_APPLE);
        return recipe;
    }

    @Override
    public boolean isCustomItem() {
        return true;
    }


    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        Sphere sphere = new Sphere(plugin, 8, player.getLocation(), SphereEffectType.HEAL, 20);

        val manager = getPlugin().getManager();
        val sphereManager = manager.getActivePlayerGame(player).getSphereManager();
        sphere.Show();
        sphereManager.addSphere(sphere);
        player.getInventory().removeItem(getItem(1));
    }

    @Override
    public void handleEntityDamage(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleActionItemDrop(Player player, ItemStack itemStack, PlayerDropItemEvent event) {

    }

    @Override
    public void onProjectileHitEvent(Player player, Projectile projectile, ProjectileHitEvent event) {

    }

    @Override
    public void onFishEndEvent(Player player, PlayerFishEvent event) {

    }
}
