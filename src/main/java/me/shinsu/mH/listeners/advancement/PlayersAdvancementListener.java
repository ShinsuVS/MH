package me.shinsu.mH.listeners.advancement;

import lombok.val;
import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GamePlayerType;
import me.shinsu.mH.manhunt.events.GameEndEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayersAdvancementListener implements Listener {

    private MH plugin;

    public PlayersAdvancementListener(MH plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event){
            if(plugin.getManager().getActivePlayerGame(event.getPlayer()) != null){

                if(plugin.getManager().getActivePlayerGame(event.getPlayer()).isSpeedRunner(plugin.getManager().getActivePlayerGame(event.getPlayer()).getGamePlayer(event.getPlayer()))){
                    if(event.getAdvancement().getKey().getKey().equals("story/root")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(1));
                        }

                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setWorkbench(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 5);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("story/lava_bucket")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));
                        }
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setHotStuff(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("nether/root")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));
                        }
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setDeepInHell(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("nether/find_fortress")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));
                        }
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setFortress(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("nether/obtain_blaze_rod")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));
                        }
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setHotRod(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("story/follow_ender_eye")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));
                        }
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setFindStronghold(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("story/enter_the_end")){
                        val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                        if(game.isActionItemAllowed()){
                            event.getPlayer().getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));
                        }
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setDeepInEnd(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);
                    }
                    if(event.getAdvancement().getKey().getKey().equals("end/kill_dragon")){
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setFreeEnd(true);
                        plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 15);
                        plugin.getServer().getPluginManager().callEvent(new GameEndEvent(plugin.getManager().getActivePlayerGame(event.getPlayer()), GamePlayerType.SPEEDRUNNER));

                    }
                }



            }
    }


    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event){
        if(plugin.getManager().getActivePlayerGame(event.getPlayer()) != null){

            if(plugin.getManager().getActivePlayerGame(event.getPlayer()).isSpeedRunner(plugin.getManager().getActivePlayerGame(event.getPlayer()).getGamePlayer(event.getPlayer()))){
                if(event.getItem().getItemStack().getType().equals(Material.ENDER_PEARL)){
                    val game = plugin.getManager().getActivePlayerGame(event.getPlayer());
                    if(game.isActionItemAllowed()){
                      val advancementManager = game.getAdvancementManager();
                      if(!advancementManager.isEnderPearl()){
                          plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setEnderPearl(true);
                          plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(event.getPlayer()).getAdvancementManager().getPercent() + 10);

                      }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onCraft(CraftItemEvent event){
        Player player = (Player)event.getWhoClicked();
        if(plugin.getManager().getActivePlayerGame(player) != null){


            if(plugin.getManager().getActivePlayerGame(player).isSpeedRunner(plugin.getManager().getActivePlayerGame(player).getGamePlayer(player))){
                if(event.getRecipe().getResult().getType().equals(Material.ENDER_EYE)){
                    val game = plugin.getManager().getActivePlayerGame(player);
                    if(game.isActionItemAllowed()){

                        player.getInventory().addItem(MH.customItemMap.get("lootingitem").getItem(3));

                    }
                    plugin.getManager().getActivePlayerGame(player).getAdvancementManager().setHellComplete(true);
                    plugin.getManager().getActivePlayerGame(player).getAdvancementManager().setPercent(plugin.getManager().getActivePlayerGame(player).getAdvancementManager().getPercent() + 10);
                }
            }
        }
    }
}
