package com.rainchat.placeprotect.data.paintclaim;


import com.rainchat.placeprotect.data.claim.Region;
import com.rainchat.placeprotect.data.enums.ClaimPermission;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PaintClaim {

    private final UUID id;
    private String name, description;
    private UUID owner;
    private long lastActive;

    private boolean adminClaim;

    private Region region;

    private final Set<ClaimMember> claimMembers;
    private final Set<SubClaim> subClaims;
    private final Set<String> villagePermissions;

    public PaintClaim(String name, UUID owner, UUID id) {
        this.id = id;
        this.name = name;
        this.owner = owner;

        adminClaim = owner == null;

        this.claimMembers = new HashSet<>();

        this.subClaims = new HashSet<>();
        this.villagePermissions = new HashSet<>();
        this.lastActive = System.currentTimeMillis();

    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public void add(ClaimMember claimMember) {
        claimMembers.add(claimMember);
    }

    public void add(SubClaim subClaim) {
        subClaims.add(subClaim);
    }

    public void add(String villageGlobalPermission) {
        villagePermissions.add(villageGlobalPermission);
    }

    public void add(Set<String> villageGlobalPermissions) {
        villagePermissions.addAll(villageGlobalPermissions);
    }

    public void add(long time) {
        lastActive = time;
    }

    public void remove(ClaimMember claimMember) {
        claimMembers.remove(claimMember);
    }

    public void remove(SubClaim subClaim) {
        subClaims.remove(subClaim);
    }

    public void remove(String villageGlobalPermission) {
        villagePermissions.remove(villageGlobalPermission);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubClaim> getVillageSubRegions() {
        return subClaims;
    }

    public void setAdminClaim(boolean adminClaim) {
        this.adminClaim = adminClaim;
    }

    public boolean hasPermission(ClaimPermission villagePermission, UUID uuid) {
        ClaimMember claimMember = getMember(uuid);
        if (claimMember != null) {
            return !claimMember.hasPermission(villagePermission);
        }
        return false;
    }

    public boolean hasPermission(String villageGlobalPermission, Location location) {
        for (SubClaim villageSubClaim : subClaims) {
            if (villageSubClaim.getCuboid().contains(location)) {
                return villageSubClaim.hasPermission(villageGlobalPermission);
            }
        }
        return villagePermissions.contains(villageGlobalPermission);
    }


    public boolean containsSubCuboid(String cuboid) {
        for (SubClaim subClaim : subClaims) {
            if (subClaim.getName().equalsIgnoreCase(cuboid)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsID(UUID uuid) {
        return id.equals(uuid);
    }

    public boolean hasPermission(String villageGlobalPermission) {
        return villagePermissions.contains(villageGlobalPermission);
    }

    public boolean isAdminClaim() {
        return adminClaim;
    }

    public boolean hasMember(Player player) {
        for (ClaimMember claimMember : claimMembers) {
            if (claimMember.getUniqueId().equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public ClaimMember getMember(UUID uuid) {
        for (ClaimMember claimMember : claimMembers) {
            if (claimMember.getUniqueId().equals(uuid)) {
                return claimMember;
            }
        }
        return null;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean isOwner(String uuid) {
        if (owner == null) return false;
        return uuid.equals(owner.toString());
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Set<ClaimMember> getVillageMembers() {
        return Collections.unmodifiableSet(claimMembers);
    }

    public SubClaim getSubClaim(String name) {
        for (SubClaim subClaim : subClaims) {
            if (subClaim.getName().equals(name)) {
                return subClaim;
            }
        }
        return null;
    }

    public Long getLastActive() {
        return lastActive;
    }
}
