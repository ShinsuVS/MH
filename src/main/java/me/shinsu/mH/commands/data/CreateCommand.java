package me.shinsu.mH.commands.data;

import com.gmail.nossr50.placeholders.SkillExpNeededPlaceholder;
import me.shinsu.mH.MH;
import me.shinsu.mH.commands.SubCommand;
import me.shinsu.mH.menu.SetupMenu;
import me.shinsu.mH.utils.Colorize;
import me.shinsu.mH.utils.skins.PlayerSkin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.SkullCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.List;

import static me.shinsu.mH.utils.skins.PlayerSkin.getSkullAllPlayersFromServer;

public class CreateCommand extends SubCommand<Player> {

    public CreateCommand(MH plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player sender, String[] args) {
        new SetupMenu(getPlugin()).displayTo(sender);
        getSkullAllPlayersFromServer(sender);


    }





    @Override
    public String getName() {
        return "create";
    }
}
