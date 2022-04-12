package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Bukkit;

import java.util.Objects;

public class PaintPlayerReplacers extends BaseReplacements<PaintPlayer> {


    private final PaintPlayer paintPlayer;


    public PaintPlayerReplacers(PaintPlayer paintPlayer) {
        super("paintplayer_");
        this.paintPlayer = paintPlayer;
    }


    @Override
    protected String getReplacement(String base, String fullKey) {
        switch (base) {
            case "is_admin_mode":
                return String.valueOf(paintPlayer.isOverriding());
            case "available_blocks":
                return String.valueOf(paintPlayer.getAvailableBlocks());
            case "bonus_blocks":
                return String.valueOf(paintPlayer.getBonusBlocks());
            case "claim_blocks":
                return String.valueOf(paintPlayer.getClaimBlocks());
            case "name":
                return paintPlayer.getPlayer().getName();

        }

        return "";
    }

    @Override
    public Class<PaintPlayer> forClass() {
        return null;
    }
}
