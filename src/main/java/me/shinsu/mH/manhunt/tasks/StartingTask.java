package me.shinsu.mH.manhunt.tasks;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GameStage;
import me.shinsu.mH.utils.Colorize;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StartingTask extends TaskTimer{

    private Game game;
    private long time;


    public StartingTask(MH plugin , Game game , long time) {
        super(plugin);
        this.game = game;
        this.time = time;

    }

    @Override
    public void execute() {
        if(time == 0){
            game.getPlayerList().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(Colorize.ColorString("&aИгра началась.")));
            game.getHunters().forEach(gamePlayer -> gamePlayer.getPlayer().getActivePotionEffects().forEach(potionEffect ->gamePlayer.getPlayer().removePotionEffect( potionEffect.getType()) ));
            game.getHunters().forEach(gamePlayer -> gamePlayer.getPlayer().getInventory().addItem(MH.customItemMap.get("compass").getItem(1)));
            game.setStage(GameStage.INGAME);
            cancel();

        }
        if(time != 0) {
            game.getHunters().forEach(gamePlayer -> gamePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10, true, false)));
            game.getHunters().forEach(gamePlayer -> gamePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 10, true, false)));
            game.getPlayerList().forEach(gamePlayer -> gamePlayer.getPlayer().sendMessage(Colorize.ColorString("&c&lИгра начнется через -> [" + String.valueOf(time) + "] секунд.")));

        }

        time--;
    }
}
