package me.shinsu.mH.manhunt;

import lombok.Getter;
import lombok.Setter;
import me.shinsu.mH.MH;
import me.shinsu.mH.actionEvents.sphere.SphereManager;
import me.shinsu.mH.manhunt.advancement.AdvancementManager;
import me.shinsu.mH.manhunt.events.GameCreateEvent;
import me.shinsu.mH.manhunt.scoreboard.ScoreBoardManager;
import me.shinsu.mH.menu.data.SetupSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.fo.menu.model.SkullCreator;

import java.util.*;

public class Game {

    private final MH plugin;

    @Setter
    @Getter
    private List<GamePlayer> playerList  = new ArrayList<>();
    @Getter
    private GameStage stage;
    @Setter
    @Getter
    private boolean isActionItemAllowed;
    @Setter
    @Getter
    private boolean isTimerEnabled;
    @Setter
    @Getter
    private  int timer;
    @Setter
    @Getter
    private Map<GamePlayer, GamePlayer> huntersLocate = new HashMap<>();
    @Setter
    @Getter
    private boolean isScoreBoardEnabled;
    @Getter
    private SphereManager sphereManager;

    @Getter
    private List<BukkitTask> gameTask = new ArrayList<>();
    @Getter
    private AdvancementManager advancementManager = new AdvancementManager();

    private final ScoreBoardManager manager;



    public Game(SetupSettings settings, MH plugin){
        this.plugin = plugin;
        this.sphereManager = new SphereManager(plugin);

        manager = new ScoreBoardManager(plugin);

            for(Player p : Bukkit.getOnlinePlayers()){
                if(settings.getSpeedRunners().contains(p.getDisplayName())){
                    GamePlayer gamePlayer = new GamePlayer(p, GamePlayerType.SPEEDRUNNER);
                    getPlayerList().add(gamePlayer);
                 }
                if(settings.getHunters().contains(p.getDisplayName())){
                    GamePlayer gamePlayer = new GamePlayer(p, GamePlayerType.HUNTER);
                    getPlayerList().add(gamePlayer);
                }
            }

        setStage(GameStage.WAITING);
        isScoreBoardEnabled = settings.isScoreBoardEnabled();
        isActionItemAllowed = settings.isEnableCustomItem();
        isTimerEnabled = settings.isTimerEnabled();
        timer = settings.getTimerSecondsBlind();


        getHunters().forEach(gamePlayer -> {
            getHuntersLocate().put(gamePlayer, getSpeedRunners().get(0));
        });

        plugin.getServer().getPluginManager().callEvent(new GameCreateEvent(this));

        if(isScoreBoardEnabled){
            addTask(plugin.getServer().getScheduler().runTaskTimer(plugin, manager, 0, 20));
        }


    }
    public List<GamePlayer> getHunters(){
        return getPlayerList().stream().filter(s -> s.getGamePlayerType().equals(GamePlayerType.HUNTER)).toList();
    }
    public List<GamePlayer> getSpeedRunners(){
        return getPlayerList().stream().filter(s -> s.getGamePlayerType().equals(GamePlayerType.SPEEDRUNNER)).toList();
    }
    public boolean isPlayerContains(Player player){
        return getPlayerList().stream().filter(gamePlayer -> gamePlayer.getPlayer().getDisplayName().contains(player.getDisplayName())).isParallel();
    }
    public GamePlayer getGamePlayer(Player player){
        return getPlayerList().stream().filter(gamePlayer -> gamePlayer.getPlayer().getDisplayName().equals(player.getDisplayName())).findAny().orElse(null);
    }
    public boolean isSpeedRunner(Player player){
        GamePlayer player1 = getSpeedRunners().stream().filter(gamePlayer -> gamePlayer.getPlayer().getDisplayName().equals(player)).findAny().orElse(null);
       if(player1 != null){
           return true; // BUGS need redo
       }
       else
        return false ;
    }
    public boolean isSpeedRunner(GamePlayer gamePlayer){
        return getSpeedRunners().contains(gamePlayer);
    }


    public List<ItemStack> getSpeedRunnersHead(){
        List<ItemStack> playerHeads = new ArrayList<>();
        getSpeedRunners().forEach(gamePlayer -> {
            ItemStack item = SkullCreator.itemFromUrl(gamePlayer.getPlayer().getPlayerProfile().getTextures().getSkin().toString());
            ItemMeta meta =  item.getItemMeta();
            meta.setDisplayName(gamePlayer.getPlayer().getDisplayName());
            item.setItemMeta(meta);
            playerHeads.add(item);

        });

        return playerHeads;
    }

    public void addTask(BukkitTask task){
        this.gameTask.add(task);
    }

    public void setStage(GameStage stage){
        this.stage = stage;
        manager.setStage(stage);
        manager.setPlayersList(getPlayerList());
    }
}
