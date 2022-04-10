package com.rainchat.placeprotect.data.paintclaim;

import com.rainchat.placeprotect.data.enums.ClaimPermission;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClaimMember {

    private final UUID uuid;
    private long cooldown = 0;
    private final Set<ClaimPermission> claimPermissions;

    public ClaimMember(UUID uuid) {
        this.uuid = uuid;
        this.claimPermissions = new HashSet<>();
    }

    public void add(ClaimPermission villagePermission) {
        claimPermissions.add(villagePermission);
    }


    public void remove(ClaimPermission villagePermission) {
        claimPermissions.remove(villagePermission);
    }

    public boolean hasPermission(ClaimPermission villagePermission) {
        return claimPermissions.contains(villagePermission);
    }

    public boolean hasCooldown() {
        return (cooldown > System.currentTimeMillis());
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long time) {
        this.cooldown = System.currentTimeMillis() + (time * 1000);
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
