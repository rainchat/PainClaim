package com.rainchat.placeprotect.data.paintclaim;


import com.rainchat.placeprotect.data.claim.Cuboid;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SubClaim {

    private final String name;

    private Cuboid cuboid;
    private final Set<String> claimPermission;
    private final Set<UUID> members;
    private String role;

    public SubClaim(String name, Location loc1, Location loc2) {
        this.claimPermission = new HashSet<>();
        this.members = new HashSet<>();

        this.name = name;

        this.cuboid = new Cuboid(loc1,loc2);
    }


    public void add(String villageGlobalPermission) {
        claimPermission.add(villageGlobalPermission);
    }

    public void add(Set<String> villageGlobalPermissions) {
        claimPermission.addAll(villageGlobalPermissions);
    }

    public void add(Player player) {
        members.add(player.getUniqueId());
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public boolean containMember(Player player) {
        return members.contains(player.getUniqueId());
    }

    public String getName() {
        return name;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean hasPermission(String villageGlobalPermission) {
        return claimPermission.contains(villageGlobalPermission);
    }

    public void remove(String villageGlobalPermission) {
        claimPermission.remove(villageGlobalPermission);
    }

    public void remove(Player player) {
        members.remove(player.getUniqueId());
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

}
