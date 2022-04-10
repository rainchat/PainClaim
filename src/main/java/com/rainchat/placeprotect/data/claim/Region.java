package com.rainchat.placeprotect.data.claim;

import com.rainchat.placeprotect.utils.claim.ClaimCorner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Region {
    private final String world;
    private final int minX;
    private final int maxX;
    private final int minZ;
    private final int maxZ;

    public Region(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockZ());
    }

    public Region(World world, int x1, int z1, int x2, int z2) {
        this.world = world.getName();

        minX = Math.min(x1, x2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxZ = Math.max(z1, z2);
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public int getLeft() {
        return Math.min(getMaxX(), getMinX());
    }

    public int getTop() {
        return Math.max(getMaxZ(), getMinZ());
    }

    public int getRight() {
        return Math.max(getMaxX(), getMinX());
    }

    public int getBottom() {
        return Math.min(getMaxZ(), getMinZ());
    }

    public boolean isCorner(Location loc) {
        return (loc.getBlockX() == getLeft() || loc.getBlockX() == getRight()) && (loc.getBlockZ() == getTop() || loc.getBlockZ() == getBottom());
    }

    public Vector getCorner(ClaimCorner corner) {
        switch (corner) {
            case TOP_LEFT: return new Vector(getLeft(), 0, getTop());
            case TOP_RIGHT: return new Vector(getRight(), 0, getTop());
            case BOTTOM_LEFT: return new Vector(getLeft(), 0, getBottom());
            case BOTTOM_RIGHT: return new Vector(getRight(), 0, getBottom());
        }
        return null;
    }

    public ClaimCorner getCorner(Location loc) {
        for (ClaimCorner corner : ClaimCorner.values()) {
            Vector cornerLoc = getCorner(corner);
            if (cornerLoc.getBlockX() == loc.getBlockX() && cornerLoc.getBlockZ() == loc.getBlockZ())
                return corner;
        }
        return null;
    }

    public boolean acceptableSize(int min, int max) {
        int xDim = (int) Math.ceil(Math.abs(getMaxX() - getMinX()+1)); //Might be Math.floor for all I know, you do the testing
        int zDim =(int) Math.ceil(Math.abs(getMaxZ() - getMinZ()+1));

        return (xDim > min && xDim < max) && (zDim > min && zDim < max);
    }

    public String getSize() {
        int xDim = (int) Math.ceil(Math.abs(getMaxX() - getMinX()+1)); //Might be Math.floor for all I know, you do the testing
        int zDim =(int) Math.ceil(Math.abs(getMaxZ() - getMinZ()+1));

        return xDim + "x" + zDim;
    }

    public int getValueSize() {
        int xDim = (int) Math.ceil(Math.abs(getMaxX() - getMinX()+1)); //Might be Math.floor for all I know, you do the testing
        int zDim =(int) Math.ceil(Math.abs(getMaxZ() - getMinZ()+1));

        return xDim * zDim;
    }


    public boolean contains(Region region) {
        return region.getWorld().equals(getWorld()) &&
                region.getMinX() >= minX && region.getMaxX() <= maxX &&
                region.getMinZ() >= minZ && region.getMaxZ() <= maxZ;
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockZ());
    }

    public boolean contains(int x, int z) {
        return x >= minX && x <= maxX &&
                z >= minZ && z <= maxZ;
    }

    public boolean overlaps2(Region region) {
        return region.getWorld().equals(getWorld()) &&
                region.getMaxX() >= minX &&
                region.getMinX() <= maxX &&
                region.getMinZ() <= maxZ &&
                region.getMaxZ() >= minZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Region)) {
            return false;
        }
        final Region other = (Region) obj;
        return getWorld().equals(other.getWorld())
                && minX == other.minX
                && minZ == other.minZ
                && maxX == other.maxX
                && maxZ == other.maxZ;
    }

    @Override
    public String toString() {
        return "Region[world:" + world +
                ", minX:" + minX +
                ", minZ:" + minZ +
                ", maxX:" + maxX +
                ", maxZ:" + maxZ + "]";
    }
}