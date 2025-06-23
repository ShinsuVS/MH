package me.shinsu.mH.actionItems.data;

import me.shinsu.mH.MH;
import me.shinsu.mH.actionItems.ActionItemBase;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class GrapplingHook extends ActionItemBase {

   private MH plugin;

   public GrapplingHook(MH plugin){
       this.plugin = plugin;
   }



    @Override
    public String getName() {
        return "&6&lHOOK";
    }

    @Override
    public Material getMaterial() {
        return Material.FISHING_ROD;
    }

    @Override
    public List<String> getLore() {
        return List.of("ПКМ для хука");
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.getItem(1));
        recipe.shape("X  ", "Y  ","   ");
        recipe.setIngredient('X', Material.FISHING_ROD);
        recipe.setIngredient('Y', Material.COBBLESTONE);
        return recipe;
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

    }

    @Override
    public void onProjectileHitEvent(Player player, Projectile projectile, ProjectileHitEvent event) {

    }

    @Override
    public void onFishEndEvent(Player player, PlayerFishEvent event) {

        player.sendMessage(Colorize.ColorString("&4&lHOOKED"));
        Location locA = player.getLocation();
        Location locB = event.getHook().getLocation();
        Vector dir = locB.toVector().subtract(locA.toVector()).normalize();
        dir.add(new Vector(0, 0.5 , 0));
        player.setVelocity(dir.multiply(2));
    }

    //V2
//    @Override
//    public void onFishEndEvent(Player player, PlayerFishEvent event) {
//
//        player.sendMessage(Colorize.ColorString("&4&lHOOKED"));
//
//        Location playerLoc = player.getLocation();
//        Location hookLoc = event.getHook().getLocation();
//
//        // Вычисляем направление от игрока к крюку
//        Vector direction = hookLoc.toVector().subtract(playerLoc.toVector());
//
//        // Проверяем, чтобы не было деления на ноль
//        if (direction.lengthSquared() > 0) {
//            direction.normalize();
//
//            // Добавляем вертикальную компоненту (можно регулировать)
//            double verticalBoost = 0.35;
//            direction.setY(direction.getY() + verticalBoost);
//
//            // Регулируем силу притяжения
//            double strength = 2; // Можно настроить под нужную физику
//
//            // Применяем скорость с небольшим уменьшением по вертикали
//            player.setVelocity(direction.multiply(strength));
//
//            // Можно добавить эффекты для лучшего визуального восприятия
//            player.getWorld().playSound(playerLoc, Sound.ENTITY_BAT_TAKEOFF, 1.0f, 0.5f);
//            player.spawnParticle(Particle.CLOUD, playerLoc, 20);
//        }
//}
//    @Override
//    public void onFishEndEvent(Player player, PlayerFishEvent event) {
//
//        player.sendMessage(Colorize.ColorString("&4&lHOOKED"));
//
//        Location hookLoc = event.getHook().getLocation();
//        hookLoc.add(0, 0.5, 0); // Цель немного выше крюка
//
//        // Запускаем притягивание с "перелётом"
//        new BukkitRunnable() {
//            private int ticks = 0;
//            private final double maxDistance = 30.0;
//            private final double initialDistance = player.getLocation().distance(hookLoc);
//            private Location targetLoc = hookLoc.clone(); // Изначально цель - крюк
//
//            @Override
//            public void run() {
//                // Если игрок уже перелетел точку крюка, цель смещается дальше
//                if (ticks > 10 && player.getLocation().distance(hookLoc) < 2.0 && targetLoc.equals(hookLoc)) {
//                    Vector overshootDir = hookLoc.toVector().subtract(player.getLocation().toVector()).normalize();
//                    targetLoc = hookLoc.clone().add(overshootDir.multiply(3)); // Пролетает на 3 блока дальше
//                }
//
//                // Условия отмены
//                if (ticks++ > 60 || // Укороченное время (3 секунды)
//                        !player.isValid() ||
//                        player.getLocation().distance(hookLoc) > maxDistance) {
//                    cancel();
//                    return;
//                }
//
//                Location playerLoc = player.getLocation();
//                Vector direction = targetLoc.toVector().subtract(playerLoc.toVector());
//                double distance = direction.length();
//
//                if (distance < 0.3) {
//                    cancel();
//                    return;
//                }
//
//                // Ускоренный полёт (сила больше и растёт быстрее)
//                double strength = 0.5 + (1.2 * (ticks / 15.0)); // Быстрый разгон
//                strength = Math.min(strength, 2.0); // Максимальная скорость увеличена
//
//                // Учёт изначальной дистанции
//                double distanceFactor = Math.min(1.5, initialDistance / 8.0); // Более агрессивный множитель
//                strength *= distanceFactor;
//
//                if (distance > 0) {
//                    direction.normalize();
//                    // Вертикальная составляющая (меньше "подбрасывания", больше инерции)
//                    direction.setY(direction.getY() * 0.7 + 0.2);
//
//                    player.setVelocity(direction.multiply(strength));
//                }
//
//                // Визуальные эффекты (чаще и заметнее)
//                if (ticks % 3 == 0) {
//                    player.getWorld().spawnParticle(Particle.CRIT, playerLoc, 5, 0.1, 0.1, 0.1, 0.05);
//                    player.getWorld().playSound(playerLoc, Sound.ENTITY_BAT_TAKEOFF, 0.4f, 1.8f);
//                }
//            }
//        }.runTaskTimer(plugin, 0, 1);
//    }


}
