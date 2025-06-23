package me.shinsu.mH.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemEnchanter {

    /**
     * Зачаровывает существующий ItemStack
     *
     * @param itemStack предмет для зачарования
     * @param enchant зачарование
     * @param level уровень зачарования
     * @return зачарованный ItemStack
     */
    public static ItemStack of(ItemStack itemStack, Enchantment enchant, int level) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new IllegalArgumentException("ItemStack не может быть null или AIR");
        }

        ItemStack result = itemStack.clone();
        result.addUnsafeEnchantment(enchant, level);
        return result;
    }

    /**
     * Создает новый ItemStack из Material и зачаровывает его
     *
     * @param material материал предмета
     * @param amount количество предметов
     * @param enchant зачарование
     * @param level уровень зачарования
     * @return зачарованный ItemStack
     */
    public static ItemStack of(Material material, int amount, Enchantment enchant, int level) {
        if (material == null || material == Material.AIR) {
            throw new IllegalArgumentException("Material не может быть null или AIR");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше 0");
        }

        ItemStack result = new ItemStack(material, amount);
        result.addUnsafeEnchantment(enchant, level);
        return result;
    }
}
