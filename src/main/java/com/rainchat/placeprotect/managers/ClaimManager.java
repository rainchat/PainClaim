package com.rainchat.placeprotect.managers;

import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.data.claim.Region;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.utils.Manager;
import com.rainchat.placeprotect.utils.claim.PlayerClaimFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ClaimManager extends Manager<PaintClaim> {

    private final Plugin plugin;

    private final Map<UUID, PaintPlayer> paintPlayers = new HashMap<>();
    private final Map<UUID, PlayerClaimFile> paintClaimFiles = new HashMap<>();

    public static final ClaimManager INSTANCE = new ClaimManager(PlaceProtect.getInstance());

    private ClaimManager(Plugin plugin) {
        super("data/claims/regions", plugin);
        this.plugin = plugin;
    }


    public void removeVillagePlayer(Player player) {
       paintPlayers.remove(player.getUniqueId());
    }

    public PaintClaim getClaim(UUID string) {
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.getId().equals(string)) {
                return paintClaim;
            }
        }
        Inventory inventory = null;
        inventory.getType().name();
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

    public PaintPlayer loadPlayerData(Player player) {
        if (!paintPlayers.containsKey(player.getUniqueId())) {
            PlayerClaimFile playerClaimFile = new PlayerClaimFile(player.getUniqueId());
            PaintPlayer paintPlayer = playerClaimFile.load();
            paintPlayers.put(player.getUniqueId(),paintPlayer);
            return paintPlayer;
        }
        return paintPlayers.computeIfAbsent(player.getUniqueId(), k -> new PaintPlayer(player));
    }

    public void unLoadPlayerData(Player player) {

        paintClaimFiles.get(player.getUniqueId()).unLoad(loadPlayerData(player));

        paintPlayers.remove(player.getUniqueId());
        paintClaimFiles.remove(player.getUniqueId());
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
            if (paintClaim.isOwner(player.getUniqueId().toString())) {
                strings.add(paintClaim);
            }
        }
        return strings;
    }

    public Set<PaintClaim> getClaims() {
        return toSet();
    }

    public void remove(UUID uuid) {
        for (PaintClaim paintClaim: toSet()) {
            if (paintClaim.getId().equals(uuid)) {
                remove(paintClaim);
                return;
            }
        }
    }

    public Collection<PaintPlayer> getPlayers() {
        return paintPlayers.values();
    }


    public PaintClaim getClaim(Player player, Location location) {
        for (PaintClaim paintClaim : toSet()) {
            if (paintClaim.isOwner(player.getUniqueId().toString()) && paintClaim.getRegion().contains(location)) {
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
