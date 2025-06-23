package me.shinsu.mH.menu;

import me.shinsu.mH.MH;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.MenuPagged;


public class CompassMenu extends MenuPagged<ItemStack>{

    private final MH plugin;
    private Player viewer;

    public CompassMenu(MH plugin, Player viewer){
        super(plugin.getManager().getPlayerGame(viewer).getSpeedRunnersHead());
        this.plugin = plugin;
        this.viewer = viewer;

        if(plugin.getManager().getPlayerGame(viewer).getSpeedRunners().size() <= 9){
            setSize(9);
        }

        setTitle("&4&l&oВыбери спидранера");
    }


    @Override
    protected ItemStack convertToItemStack(ItemStack itemStack) {
        return itemStack;
    }

    @Override
    protected void onPageClick(Player player, ItemStack itemStack, ClickType clickType) {
        plugin.getManager().getPlayerGame(player).getHuntersLocate().put(plugin.getManager().getPlayerGame(player).getGamePlayer(player), plugin.getManager().getPlayerGame(player).getSpeedRunners().stream().filter(gamePlayer -> gamePlayer.getPlayer().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())).findAny().orElse(null));
        player.closeInventory();
        player.sendMessage(Colorize.ColorString("&6&oПКМ для отслеживания последнего местоположения спидранера &a&l" + plugin.getManager().getPlayerGame(player).getHuntersLocate().get(plugin.getManager().getPlayerGame(player).getGamePlayer(player)).getPlayer().getDisplayName()));

    }
}
