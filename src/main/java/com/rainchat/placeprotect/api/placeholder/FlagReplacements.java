package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.config.ConfigCliam;
import com.rainchat.placeprotect.data.flag.FlagClaim;
import com.rainchat.placeprotect.data.flag.FlagItem;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FlagReplacements extends BaseReplacements<Player> {

    private final FlagClaim flagClaim;
    private final FlagItem flagItem;
    private final boolean status;


    public FlagReplacements(FlagClaim flagClaim, FlagItem flagItem, boolean status) {
        super("flag_");

        this.flagClaim = flagClaim;
        this.flagItem = flagItem;
        this.status = status;
    }

    @Override
    public Class<Player> forClass() {
        return Player.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {


        if ("getName".equalsIgnoreCase(base)) {
            if (flagItem == null) {
                return flagClaim.getDisplayName();
            }
            return flagItem.getName();
        }
        if ("getMaterial".equalsIgnoreCase(base)) {
            if (flagItem == null) {
                return flagClaim.getItem().getType().toString();
            }
            return flagItem.getMaterial().toString();
        }
        if ("getStatus".equalsIgnoreCase(base)) {
            if (status) {
                return ConfigCliam.PLACEHOLDERS.get("enable-flag");
            }
            return ConfigCliam.PLACEHOLDERS.get("disable-flag");
        }

        return "";
    }

    public Material getMaterial() {
        if (flagItem == null) {
            return  flagClaim.getItem().getType();
        }
        return flagItem.getMaterial();
    }


}
