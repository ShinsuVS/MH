package me.shinsu.mH.manhunt.scoreboard;

import lombok.Setter;
import me.shinsu.mH.MH;
import me.shinsu.mH.manhunt.Game;
import me.shinsu.mH.manhunt.GamePlayer;
import me.shinsu.mH.manhunt.GameStage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScoreBoardManager implements Runnable {

    private MH plugin;

    @Setter
    private GameStage stage;
    @Setter
    private List<GamePlayer> playersList;



    private Date starting;
    private Date now;

    private int fadeUpdater = 0;
    private int curr = 0;
    private String currDisplay = "";


    private int currWaiter = 0;
    private int currUpdater = 0;

    public ScoreBoardManager(MH plugin){
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        if(stage == GameStage.WAITING){
            for(GamePlayer gamePlayer : playersList){
                if(gamePlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null){
                    createWaitingScoreBoard(gamePlayer.getPlayer());
                }
                else {
                    updateWaitingScoreBoard(gamePlayer.getPlayer());
                }
            }
        }
        if(stage == GameStage.INGAME){
            for(GamePlayer gamePlayer : playersList){
                if(gamePlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null || gamePlayer.getPlayer().getScoreboard().getObjective("Ingame") == null ){
                    createIngameScoreBoard(gamePlayer.getPlayer());
                }
                else {
                    updateIngameScoreBoard(gamePlayer.getPlayer());
                }
            }
        }
        if(stage == GameStage.ENDING){
            for(GamePlayer gamePlayer : playersList){
                if(gamePlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null|| gamePlayer.getPlayer().getScoreboard().getObjective("Ending") == null){
                    createEndingScoreBoard(gamePlayer.getPlayer());
                }

            }
        }
    }


    public void createWaitingScoreBoard(Player player){
        //Scoreboard main
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Waiting", "yammy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.AQUA +  "ManHunt");
        //Scoreboard main

        //WaiterUpdater
        Team waiterUPD = scoreboard.registerNewTeam("waiter");
        waiterUPD.addEntry(ChatColor.GOLD.toString());
        waiterUPD.setPrefix(ChatColor.AQUA + " Ожидание начала: ");
        waiterUPD.setSuffix(ChatColor.GOLD + "▁▁▁▁▁▁");
        objective.getScore(ChatColor.GOLD.toString()).setScore(0);
        //WaiterUpdater



        player.setScoreboard(scoreboard);
    }
    public void updateWaitingScoreBoard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Waiting");
        objective.setDisplayName(roundFadeCharacter("ManHunt"));
        Team waiter = scoreboard.getTeam("waiter");
        waiter.setSuffix( ChatColor.LIGHT_PURPLE + animatedWaiter());
    }
    public void createIngameScoreBoard(Player player){
        //Scoreboard main
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Ingame", "yammy");

        starting = new Date();

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.AQUA +  "ManHunt");
        //Scoreboard main

        //Timer
        SimpleDateFormat forma = new SimpleDateFormat("dd:MM:yyyy HH:mm");

        //Timer

        objective.getScore( ChatColor.GREEN + "" + ChatColor.BOLD + "⇩ Статистика").setScore(14);

        //DateUpdater
        Team dateUPD = scoreboard.registerNewTeam("dateUPD");
        dateUPD.addEntry(ChatColor.BLUE.toString());
        dateUPD.setPrefix(ChatColor.AQUA + " Дата: ");
        dateUPD.setSuffix(ChatColor.GOLD + forma.format(starting));
        objective.getScore(ChatColor.BLUE.toString()).setScore(13);
        //DateUpdater
        //TimerUpdater
        Team timerUPD = scoreboard.registerNewTeam("timer");
        timerUPD.addEntry(ChatColor.GOLD.toString());
        timerUPD.setPrefix(ChatColor.AQUA + " Таймер: ");
        timerUPD.setSuffix(ChatColor.GOLD + "00:00");
        objective.getScore(ChatColor.GOLD.toString()).setScore(12);
        //TimerUpdater
        Team percent = scoreboard.registerNewTeam("percent");
        percent.addEntry(ChatColor.RED.toString());
        percent.setPrefix( ChatColor.AQUA + " Прохождение: ");
        percent.setSuffix(ChatColor.GOLD + "0%");
        objective.getScore(ChatColor.RED.toString()).setScore(11);
        objective.getScore( ChatColor.GREEN + "" + ChatColor.BOLD + "⇩ Достижения " + ChatColor.RESET + ChatColor.GOLD + "★").setScore(10);

        Team workbench = scoreboard.registerNewTeam("workbench");
        workbench.addEntry(ChatColor.WHITE.toString());
        workbench.setPrefix(ChatColor.AQUA + " Создание верстака : ");
        workbench.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.WHITE.toString()).setScore(9);

        Team hotStuff = scoreboard.registerNewTeam("hotStuff");
        hotStuff.addEntry(ChatColor.GREEN.toString());
        hotStuff.setPrefix(ChatColor.AQUA + " Горячая штучка : ");
        hotStuff.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.GREEN.toString()).setScore(8);

        Team deepInHell = scoreboard.registerNewTeam("deepInHell");
        deepInHell.addEntry(ChatColor.GRAY.toString());
        deepInHell.setPrefix(ChatColor.AQUA + " Посетить Ад : ");
        deepInHell.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.GRAY.toString()).setScore(7);

        Team fortress = scoreboard.registerNewTeam("fortress");
        fortress.addEntry(ChatColor.BLACK.toString());
        fortress.setPrefix(ChatColor.AQUA + " Найти фортресс : ");
        fortress.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.BLACK.toString()).setScore(6);

        Team hotRod = scoreboard.registerNewTeam("hotRod");
        hotRod.addEntry(ChatColor.YELLOW.toString());
        hotRod.setPrefix(ChatColor.AQUA + " Найти стержни : ");
        hotRod.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.YELLOW.toString()).setScore(5);

        Team enderPearl = scoreboard.registerNewTeam("enderPearl");
        enderPearl.addEntry(ChatColor.DARK_AQUA.toString());
        enderPearl.setPrefix(ChatColor.AQUA + " Получить жемчуг : ");
        enderPearl.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.DARK_AQUA.toString()).setScore(4);

        Team hellComplete = scoreboard.registerNewTeam("hellComplete");
        hellComplete.addEntry(ChatColor.DARK_BLUE.toString());
        hellComplete.setPrefix(ChatColor.AQUA + " Пройти Ад : ");
        hellComplete.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.DARK_BLUE.toString()).setScore(3);

        Team findStronghold = scoreboard.registerNewTeam("findStronghold");
        findStronghold.addEntry(ChatColor.DARK_GRAY.toString());
        findStronghold.setPrefix(ChatColor.AQUA + " Найти стронгхолд : ");
        findStronghold.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.DARK_GRAY.toString()).setScore(2);

        Team deepInEnd = scoreboard.registerNewTeam("deepInEnd");
        deepInEnd.addEntry(ChatColor.DARK_GREEN.toString());
        deepInEnd.setPrefix(ChatColor.AQUA + " Посетить Энд : ");
        deepInEnd.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);

        Team freeEnd = scoreboard.registerNewTeam("freeEnd");
        freeEnd.addEntry(ChatColor.DARK_PURPLE.toString());
        freeEnd.setPrefix(ChatColor.AQUA + " Освободить Энд : ");
        freeEnd.setSuffix(ChatColor.RED +""+ ChatColor.BOLD + "✖");
        objective.getScore(ChatColor.DARK_PURPLE.toString()).setScore(0);


        player.setScoreboard(scoreboard);
    }
    public void updateIngameScoreBoard(Player player){

        Scoreboard scoreboard = player.getScoreboard();
        Game game = plugin.getManager().getActivePlayerGame(player);
        now = new Date();

        Objective objective = scoreboard.getObjective("Ingame");
        objective.setDisplayName(roundFadeCharacter("ManHunt"));


        Team dateUPD = scoreboard.getTeam("dateUPD");
        SimpleDateFormat forma = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        dateUPD.setSuffix(ChatColor.GOLD + forma.format(now));
        Team timer = scoreboard.getTeam("timer");
        SimpleDateFormat timerformat  =  new SimpleDateFormat("HH:mm:ss");
        timer.setSuffix(ChatColor.GOLD + timerformat.format(getDateDiff(starting, now, TimeUnit.MILLISECONDS)));

        Team percent = scoreboard.getTeam("percent");
        percent.setSuffix( ChatColor.GOLD + String.valueOf(game.getAdvancementManager().getPercent()) + "%");

        if(game.getAdvancementManager().isWorkbench()){
            Team workbench = scoreboard.getTeam("workbench");
            workbench.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isHotStuff()){
            Team hotStuff = scoreboard.getTeam("hotStuff");
            hotStuff.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isDeepInHell()){
            Team deepInHell = scoreboard.getTeam("deepInHell");
            deepInHell.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isFortress()){
            Team fortress = scoreboard.getTeam("fortress");
            fortress.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isHotRod()){
            Team hotRod = scoreboard.getTeam("hotRod");
            hotRod.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isEnderPearl()){
            Team enderPearl = scoreboard.getTeam("enderPearl");
            enderPearl.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isHellComplete()){
            Team hellComplete = scoreboard.getTeam("hellComplete");
            hellComplete.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isFindStronghold()){
            Team findStronghold = scoreboard.getTeam("findStronghold");
            findStronghold.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isDeepInEnd()){
            Team deepInEnd = scoreboard.getTeam("deepInEnd");
            deepInEnd.setSuffix(ChatColor.GREEN + "✔");
        }
        if(game.getAdvancementManager().isFreeEnd()){
            Team freeEnd = scoreboard.getTeam("freeEnd");
            freeEnd.setSuffix(ChatColor.GREEN + "✔");
        }


    }
    public void createEndingScoreBoard(Player player){
        //Scoreboard main
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Ending", "yammy");

        now  = new Date();

        Game game = plugin.getManager().getPlayerGame(player);

        SimpleDateFormat forma = new SimpleDateFormat("HH:mm:ss");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.AQUA +  "ManHunt");
        //Scoreboard main
            if(game.getAdvancementManager().getPercent() != 100){
                objective.getScore(ChatColor.RED.toString()).setScore(5);
                objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "ОХОТНИКИ ПОБЕДИЛИ").setScore(4);
                objective.getScore(ChatColor.WHITE.toString()).setScore(3);
                objective.getScore(ChatColor.GOLD + "Затраченное время : " + forma.format(getDateDiff(starting, now, TimeUnit.MILLISECONDS))).setScore(2);
                objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);
                objective.getScore(ChatColor.DARK_PURPLE + "by Shinsu").setScore(0);
            }
            else {
                objective.getScore(ChatColor.RED.toString()).setScore(5);
                objective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "СПИДРАНЕРЫ ПОБЕДИЛИ").setScore(4);
                objective.getScore(ChatColor.WHITE.toString()).setScore(3);
                objective.getScore(ChatColor.GOLD + "Затраченное время : " + forma.format(getDateDiff(  starting, now, TimeUnit.MILLISECONDS))).setScore(2);
                objective.getScore(ChatColor.DARK_GREEN.toString()).setScore(1);
                objective.getScore(ChatColor.DARK_PURPLE + "              by Shinsu").setScore(0);
            }

            player.setScoreboard(scoreboard);
    }



    private String roundFadeCharacter(String msg)
    {
        if(fadeUpdater == playersList.size()){
            fadeUpdater = 0;

            msg = ChatColor.AQUA + msg;
            StringBuilder sb = new StringBuilder(msg);
            if(curr == sb.length() ){
                curr =0;
            }

            char[] arr = msg.toCharArray();
            String nwstr = "";
            String bef = "";
            String after = "";
            String character = "";
            boolean isCharacted = false;
            for(int i = 2; i < arr.length ; i++)
            {

                if(i == curr){
                    character = String.valueOf(arr[i]);
                    isCharacted = true;
                }
                else {
                    if(isCharacted == false){
                        bef = bef + String.valueOf(arr[i]);
                    }
                    else
                    {
                        after = after + String.valueOf(arr[i]);
                    }
                }

            }


            nwstr = ChatColor.GOLD + bef + ChatColor.RESET + ChatColor.AQUA + character + ChatColor.RESET + ChatColor.GOLD + after;
            curr ++;
            currDisplay = nwstr;
            return nwstr;


        }
        else
        {
            fadeUpdater++;
            if(currDisplay != null){
                return currDisplay;
            }
            else return msg;
        }



    }

    private String animatedWaiter()
    {
        String[] val = new String[]
                {
                        "▁▁▁▁▁▁",
                        "▇▁▁▁▁▁",
                        "▆▇▁▁▁▁",
                        "▅▆▇▁▁▁",
                        "▃▅▆▇▁▁",
                        "▂▃▅▆▇▁",
                        "▁▂▃▅▆▇",
                        "▁▁▁▁▁▇",
                        "▁▁▁▁▇▆",
                        "▁▁▁▇▆▅",
                        "▁▁▇▆▅▃",
                        "▁▇▆▅▃▂",
                        "▇▆▅▃▂▁",

                };

        if(currUpdater == playersList.size()) {
            currUpdater = 0;

            currWaiter++;

            if(currWaiter >= val.length)
            {
                currWaiter = 0;
                return val[0];
            }
            else
            {
                return val[currWaiter];
            }

        }
        else {
            currUpdater++;
            return val[currWaiter];
        }




    }
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime() - 10800000;
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
