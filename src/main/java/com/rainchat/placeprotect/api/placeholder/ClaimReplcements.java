package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Bukkit;

import java.util.Objects;

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
                if (paintClaim.isAdminClaim()) {
                    return "admin";
                }
                return Objects.requireNonNull(Bukkit.getPlayer(paintClaim.getOwner())).getName();
            case "owner_uuid":
                if (paintClaim.isAdminClaim()) {
                    return "admin";
                }
                return paintClaim.getOwner().toString();
            case "size":
                return paintClaim.getRegion().getSize();
            case "value":
                return String.valueOf(paintClaim.getRegion().getValueSize());

        }

        return "";
    }

}
