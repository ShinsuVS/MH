package me.shinsu.mH.manhunt;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class GamePlayer {
    @Setter
    @Getter
    private Player player;
    @Setter
    @Getter
    private GamePlayerType gamePlayerType;

    public GamePlayer(Player player, GamePlayerType type) {
        this.player = player;
        this.gamePlayerType = type;
    }

}
