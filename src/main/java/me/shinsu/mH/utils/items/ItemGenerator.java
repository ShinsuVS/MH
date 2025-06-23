package me.shinsu.mH.utils.items;

import me.shinsu.mH.MH;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class ItemGenerator {
    private static final Random random = new Random();
    private static final List<DropEntry> dropEntries = new ArrayList<>();

    private static class DropEntry {
        private final List<ItemStack> items;
        private final int fixedAmount;
        private final int minAmount;
        private final int maxAmount;

        DropEntry(List<ItemStack> items) {
            this(items, 0, 0, 0);
        }

        DropEntry(List<ItemStack> items, int fixedAmount) {
            this(items, fixedAmount, 0, 0);
        }

        DropEntry(List<ItemStack> items, int minAmount, int maxAmount) {
            this(items, 0, minAmount, maxAmount);
        }

        private DropEntry(List<ItemStack> items, int fixedAmount, int minAmount, int maxAmount) {
            this.items = new ArrayList<>(items);
            this.fixedAmount = fixedAmount;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }

        List<ItemStack> generateDrop() {
            List<ItemStack> result = new ArrayList<>();
            int amount = determineAmount();

            for (ItemStack item : items) {
                ItemStack clone = item.clone();
                if (amount > 0) {
                    clone.setAmount(amount);
                }
                result.add(clone);
            }
            return result;
        }

        private int determineAmount() {
            if (fixedAmount > 0) return fixedAmount;
            if (minAmount > 0 && maxAmount >= minAmount) {
                return random.nextInt(minAmount, maxAmount + 1);
            }
            return 0;
        }
    }

    public static void addItem(ItemStack item, int amount, int chance) {
        addItems(Collections.singletonList(item), amount, chance);
    }

    public static void addItem(ItemStack item, int minAmount, int maxAmount, int chance) {
        addItems(Collections.singletonList(item), minAmount, maxAmount, chance);
    }

    public static void addItems(List<ItemStack> items, int chance) {
        for (int i = 0; i < chance; i++) {
            dropEntries.add(new DropEntry(items));
        }
    }

    public static void addItems(List<ItemStack> items, int amount, int chance) {
        for (int i = 0; i < chance; i++) {
            dropEntries.add(new DropEntry(items, amount));
        }
    }

    public static void addItems(List<ItemStack> items, int minAmount, int maxAmount, int chance) {
        for (int i = 0; i < chance; i++) {
            dropEntries.add(new DropEntry(items, minAmount, maxAmount));
        }
    }

    public static List<ItemStack> getRandomDrop() {
        if (dropEntries.isEmpty()) {
            return null;
        }

        int index = random.nextInt(dropEntries.size());
        return dropEntries.get(index).generateDrop();
    }

    public static boolean isDropFileExists(MH plugin) {
        if (plugin == null) {
            Bukkit.getLogger().warning("[ItemGenerator] Plugin is null! Cannot check file existence.");
            return false;
        }
        return new File(plugin.getDataFolder(), "drop_chances.yml").exists();
    }

    public static void clear() {
        dropEntries.clear();
    }
}