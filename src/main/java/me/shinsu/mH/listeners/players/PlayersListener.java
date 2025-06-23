package me.shinsu.mH.listeners.players;

import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.GamePlayerType;

import me.shinsu.mH.manhunt.events.HunterRespawnEvent;
import me.shinsu.mH.manhunt.events.SpeedRunnerDeathEvent;
import me.shinsu.mH.utils.resourcePack.ResourcePackManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayersListener implements Listener {

    private MH plugin;
    private ResourcePackManager packManager;

    public PlayersListener(MH plugin) {
        this.plugin = plugin;
        packManager = new ResourcePackManager(plugin, "https://drive.google.com/uc?export=download&id=1Qd57DAw689v4OGFZ7J6vpJQoYQcmx_WV");
        packManager.initialize();
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        if (plugin.getManager().getActivePlayerGame(event.getPlayer()) != null) {
            if (plugin.getManager().getActivePlayerGame(event.getPlayer()).getGamePlayer(event.getPlayer()).getGamePlayerType().equals(GamePlayerType.HUNTER)) {
                plugin.getServer().getPluginManager().callEvent(new HunterRespawnEvent(plugin.getManager().getActivePlayerGame(event.getPlayer()), plugin.getManager().getActivePlayerGame(event.getPlayer()).getGamePlayer(event.getPlayer())));
            }


        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (plugin.getManager().getActivePlayerGame(event.getEntity()) != null) {
            if (plugin.getManager().getActivePlayerGame(event.getEntity()).getGamePlayer(event.getEntity()).getGamePlayerType().equals(GamePlayerType.SPEEDRUNNER)) {
                plugin.getServer().getPluginManager().callEvent(new SpeedRunnerDeathEvent(plugin.getManager().getActivePlayerGame(event.getEntity()), plugin.getManager().getActivePlayerGame(event.getEntity()).getGamePlayer(event.getEntity())));
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().setResourcePack(
                packManager.getResourcePackUrl(),
                packManager.getResourcePackHash()
        );
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent e) {
        if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            e.getPlayer().kickPlayer("§cВы должны принять ресурспак для игры на сервере!");
        } else if (e.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            e.getPlayer().kickPlayer("§cОшибка загрузки ресурспака! Попробуйте позже.");
        }
    }

}
