package com.rainchat.placeprotect.utils.claim;

import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.data.config.ConfigCliam;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayerClaimFile {

    UUID user;
    File userFile;
    FileConfiguration userConfig;

    public PlayerClaimFile(UUID user){

        this.user = user;

        userFile = new File(PlaceProtect.getInstance().getDataFolder() + File.separator + "data" + File.separator + "players", user + ".yml");

        userConfig = YamlConfiguration.loadConfiguration(userFile);

    }

    public PaintPlayer load() {
        Player player = Bukkit.getPlayer(user);

        if (player == null) {
            return null;
        }

        if ( !(userFile.exists()) ) {
            try {

                userConfig.set("User.name", player.getName());
                userConfig.set("User.unique-id", player.getUniqueId().toString());
                userConfig.set("User.claim-blocks", ConfigCliam.START_CLAIM_BLOCKS);
                userConfig.set("User.bonus-blocks", 0);
                userConfig.set("User.available-blocks", ConfigCliam.START_CLAIM_BLOCKS);

                userConfig.save(userFile);

                PaintPlayer paintClaim = new PaintPlayer(player);
                paintClaim.setClaimBlocks(ConfigCliam.START_CLAIM_BLOCKS);
                paintClaim.setAvailableBlocks(ConfigCliam.START_CLAIM_BLOCKS);

                return paintClaim;

            } catch (Exception e) {

                e.printStackTrace();

            }
        } else {
            PaintPlayer paintClaim = new PaintPlayer(player);
            paintClaim.setClaimBlocks( userConfig.getInt("User.claim-blocks", ConfigCliam.START_CLAIM_BLOCKS));
            paintClaim.setClaimBlocks( userConfig.getInt("User.bonus-blocks", 0));
            paintClaim.setClaimBlocks( userConfig.getInt("User.available-blocks", ConfigCliam.START_CLAIM_BLOCKS));
            //paintClaim.setBonusBlocks();

            return paintClaim;


        }

        return null;
    }

    public void unLoad(PaintPlayer paintClaim) {
        try {

            userConfig.set("User.name", paintClaim.getPlayer().getName());
            userConfig.set("User.unique-id", paintClaim.getPlayer().getUniqueId());
            userConfig.set("User.claim-blocks", paintClaim.getClaimBlocks());
            userConfig.set("User.available-blocks", paintClaim.getAvailableBlocks());
            userConfig.set("User.bonus-blocks", paintClaim.getBonusBlocks());

            userConfig.save(userFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
