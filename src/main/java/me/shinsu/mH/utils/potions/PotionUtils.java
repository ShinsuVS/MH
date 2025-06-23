package me.shinsu.mH.utils.potions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionUtils {
    public static ItemStack getPotion(PotionEffectType effect, int durationInSeconds, int strength) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.addCustomEffect(new PotionEffect(effect, durationInSeconds * 20, strength), true);
        potion.setItemMeta(meta);
        return potion;
    }
}
