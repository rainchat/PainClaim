package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.config.ConfigCliam;
import com.rainchat.placeprotect.data.enums.ClaimPermission;
import com.rainchat.placeprotect.data.flag.FlagClaim;
import com.rainchat.placeprotect.data.flag.FlagItem;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PermissionReplacements extends BaseReplacements<Player> {

    private final ClaimPermission claimPermission;
    private final boolean status;


    public PermissionReplacements(ClaimPermission claimPermission, boolean status) {
        super("permission_");

        this.claimPermission = claimPermission;
        this.status = status;
    }

    @Override
    public Class<Player> forClass() {
        return Player.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {


        if ("getName".equalsIgnoreCase(base)) {
            return claimPermission.name();
        }
        if ("getStatus".equalsIgnoreCase(base)) {
            if (status) {
                return ConfigCliam.PLACEHOLDERS.get("enable-flag");
            }
            return ConfigCliam.PLACEHOLDERS.get("disable-flag");
        }

        return "";
    }

}
