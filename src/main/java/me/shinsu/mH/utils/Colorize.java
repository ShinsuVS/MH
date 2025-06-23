package me.shinsu.mH.utils;

import org.bukkit.ChatColor;

public class Colorize {

    public static String ColorString(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
