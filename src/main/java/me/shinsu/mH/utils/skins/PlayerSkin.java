package me.shinsu.mH.utils.skins;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.profile.PlayerProfile;
import org.mineacademy.fo.menu.model.SkullCreator;


public class PlayerSkin {

    private static  int slots = 9;

    public static String[] getFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[] {texture, signature};
        } catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }

    public static void  getSkullAllPlayersFromServer(Player playerInv){

        playerInv.getServer().getOnlinePlayers().forEach(player -> {
            PlayerProfile profile = player.getPlayerProfile();
            ItemStack skull = SkullCreator.itemFromUrl(profile.getTextures().getSkin().toString());
            ItemMeta meta = skull.getItemMeta();
            meta.setDisplayName(player.getDisplayName());
            skull.setItemMeta(meta);
            playerInv.getInventory().setItem(slots, skull);
            slots++;

        });

        slots = 9;

    }

}
