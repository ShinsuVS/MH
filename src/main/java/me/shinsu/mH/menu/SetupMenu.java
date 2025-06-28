package me.shinsu.mH.menu;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.menu.data.SetupSettings;
import me.shinsu.mH.utils.inventory.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.button.ButtonReturnBack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.MenuClickLocation;
import org.mineacademy.fo.menu.model.SkullCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SetupMenu extends Menu {

    private MH plugin;
    //Buttons
    private final Button mirrorBtn;
    private final Button settingsBtn;
    private final Button infoBtn;
    private final Button infoBtn2;
    private final Button settingsInfo;
    private final Button confirmBtn;
    private final Button closeBtn;

    //Data
    private final int[] speedRunnersSlots = {18,19,20,21,27,28,29,30,36,37,38,39,45,46,47,48};
    private final int[] huntersSlots = {23,24,25,26,32,33,34,35,41,42,43,44,50,51,52,53};

    //Settings
    private final SetupSettings settings = new SetupSettings();

    public SetupMenu( MH plugin ){

        this.plugin  = plugin;

        setTitle("&1&lСетап игры");
        setSize(9*6);

        this.mirrorBtn = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {

            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE," ").glow(false).make();
            }
        };

        this.settingsBtn = new ButtonMenu( new SettingsMenu(), ItemCreator.of(CompMaterial.COMPARATOR,
                "&1&lМеню настроек.",
                "&c&lНажми для настройки игры.").glow(true));

        this.infoBtn = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {

            }

            @Override
            public ItemStack getItem() {

                ItemStack item =  SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNhMzlhZjg3NzE5YTQyNzlmNDkxNTUyYTExODhlNjUxMWI3NGI1NWJiY2FkOTdlMjc3YmE3Y2M2NzI3OGFmMCJ9fX0=");
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN +""+ ChatColor.BOLD + "SpeedRunners");
                item.setItemMeta(meta);
                return item;
            }
        };
        this.infoBtn2 = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {

            }

            @Override
            public ItemStack getItem() {
                ItemStack item =  SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY5NmI4ZDAxZjU4MzVlZDM4YWZkNDUzMDIyOGQwYjVhYmI3ZDQ1YTM1NTUxOWVhNjgwYzQwZmZjYTMyZWRmMiJ9fX0=");
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED +""+ ChatColor.BOLD + "Hunters");
                item.setItemMeta(meta);
                return item;
            }
        };
        this.settingsInfo = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                restartMenu();
                onPostDisplay(player);
            }

            @Override
            public ItemStack getItem() {
                ItemStack item =  SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTY0MzlkMmUzMDZiMjI1NTE2YWE5YTZkMDA3YTdlNzVlZGQyZDUwMTVkMTEzYjQyZjQ0YmU2MmE1MTdlNTc0ZiJ9fX0=");
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA +""+ ChatColor.BOLD + "Информация(нажми для обновления) : ");
                List<String> lore = new ArrayList<>();
                lore.add( "Количество спидранеров : " + settings.getSpeedRunners().size() );
                if(!settings.getSpeedRunners().isEmpty() && settings.getSpeedRunners().size() <= 5){
                    settings.getSpeedRunners().forEach( s -> lore.add(ChatColor.GREEN + "[R] " + s));
                }
                lore.add( "Количество охотников : " + settings.getHunters().size() );
                if(!settings.getHunters().isEmpty() && settings.getHunters().size() <= 5){
                    settings.getHunters().forEach(s -> lore.add(ChatColor.RED + "[H] " + s));
                }
                lore.add( "Кастомные предметы : " + (settings.isEnableCustomItem() ? "Включены" : "Выключены"));
                lore.add( "Таймер заморозки охотников : " + (settings.isTimerEnabled() ? "Включен " + settings.getTimerSecondsBlind() + "cек"  : "Выключен"));
                meta.setLore(lore);
                item.setItemMeta(meta);
                return item;
            }
        };
        this.confirmBtn = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                player.closeInventory();
                plugin.getManager().addGame(new Game(settings, plugin));

            }

            @Override
            public ItemStack getItem() {
                ItemStack item = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTkyZTMxZmZiNTljOTBhYjA4ZmM5ZGMxZmUyNjgwMjAzNWEzYTQ3YzQyZmVlNjM0MjNiY2RiNDI2MmVjYjliNiJ9fX0=");
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Создать игру!");
                item.setItemMeta(meta);

                return item;
            }
        };
        this.closeBtn = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                player.sendMessage(settings.toString());
                player.closeInventory();
            }

            @Override
            public ItemStack getItem() {
                ItemStack item = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdmMzNkM2Y5Mzg3Mzc0NWEzZTE1ZTJhM2Y0NjI4N2JhNmYxNDU3YzhiNzkwYmIzYTk4YmY1NmZkNTlkZmJiIn19fQ==");
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Отменить создание игры!");
                meta.setLore(Collections.singletonList( ChatColor.RED + "Так же для закрытия меню, можно из него просто выйти."));
                item.setItemMeta(meta);
                return item;
            }
        };


    }

    @Override
    public ItemStack getItemAt(int slot) {
        if(slot == 3){
            return closeBtn.getItem();
        }
        if(slot == 4){
            return settingsInfo.getItem();
        }
        if(slot == 5){
            return confirmBtn.getItem();
        }
        if(slot>8 && slot < 18 || slot == 22 || slot == 31 || slot == 40 || slot == 49){
            if(slot == 10 ){
                return infoBtn.getItem();
            }
            if(slot == 16){
                return infoBtn2.getItem();
            }

            else {
                return mirrorBtn.getItem();
            }

        }




        if(slot == 8){
            return  settingsBtn.getItem();
        }



        return NO_ITEM;
    }

    @Override
    protected boolean isActionAllowed(MenuClickLocation location, int slot, @Nullable ItemStack clicked, @Nullable ItemStack cursor) {

        if(slot >= 18 &&  slot <= 21 || slot >= 27 &&  slot <= 30 || slot >= 36 &&  slot <= 39 || slot >= 45 &&  slot <= 48 || slot >= 23 &&  slot <= 26 || slot >= 32 &&  slot <= 35 || slot >= 41 &&  slot <= 44 || slot >= 50 &&  slot <= 53){
            return true;
        }
        if(location.equals(MenuClickLocation.PLAYER_INVENTORY)){
            return true;
        }
        return false;
    }

    @Override
    protected void onMenuClose(Player player, Inventory inventory) {
        for(int i = 0; i < speedRunnersSlots.length; i++){
            if(inventory.getItem(speedRunnersSlots[i]) != null){
                if(inventory.getItem(speedRunnersSlots[i]).getType().equals(Material.PLAYER_HEAD)){
                    if(!settings.getSpeedRunners().contains(inventory.getItem(speedRunnersSlots[i]).getItemMeta().getDisplayName())){
                       if(!settings.getHunters().contains(inventory.getItem(speedRunnersSlots[i]).getItemMeta().getDisplayName())){
                           settings.getSpeedRunners().add(inventory.getItem(speedRunnersSlots[i]).getItemMeta().getDisplayName());
                           settings.getItemCollectiom().put(speedRunnersSlots[i], inventory.getItem(speedRunnersSlots[i]));
                       }
                    }


                }
            }

        }
        for(int i = 0; i < huntersSlots.length; i++){
            if(inventory.getItem(huntersSlots[i]) != null){
                if(inventory.getItem(huntersSlots[i]).getType().equals(Material.PLAYER_HEAD)){
                   if(!settings.getHunters().contains(inventory.getItem(huntersSlots[i]).getItemMeta().getDisplayName())){
                      if(!settings.getSpeedRunners().contains(inventory.getItem(huntersSlots[i]).getItemMeta().getDisplayName())){
                          settings.getHunters().add(inventory.getItem(huntersSlots[i]).getItemMeta().getDisplayName());
                          settings.getItemCollectiom().put(huntersSlots[i], inventory.getItem(huntersSlots[i]));
                      }

                   }


                }
            }

        }



    }

    @Override
    protected void onPostDisplay(Player viewer) {
        if(!settings.getItemCollectiom().isEmpty()){
            settings.getItemCollectiom().forEach(
                    (key, tab) -> setItem(key, tab)
                    );


        }
    }

    private class SettingsMenu extends Menu{

        private Button infoBtn;
        private final Button mirrorBtn;
        private final Button timerON;
        private final Button timeUp;
        private final Button timeDowm;
        private final Button scoreBoardBtn;
        private final Button actionItemManagerMenu;

        SettingsMenu(){

            super(SetupMenu.this);

            //
            setSize(9*6);
            setTitle("&1&lНастройки");

            //
            ButtonReturnBack.setMaterial(CompMaterial.LECTERN);
            ButtonReturnBack.setTitle("&4Вернуться назад");
            ButtonReturnBack.setLore(new ArrayList<>());

            this.mirrorBtn = new Button() {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {

                }

                @Override
                public ItemStack getItem() {
                    return ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE," ").glow(false).make();
                }
            };
            this.infoBtn = new Button() {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {

                    if(settings.isEnableCustomItem()){
                        settings.setEnableCustomItem(false);
                    }
                    else {
                        settings.setEnableCustomItem(true);
                    }

                }

                @Override
                public ItemStack getItem() {
                    return ItemCreator.of(CompMaterial.RESPAWN_ANCHOR,
                            "&a&lИспользовать кастомные предметы",
                            "&4&lНажми на кнопку ->").glow(true).make();
                }
            };

            this.timerON = new Button() {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                    if(settings.isTimerEnabled()){
                        settings.setTimerEnabled(false);
                    }
                    else {
                        settings.setTimerEnabled(true);
                    }
                }

                @Override
                public ItemStack getItem() {
                    return ItemCreator.of(CompMaterial.RESPAWN_ANCHOR,
                            "&a&lВключить таймер заморозки охотников",
                            "&4&lНажми на кнопку",
                            "&4&lБазовое время: &r&6" + settings.getTimerSecondsBlind()).glow(true).make();
                }
            };
            this.timeUp = new Button() {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {

                    settings.setTimerSecondsBlind(settings.getTimerSecondsBlind() + 5);
                    restartMenu();
                }

                @Override
                public ItemStack getItem() {

                    ItemStack item = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ==");
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.AQUA + "Увеличить время на 5с");
                    item.setItemMeta(meta);
                    return item;
                }
            };
            this.timeDowm = new Button() {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                   if(settings.getTimerSecondsBlind() <= 5){
                       settings.setTimerSecondsBlind(0);
                       settings.setTimerEnabled(false);
                       restartMenu();
                   }
                   else {
                       settings.setTimerSecondsBlind(settings.getTimerSecondsBlind() - 5);
                       restartMenu();
                   }

                }

                @Override
                public ItemStack getItem() {

                    ItemStack item = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ3MmM5ZDYyOGJiMzIyMWVmMzZiNGNiZDBiOWYxNWVkZDU4ZTU4NjgxODUxNGQ3ZTgyM2Q1NWM0OGMifX19");
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.AQUA + "Уменьшить время на 5с");
                    item.setItemMeta(meta);
                    return item;
                }
            };

            this.scoreBoardBtn = new Button(19) {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                    if (settings.isScoreBoardEnabled()) {
                        settings.setScoreBoardEnabled(false);
                    } else {
                        settings.setScoreBoardEnabled(true);
                    }
                }

                @Override
                public ItemStack getItem() {
                    return ItemCreator.of(CompMaterial.RESPAWN_ANCHOR,
                            "&a&lИспользовать скорбоард",
                            "&4&lНажми на кнопку ->").glow(true).make();
                }
            };
            this.actionItemManagerMenu = new Button(53) {
                @Override
                public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                    new ActionItemsManagerMenu().displayTo(player);
                }

                @Override
                public ItemStack getItem() {
                    return ItemCreator.of(CompMaterial.ENDER_CHEST,
                            "Настройки кастомных предметов",
                            "СКОРО!").glow(true).make();
                }
            };



        }

        @Override
        public ItemStack getItemAt(int slot) {
            if(slot == 4 || slot == 13 || slot == 22 || slot == 31 || slot == 40 || slot == 49){
                return mirrorBtn.getItem();
            }
            if(slot == 10 ){
                return infoBtn.getItem();
            }

            if(slot == 45)
            {
                return timerON.getItem();
            }
            if(slot == 47){
                return timeUp.getItem();
            }
            if(slot == 48){
                return timeDowm.getItem();
            }
            return NO_ITEM;
        }

        @Override
        protected void onPostDisplay(Player viewer) {
            animate(10, new MenuRunnable() {
                @Override
                public void run() {
                    setItem(11,  ItemCreator.of(settings.isEnableCustomItem() ? CompMaterial.LIME_DYE : CompMaterial.GRAY_DYE,settings.isEnableCustomItem() ? "&a&lВКЛЮЧЕНО" : "&7&lВЫКЛЮЧЕНО" ).make());
                    setItem(46,  ItemCreator.of(settings.isTimerEnabled() ? CompMaterial.LIME_DYE : CompMaterial.GRAY_DYE,settings.isTimerEnabled() ? "&a&lВКЛЮЧЕНО" : "&7&lВЫКЛЮЧЕНО" ).make());
                    setItem(20, ItemCreator.of(settings.isScoreBoardEnabled() ?  CompMaterial.LIME_DYE : CompMaterial.GRAY_DYE, settings.isScoreBoardEnabled() ? "&a&lВКЛЮЧЕНО" : "&7&lВЫКЛЮЧЕНО" ).make());
                }
            });
        }

        @Override
        protected int getReturnButtonPosition() {
            return 0;
        }

        public List<ItemStack> getItems(){
            InventoryUtils.getItemsList();
            return  InventoryUtils.getItemsList();
        }

        private class ActionItemsManagerMenu extends MenuPagged<ItemStack>{


            ActionItemsManagerMenu()
            {
                super(getItems());
            }



            @Override
            protected ItemStack convertToItemStack(ItemStack itemStack) {
                return itemStack;
            }

            @Override
            protected void onPageClick(Player player, ItemStack itemStack, ClickType clickType) {

            }
        }
    }


}
