package com.rainchat.placeprotect.resourses.commands;


import com.rainchat.placeprotect.data.enums.ClaimPermission;
import com.rainchat.placeprotect.data.paintclaim.ClaimMember;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;

import com.rainchat.placeprotect.managers.MenuManager;
import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.placeprotect.utils.claim.ClaimWriter;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.HashMap;

@Command("claim")
public class ClaimCommand {

    private final ClaimManager claimManager;

    public ClaimCommand(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Subcommand("list")
    @CommandPermission("paintclain.commands.list")
    public void onList(Player player) {
        HashMap<String,String> param = new HashMap<>();
        param.put("target", player.getName());
        MenuManager.getInstance().openMenu(player,"test", param);
    }

    @Subcommand("claim")
    @CommandPermission("paintclain.commands.claim")
    public void onClaim(Player player, int radius) {
        PaintPlayer paintPlayer = claimManager.loadPlayerData(player);
        paintPlayer.getClaimInteraction().setMode(ClaimMode.CREATE);
        paintPlayer.getClaimInteraction().setPos1(player.getLocation().add(radius,0,radius));
        paintPlayer.getClaimInteraction().setPos2(player.getLocation().add(-radius,0,-radius));

        ClaimWriter.createClaim(paintPlayer, player);

    }


    @Subcommand("panel")
    @CommandPermission("paintclain.commands.panel")
    public void onPanel(Player player) {
        PaintClaim paintClaim = claimManager.getClaim(player.getLocation());
        PaintPlayer paintPlayer = claimManager.loadPlayerData(player);

        if (paintClaim == null) {
            return;
        }

        if (paintClaim.isOwner(player.getUniqueId().toString()) ||
        paintPlayer.isOverriding() ||
        paintClaim.hasMember(player)) {
            if (paintClaim.hasMember(player)) {
                if (paintClaim.getMember(player.getUniqueId()).hasPermission(ClaimPermission.PANEL)) {
                    return;
                }
            }
            HashMap<String,String> param = new HashMap<>();
            param.put("claim", paintClaim.getId().toString());
            MenuManager.getInstance().openMenu(player,"main", param);
        }
    }

    @Subcommand("info")
    @CommandPermission("paintclain.commands.info")
    public void onInfo(Player player) {
        PaintPlayer paintPlayer = claimManager.loadPlayerData(player);
        PaintClaim paintClaim = claimManager.getClaim(player.getLocation());

        player.sendMessage("Доступных блоков " + paintPlayer.getAvailableBlocks());
        player.sendMessage("Текущие блоки " + paintPlayer.getClaimBlocks());
        player.sendMessage("Доп блоки " + paintPlayer.getBonusBlocks());
        if (paintClaim == null) {
            player.sendMessage("Вы не в регионе");
        } else {
            player.sendMessage("Вы в регионе");
        }

    }

    @Subcommand("add")
    @CommandPermission("paintclain.commands.add")
    public void onInfo(Player player, Player target) {
        PaintPlayer paintPlayer = claimManager.loadPlayerData(player);
        PaintClaim paintClaim = claimManager.getClaim(player.getLocation());

        if (paintClaim != null) {
            if (paintClaim.isOwner(player.getUniqueId().toString())) {
                player.sendMessage("Вы добавили игрока");
                paintClaim.add(new ClaimMember(target.getUniqueId()));
            } else {
                player.sendMessage("Вы не добавили игрока");
            }
        } else {
            player.sendMessage("Вы в регионе");
        }

    }


}
