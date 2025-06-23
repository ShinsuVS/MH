package me.shinsu.mH.actionItems.data;


import me.shinsu.mH.MH;
import me.shinsu.mH.actionItems.ActionItemBase;
import me.shinsu.mH.manhunt.GamePlayer;
import me.shinsu.mH.menu.CompassMenu;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.mineacademy.fo.menu.model.ItemCreator;

import java.util.*;

public class Compass extends ActionItemBase {

    private final MH plugin;

    private final Map<UUID, Long> lastUseTime = new HashMap<>();

    public Compass(MH plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "&6&lТрекер: &7НЕ УСТАНОВЛЕНО ";
    }


    @Override
    public Material getMaterial() {
        return Material.COMPASS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                Colorize.ColorString("&7Мини-игра: &f" + "&cMANHUNT"),
                Colorize.ColorString("&7ЛКМ: &aДля выбора цели "),
                Colorize.ColorString("&7ПКМ: &aДля отслеживания"),
                Colorize.ColorString("&8(Обновляется при использовании)"));

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
        if(plugin.getManager().getPlayerGame(player) != null){
            new CompassMenu(plugin,player).displayTo(player);
        }
        else
        {
            player.sendMessage(Colorize.ColorString("&aВаша игра закончилась или ещё не началось."));
            player.getInventory().remove(itemStack);
        }


    }

@Override
public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
    // 1. Проверяем, что игрок в игре
    if (plugin.getManager().getPlayerGame(player) == null) {
        player.sendMessage(Colorize.ColorString("&cВы не в игре!"));
        player.getInventory().remove(itemStack); // Удаляем компас, если игра не активна
        return;
    }

    // 2. Проверка кулдауна (10 секунд)
    long currentTime = System.currentTimeMillis();
    long lastUse = lastUseTime.getOrDefault(player.getUniqueId(), 0L);

    if (currentTime - lastUse < 10000) {
        double remainingSec = Math.ceil((10000 - (currentTime - lastUse)) / 1000.0);
        player.sendMessage(Colorize.ColorString("&eКомпас перезаряжается! &7(Осталось: &c" + remainingSec + " сек.&7)"));
        return;
    }

    // 3. Получаем цель (спидранера)
    Player target;
    try {
        target = plugin.getManager()
                .getPlayerGame(player)
                .getHuntersLocate()
                .get(plugin.getManager().getPlayerGame(player).getGamePlayer(player))
                .getPlayer();
    } catch (NullPointerException e) {
        player.sendMessage(Colorize.ColorString("&cОшибка: цель не найдена!"));
        return;
    }

    // 4. Проверяем, что цель онлайн
    if (target == null || !target.isOnline()) {
        player.sendMessage(Colorize.ColorString("&cИгрок &e" + target.getName() + " &cне в сети!"));
        return;
    }

    // 5. Обновляем компас
    CompassMeta meta = (CompassMeta) itemStack.getItemMeta();

    // Привязываем к локации цели (работает в Аду/Энде)
    meta.setLodestone(target.getLocation());
    meta.setLodestoneTracked(false); // Отключаем авто-сброс

    // Устанавливаем имя и описание
    meta.setDisplayName(Colorize.ColorString("&6Трекер &e" + target.getName()));
    meta.setLore(Arrays.asList(
            Colorize.ColorString("&7Мини-игра: &f" + "&cMANHUNT"),
            Colorize.ColorString("&7Координаты: &aX " + target.getLocation().getBlockX() +
                    " &aY " + target.getLocation().getBlockY() +
                    " &aZ " + target.getLocation().getBlockZ()),
            Colorize.ColorString("&8(Обновляется при использовании)")
    ));

    itemStack.setItemMeta(meta);

    // 6. Уведомление игрока
    player.sendMessage(Colorize.ColorString(
            "&aКомпас обновлён! Направление на &e" + target.getName() +
                    " &aв мире &b" + getWorldName(target.getWorld()) + "&a."
    ));

    // 7. Обновляем кулдаун
    lastUseTime.put(player.getUniqueId(), currentTime);
}

    // Вспомогательный метод для красивого отображения мира
    private String getWorldName(World world) {
        switch (world.getEnvironment()) {
            case NETHER: return "Аду";
            case THE_END: return "Энде";
            default: return "Обычном";
        }
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


    @Override
    public ItemStack getItem(int amount) {
        ItemStack itemStack = new ItemStack(getMaterial(), amount);
        CompassMeta itemMeta = (CompassMeta) itemStack.getItemMeta();
        itemMeta.setDisplayName(Colorize.ColorString(getName()));
        List<String> lore = new ArrayList<>();
        getLore().forEach(l-> lore.add(Colorize.ColorString(l)));
        itemMeta.setLore(lore);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(MH.actionItemKey, PersistentDataType.STRING, getId());
        itemStack.setItemMeta(itemMeta);


        return ItemCreator.of(itemStack).glow(true).make();
    }

}
