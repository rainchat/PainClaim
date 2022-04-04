package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.village.PaintClaim;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class ClaimReplcements extends BaseReplacements<PaintClaim> {

    private final PaintClaim paintClaim;


    public ClaimReplcements(PaintClaim paintClaim) {
        super("claim_");

        this.paintClaim = paintClaim;
    }

    @Override
    public Class<PaintClaim> forClass() {
        return PaintClaim.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {


        switch (base) {
            case "name":
                return paintClaim.getName();

            case "uuid":
                return paintClaim.getId().toString();

            case "owner":
                return Objects.requireNonNull(Bukkit.getPlayer(paintClaim.getOwner())).getName();

        }

        return "";
    }

}
