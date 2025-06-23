package me.shinsu.mH.menu.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupSettings {

    @Setter
    @Getter
    private List<String> speedRunners = new ArrayList<>();
    @Setter
    @Getter
    private List<String> hunters = new ArrayList<>();
    @Setter
    @Getter
    private Map<Integer, ItemStack> itemCollectiom = new HashMap<>();
    @Setter
    @Getter
    private boolean enableCustomItem = false;
    @Setter
    @Getter
    private boolean timerEnabled = false;
    @Setter
    @Getter
    private int timerSecondsBlind = 10;
    @Setter
    @Getter
    private boolean ScoreBoardEnabled = false;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("SR [");
        for(int i = 0; i< speedRunners.size(); i++){
            if(i == 0){
                string.append(speedRunners.get(i));
            }
            else {
                string.append(", " + speedRunners.get(i) );
            }

        }
        string.append("] HR [");

        for(int i = 0; i< hunters.size(); i++){
            if(i == 0){
                string.append(hunters.get(i));
            }
            else {
                string.append(", " + hunters.get(i) );
            }

        }
        string.append("] Custom Items Enabled [");
        string.append(enableCustomItem);
        string.append("] Start Timer Enabled [");
        string.append(timerEnabled);
        string.append("] ScoreBoard Enabled [");
        string.append(ScoreBoardEnabled);
        string.append("]");
        if (timerEnabled){
            string.append("Duration [" + String.valueOf(timerSecondsBlind) + " seconds ]");
        }


        return string.toString();
    }


    //DEFAULT ? VALUE



}
