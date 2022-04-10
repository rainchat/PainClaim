package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.paintclaim.ClaimMember;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MemberReplacements extends BaseReplacements<Player> {

    private final ClaimMember claimMember;


    public MemberReplacements(ClaimMember claimMember) {
        super("member_");

        this.claimMember = claimMember;

    }


    @Override
    public Class<Player> forClass() {
        return Player.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {

        if ("uuid".equals(base)) {
            return claimMember.getUniqueId().toString();
        }
        if ("name".equals(base)) {
            return Bukkit.getOfflinePlayer(claimMember.getUniqueId()).getName();
        }

        return "";
    }

}