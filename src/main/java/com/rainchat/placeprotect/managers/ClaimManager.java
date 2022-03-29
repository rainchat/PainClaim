package com.rainchat.placeprotect.managers;

import com.rainchat.placeprotect.data.claim.Region;
import com.rainchat.placeprotect.data.village.PaintClaim;
import com.rainchat.placeprotect.data.village.PaintPlayer;
import com.rainchat.placeprotect.utils.Manager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ClaimManager extends Manager<PaintClaim> {

    private final Plugin plugin;

    private final Map<UUID, PaintPlayer> paintPlayers = new HashMap<>();

    public ClaimManager(Plugin plugin) {
        super("date/regions", plugin);
        this.plugin = plugin;
    }


    public void removeVillagePlayer(Player player) {
       paintPlayers.remove(player.getUniqueId());
    }

    public PaintClaim getVillage(String string) {
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.getName().equalsIgnoreCase(string)) {
                return paintClaim;
            }
        }
        return null;
    }

    public UUID generateUUID() {
        UUID uuid = UUID.randomUUID();
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.containsID(uuid)) {
                return generateUUID();
            }
        }
        return uuid;
    }

    public PaintPlayer getPlayerData(Player player) {
        return paintPlayers.computeIfAbsent(player.getUniqueId(), k -> new PaintPlayer(player));
    }

    public List<Region> regionOverlaps(Region region) {
        List<Region> regions = new ArrayList<>();

        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.getRegion().overlaps2(region)) {
                regions.add(paintClaim.getRegion());
            }
        }
        return regions;
    }

    public void updateLastActive(PaintClaim paintClaim, Long time) {
        paintClaim.add(time);
    }

    public Long getActive(PaintClaim paintClaim) {
        return paintClaim.getLastActive();
    }


    public void deleteNonActive() {
        int i = 0;
        long time_expiration = 10 * 1000 * 60 * 60 * 24;
        for (PaintClaim paintClaim : toSet()) {
            if (System.currentTimeMillis() - paintClaim.getLastActive() > time_expiration) {
                i++;
                remove(paintClaim);
            }
        }

        plugin.getLogger().info("Removed " + i + " inactive claim!");
    }


    public List<PaintClaim> getClaims(Player player) {
        List<PaintClaim> strings = new ArrayList<>();
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.getOwner().equals(player.getUniqueId())) {
                strings.add(paintClaim);
            }
        }
        return strings;
    }

    public Set<PaintClaim> getClaims() {
        return toSet();
    }

    public Collection<PaintPlayer> getPlayers() {
        return paintPlayers.values();
    }


    public PaintClaim getClaim(Player player, Location location) {
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.isOwner(player.getUniqueId()) && paintClaim.getRegion().contains(location)) {
                return paintClaim;
            }
        }
        return null;
    }

    public PaintClaim getClaim(Location location) {
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.getRegion().contains(location)) {
                return paintClaim;
            }
        }
        return null;
    }



    public List<PaintClaim> getArray() {
        return new ArrayList<>(toSet());
    }

}
