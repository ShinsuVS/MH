package me.shinsu.mH;

import lombok.Getter;
import me.shinsu.mH.actionItems.ActionItemBase;
import me.shinsu.mH.actionItems.ActionItemHandler;
import me.shinsu.mH.actionItems.data.*;
import me.shinsu.mH.commands.MhBaseCommand;
import me.shinsu.mH.listeners.advancement.PlayersAdvancementListener;
import me.shinsu.mH.listeners.chat.ChatListener;
import me.shinsu.mH.listeners.players.PlayersListener;
import me.shinsu.mH.manhunt.gameListeners.GameListener;
import me.shinsu.mH.manhunt.gameListeners.GamePlayersListener;
import me.shinsu.mH.manhunt.manager.GameManager;
import me.shinsu.mH.tests.MetaListener;
import me.shinsu.mH.utils.items.ItemGenerator;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.mineacademy.fo.plugin.SimplePlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class MH extends SimplePlugin {
    @Getter
    private  GameManager manager;
    public static NamespacedKey actionItemKey;
    public static Map<String, ActionItemBase> customItemMap;



    @Override
    public void onPluginStart() {
        saveDefaultConfig();


        actionItemKey = new NamespacedKey(this, "action_item_key");
        customItemMap = new HashMap<>();

        registerListeners(
                new ActionItemHandler(this),
                new GameListener(this),
                new ChatListener(this),
                new PlayersListener(this),
                new GamePlayersListener(),
                new PlayersAdvancementListener(this),
                new MetaListener()

        );

        registerItems(
                new Compass(this),
                new StartItem(this),
                new GrapplingHook(this),
                new HealingSphere(this),
                new LootingItem(this)

        );


        ItemGenerator.addItem(MH.customItemMap.get("grapplinghook").getItem(1), 1 ,10);
        ItemGenerator.addItem(MH.customItemMap.get("healingsphere").getItem(1), 1 ,10);
        if(ItemGenerator.isDropFileExists(this)){

        }
        else {

        }




        getCommand("mh").setExecutor(new MhBaseCommand(this));

        this.manager = new GameManager(this);
    }

    @Override
    public void onPluginStop() {
        // Plugin shutdown logic
    }

    private void registerItems(ActionItemBase... customItems) {
        Arrays.asList(customItems).forEach(aI-> {
            customItemMap.put(aI.getId(), aI);
            if(aI.getRecipe() !=null){
                Bukkit.addRecipe( aI.getRecipe());
            }
        });
    }
    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(l-> Bukkit.getPluginManager().registerEvents(l, this));
    }

}
