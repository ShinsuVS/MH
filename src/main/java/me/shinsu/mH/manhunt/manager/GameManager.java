package me.shinsu.mH.manhunt.manager;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GameStage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private MH plugin;

    private List<Game> games = new ArrayList<>();

    public GameManager( MH plugin){
        this.plugin = plugin;
    }

    public void addGame(Game game){
        games.add(game);
    }
    public void removeGame(Game game){
        games.remove(game);
    }
    public Game getPlayerGame(Player player){
        return games.stream().filter(game -> game.getGamePlayer(player) != null).findAny().orElse(null);
    }
    public List<Game> getActiveGames(){
       return games.stream().filter(game -> !game.getStage().equals(GameStage.ENDING)).toList();
    }
    public Game getActivePlayerGame(Player player){
        return getActiveGames().stream().filter(game -> game.getGamePlayer(player) != null).findAny().orElse(null);
    }
    public boolean isGameActive(Player player){
        if(getActivePlayerGame(player) == null){
            return false;
        }
        else return true;
    }

}
