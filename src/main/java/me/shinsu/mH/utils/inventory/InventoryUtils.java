package me.shinsu.mH.utils.inventory;

import me.shinsu.mH.MH;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryUtils {

    public static boolean canAddItemToInventory(Player player, ItemStack itemToAdd) {
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
    public static List<String> getCustomItemMapKeysAsList() {
        return new ArrayList<>(MH.customItemMap.keySet());
    }

    public static List<ItemStack> getItemsList(){
        List<ItemStack> itemStacks = new ArrayList<>();

        getCustomItemMapKeysAsList().forEach(k ->
                {
                    itemStacks.add(MH.customItemMap.get(k).getItem(1));
                }

        );


        return itemStacks;

    }
}
