package me.shinsu.mH.actionItems.data;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import lombok.val;
import me.shinsu.mH.MH;
import me.shinsu.mH.actionItems.ActionItemBase;
import me.shinsu.mH.utils.Colorize;
import me.shinsu.mH.utils.items.ItemEnchanter;
import me.shinsu.mH.utils.items.ItemGenerator;
import me.shinsu.mH.utils.potions.PotionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LootingItem extends ActionItemBase {

    private MH plugin;

    private Map<Integer, ItemStack> loot = new HashMap<>();

    public LootingItem(MH plugin){
        this.plugin = plugin;
        //
        ItemGenerator.addItem(new ItemStack(Material.COBBLESTONE),64,10 );
        ItemGenerator.addItem(new ItemStack(Material.RED_BED), 2,5,5);
        ItemGenerator.addItem(new ItemStack(Material.DIAMOND_PICKAXE), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.NETHERITE_PICKAXE), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.DIAMOND_AXE), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.NETHERITE_AXE), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.SHEARS), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.DIAMOND_HELMET), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.DIAMOND_CHESTPLATE), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.DIAMOND_LEGGINGS), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.DIAMOND_BOOTS), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.NETHERITE_HELMET), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.NETHERITE_CHESTPLATE), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.NETHERITE_LEGGINGS), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.NETHERITE_BOOTS), 1, 1);
        ItemGenerator.addItem(new ItemStack(Material.OBSIDIAN), 1,3, 5);
        List<ItemStack> tools = Arrays.asList(
                new ItemStack(Material.ELYTRA,1),
                new ItemStack(Material.FIREWORK_ROCKET,64)
        );
        ItemGenerator.addItems(tools, 10);
        ItemGenerator.addItem(PotionUtils.getPotion(PotionEffectType.FIRE_RESISTANCE, 90, 5), 1, 2);
        ItemGenerator.addItem(PotionUtils.getPotion(PotionEffectType.JUMP_BOOST, 90, 5), 1, 2);
        ItemGenerator.addItem(new ItemStack(Material.TNT), 6,8,10);
        ItemGenerator.addItem(new ItemStack(Material.TOTEM_OF_UNDYING), 1,2);
        List<ItemStack> tools1 = Arrays.asList(
                new ItemStack(Material.END_CRYSTAL,2),
                new ItemStack(Material.OBSIDIAN,2)
        );
        ItemGenerator.addItems( tools1,2);
        ItemGenerator.addItem(ItemEnchanter.of(Material.STICK, 1, Enchantment.KNOCKBACK, 255), 1, 10);



    }

    @Override
    public String getName() {
        return "&aМагический лутбокс";
    }

    @Override
    public Material getMaterial() {
        return Material.ECHO_SHARD;
    }

    @Override
    public List<String> getLore() {
        return List.of(
                "",
                "&1ПКМ для получения редких предметов",
                ""

        );

    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public ShapedRecipe getRecipe() {
        return null;
    }

    @Override
    public boolean isCustomItem() {
        return true;
    }


    @Override
    public void handleLeftClick(Player player, org.bukkit.inventory.ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        // Проверяем, что у игрока есть хотя бы 1 предмет для удаления
        if (!player.getInventory().containsAtLeast(itemStack, 1)) {
            player.sendMessage("У вас нет этого предмета!");
            return;
        }

        // Получаем дроп
        List<ItemStack> drops = ItemGenerator.getRandomDrop();

        // Проверяем и выдаём каждый предмет из дропа
        for (ItemStack drop : drops) {
            if (canAddItemToInventory(player, drop)) {
                player.getInventory().addItem(drop);
            }
            else {
                player.getWorld().dropItemNaturally(player.getLocation(), drop);
            }
        }

        // Удаляем ровно 1 предмет (корректная работа с NBT и метаданными)
        removeOneItem(player, itemStack);
    }

    @Override
    public void handleEntityDamage(Player player, org.bukkit.inventory.ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleActionItemDrop(Player player, org.bukkit.inventory.ItemStack itemStack, PlayerDropItemEvent event) {

    }

    @Override
    public void onProjectileHitEvent(Player player, Projectile projectile, ProjectileHitEvent event) {

    }

    @Override
    public void onFishEndEvent(Player player, PlayerFishEvent event) {

    }

    public boolean canAddItemToInventory(Player player, ItemStack itemToAdd) {
        Inventory inventory = player.getInventory();
        int remaining = itemToAdd.getAmount();

        // Слоты которые нужно игнорировать (броня и вторая рука)
        Set<Integer> ignoredSlots = new HashSet<>(Arrays.asList(36, 37, 38, 39, 40));

        // Проверка стакающихся предметов
        if (itemToAdd.getMaxStackSize() > 1) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (ignoredSlots.contains(i)) continue;

                ItemStack item = inventory.getItem(i);
                if (item != null && item.isSimilar(itemToAdd)) {
                    int freeSpace = item.getMaxStackSize() - item.getAmount();
                    remaining -= Math.min(freeSpace, remaining);
                    if (remaining <= 0) return true;
                }
            }
        }

        // Проверка свободных слотов
        int emptySlots = 0;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (ignoredSlots.contains(i)) continue;

            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                emptySlots++;
            }
        }

        int neededSlots = (int) Math.ceil((double) remaining / itemToAdd.getMaxStackSize());
        return emptySlots >= neededSlots;
    }
    // Метод для удаления 1 предмета с учётом метаданных
    private void removeOneItem(Player player, ItemStack itemToRemove) {
        ItemStack oneItem = itemToRemove.clone();
        oneItem.setAmount(1);

        // Удаление через HashMap (лучший способ для Bukkit)
        HashMap<Integer, ItemStack> notRemoved = player.getInventory().removeItem(oneItem);

        // Если предмет почему-то не удалился
        if (!notRemoved.isEmpty()) {
            player.sendMessage("Не удалось удалить предмет!");
        }
    }
}

