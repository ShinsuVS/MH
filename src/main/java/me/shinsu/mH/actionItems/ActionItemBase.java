package me.shinsu.mH.actionItems;

import io.papermc.paper.datacomponent.item.CustomModelData;
import me.shinsu.mH.MH;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class ActionItemBase {


    public abstract String getName();

    public abstract Material getMaterial();

    public abstract List<String> getLore();

    public abstract boolean isGlowing();

    public abstract ShapedRecipe getRecipe();

    public abstract boolean isCustomItem();

    public abstract void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public  abstract void handleEntityDamage(Player player, ItemStack itemStack, EntityDamageByEntityEvent event);

    public abstract  void handleActionItemDrop(Player player, ItemStack itemStack, PlayerDropItemEvent event);

    public abstract void onProjectileHitEvent(Player player, Projectile projectile, ProjectileHitEvent event);

    public abstract void onFishEndEvent(Player player, PlayerFishEvent event);

    public String getId() {
        return getClass().getSimpleName().toLowerCase();
    }

    public ItemStack getItem(int amount){

        ItemStack itemStack = new ItemStack(getMaterial(), amount);



            ItemMeta itemMeta = itemStack.getItemMeta();
            if (isGlowing()){
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.addEnchant(Enchantment.FORTUNE, 1, true);
            }
            if(isCustomItem()){
                CustomModelDataComponent component  = itemMeta.getCustomModelDataComponent();
                component.setStrings(List.of(getId()));
                itemMeta.setCustomModelDataComponent(component);
            }



            PersistentDataContainer container = itemMeta.getPersistentDataContainer();




            itemMeta.setDisplayName(Colorize.ColorString(getName()));
            List<String> lore = new ArrayList<>();
            getLore().forEach(l-> lore.add(Colorize.ColorString(l)));
            itemMeta.setLore(lore);
            container.set(MH.actionItemKey, PersistentDataType.STRING, getId());
            itemStack.setItemMeta(itemMeta);







        return itemStack;
    }
}
