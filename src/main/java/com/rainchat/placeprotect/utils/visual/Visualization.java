package com.rainchat.placeprotect.utils.visual;

import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.data.claim.Region;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Visualization {

    private List<VisualizationElement> elements = new ArrayList<>();

    public List<VisualizationElement> getElements() {
        return elements;
    }

    public void apply(Player player) {
        //if he has any current visualization, clear it first

        if (PlaceProtect.getClaimManager().getPlayerData(player).getVisualization() != null) {
            PlaceProtect.getClaimManager().getPlayerData(player).getVisualization().revert(player);
        }

        //if they are online, create a task to send them the visualization
        if (player.isOnline() && elements.size() > 0 && Objects.equals(elements.get(0).location.getWorld(), player.getWorld())) {
            PlaceProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(PlaceProtect.getInstance(), new VisualizationTaskApply(player, this), 5L);
        }
    }

    public void revert(Player player) {
        if (!player.isOnline()) return;

        Visualization visualization = PlaceProtect.getClaimManager().getPlayerData(player).getVisualization();

        if (visualization != null) {

            //send real block information for any remaining elements
            for (VisualizationElement element: elements) {
                element.getLocation().getBlock().getState().update();
            }

            PlaceProtect.getClaimManager().getPlayerData(player).removeVisualization();
        }
    }

    //convenience method to build a visualization from a claim
    //visualizationType determines the style (gold blocks, silver, red, diamond, etc)
    public static Visualization fromClaim(Region claim, int height, VisualizationType visualizationType, Location locality) {
        //visualize only top level claims
        /*if (claim.parent != null) {
            return FromClaim(claim.parent, height, visualizationType, locality);
        }*/

        Visualization visualization = new Visualization();


        Location max = new Location(claim.getWorld(),claim.getMaxX(),height,claim.getMaxZ());
        Location min = new Location(claim.getWorld(),claim.getMinX(),height,claim.getMinZ());
        //add top level last so that it takes precedence (it shows on top when the child claim boundaries overlap with its boundaries)
        visualization.addElements(min, max, height, visualizationType, locality);

        return visualization;
    }

    public static Visualization fromClaim(List<Region> regions, int height, VisualizationType visualizationType, Location locality) {
        //visualize only top level claims
        /*if (claim.parent != null) {
            return FromClaim(claim.parent, height, visualizationType, locality);
        }*/

        Visualization visualization = new Visualization();


        for (Region claim: regions) {
            Location max = new Location(claim.getWorld(), claim.getMaxX(), height, claim.getMaxZ());
            Location min = new Location(claim.getWorld(), claim.getMinX(), height, claim.getMinZ());
            //add top level last so that it takes precedence (it shows on top when the child claim boundaries overlap with its boundaries)
            visualization.addElements(min, max, height, visualizationType, locality);
        }

        return visualization;
    }

    //convenience method to build a visualization from a location
    //visualizationType determines the style (gold blocks, silver, red, diamond, etc)
    public static Visualization fromLocation(Visualization visualization, Location location, int height, Location locality) {
        //add top level last so that it takes precedence (it shows on top when the child claim boundaries overlap with its boundaries)
        visualization.addSides(location, location, locality, height, Material.EMERALD_BLOCK.createBlockData(), Material.EMERALD_BLOCK.createBlockData(), 1);

        return visualization;
    }

    public static Visualization fromLocation(Location location, int height, Location locality) {
        return fromLocation(new Visualization(), location, height, locality);
    }

    public static Visualization fromLocation(Location min, Location max, int height, VisualizationType visualizationType, Location locality) {

        Visualization visualization = new Visualization();

        //add top level last so that it takes precedence (it shows on top when the child claim boundaries overlap with its boundaries)
        visualization.addElements(min, max, height, visualizationType, locality);

        return visualization;
    }

    //adds a claim's visualization to the current visualization
    //handy for combining several visualizations together, as when visualization a top level claim with several subdivisions inside
    //locality is a performance consideration.  only create visualization blocks for around 100 blocks of the locality

    private void addElements(Location min, Location max, int height, VisualizationType visualizationType, Location locality) {
        BlockData cornerBlockData = Material.GLOWSTONE.createBlockData();
        BlockData accentBlockData;

        switch (visualizationType) {
            case CLAIM: accentBlockData = Material.EMERALD_BLOCK.createBlockData(); break;
            case OVERLAPS: accentBlockData = Material.REDSTONE_BLOCK.createBlockData(); break;
            case ADMIN_CLAIM: accentBlockData = Material.COAL_BLOCK.createBlockData(); break;
            case CLAIM_SUB: accentBlockData = Material.WHITE_WOOL.createBlockData(); break;
            case EDIT: accentBlockData = Material.BONE_BLOCK.createBlockData(); break;
            case AUCTION: accentBlockData = Material.GOLD_BLOCK.createBlockData(); break;
            default: //Errors
                //((Lightable) cornerBlockData).setLit(true); //For redstone
                accentBlockData = Material.NETHERRACK.createBlockData();
                break;
        }
        int step;
        switch (visualizationType) {
            case ERROR_LARGE:
            case ERROR_SMALL: step = 1; break;
            case EDIT: step = 1; break;
            default: step = 1;
        }
        addSides(min, max, locality, height, cornerBlockData, accentBlockData, step);
    }

    //adds a general claim cuboid (represented by min and max) visualization to the current visualization
    void addSides(Location min, Location max, Location locality, int height, BlockData cornerBlockData, BlockData accentBlockData, int STEP) {
        World world = min.getWorld();
        boolean waterIsTransparent = locality.getBlock().getType() == Material.WATER;

        int smallx = min.getBlockX();
        int smallz = min.getBlockZ();
        int bigx = max.getBlockX();
        int bigz = max.getBlockZ();

        ArrayList<VisualizationElement> newElements = new ArrayList<>();

        //initialize visualization elements without Y values and real data
        //that will be added later for only the visualization elements within visualization range

        //locality
        int minx = locality.getBlockX() - 75;
        int minz = locality.getBlockZ() - 75;
        int maxx = locality.getBlockX() + 75;
        int maxz = locality.getBlockZ() + 75;

        //top line
        newElements.add(new VisualizationElement(new Location(world, smallx, 0, bigz), cornerBlockData, Material.AIR.createBlockData()));
        newElements.add(new VisualizationElement(new Location(world, smallx + 1, 0, bigz), accentBlockData, Material.AIR.createBlockData()));
        for (int x = smallx + STEP; x < bigx - STEP / 2; x += STEP) {
            if (x > minx && x < maxx)
                newElements.add(new VisualizationElement(new Location(world, x, 0, bigz), accentBlockData, Material.AIR.createBlockData()));
        }
        newElements.add(new VisualizationElement(new Location(world, bigx - 1, 0, bigz), accentBlockData, Material.AIR.createBlockData()));

        //bottom line
        newElements.add(new VisualizationElement(new Location(world, smallx + 1, 0, smallz), accentBlockData, Material.AIR.createBlockData()));
        for (int x = smallx + STEP; x < bigx - STEP / 2; x += STEP) {
            if (x > minx && x < maxx)
                newElements.add(new VisualizationElement(new Location(world, x, 0, smallz), accentBlockData, Material.AIR.createBlockData()));
        }
        newElements.add(new VisualizationElement(new Location(world, bigx - 1, 0, smallz), accentBlockData, Material.AIR.createBlockData()));

        //left line
        newElements.add(new VisualizationElement(new Location(world, smallx, 0, smallz), cornerBlockData, Material.AIR.createBlockData()));
        newElements.add(new VisualizationElement(new Location(world, smallx, 0, smallz + 1), accentBlockData, Material.AIR.createBlockData()));
        for (int z = smallz + STEP; z < bigz - STEP / 2; z += STEP) {
            if (z > minz && z < maxz)
                newElements.add(new VisualizationElement(new Location(world, smallx, 0, z), accentBlockData, Material.AIR.createBlockData()));
        }
        newElements.add(new VisualizationElement(new Location(world, smallx, 0, bigz - 1), accentBlockData, Material.AIR.createBlockData()));

        //right line
        newElements.add(new VisualizationElement(new Location(world, bigx, 0, smallz), cornerBlockData, Material.AIR.createBlockData()));
        newElements.add(new VisualizationElement(new Location(world, bigx, 0, smallz + 1), accentBlockData, Material.AIR.createBlockData()));
        for (int z = smallz + STEP; z < bigz - STEP / 2; z += STEP) {
            if (z > minz && z < maxz)
                newElements.add(new VisualizationElement(new Location(world, bigx, 0, z), accentBlockData, Material.AIR.createBlockData()));
        }
        newElements.add(new VisualizationElement(new Location(world, bigx, 0, bigz - 1), accentBlockData, Material.AIR.createBlockData()));
        newElements.add(new VisualizationElement(new Location(world, bigx, 0, bigz), cornerBlockData, Material.AIR.createBlockData()));

        //remove any out of range elements
        this.removeElementsOutOfRange(newElements, minx, minz, maxx, maxz);

        //remove any elements outside the claim
        BoundingBox box = BoundingBox.of(min, max);
        for (int i = 0; i < newElements.size(); i++) {
            VisualizationElement element = newElements.get(i);
            if (!containsIncludingIgnoringHeight(box, element.location.toVector())) {
                newElements.remove(i--);
            }
        }

        //set Y values and real block information for any remaining visualization blocks
        for (VisualizationElement element : newElements) {
            Location tempLocation = element.location;
            element.location = getVisibleLocation(tempLocation.getWorld(), tempLocation.getBlockX(), height, tempLocation.getBlockZ(), waterIsTransparent);
            height = element.location.getBlockY();
            element.realBlock = element.location.getBlock().getBlockData();
        }

        this.elements.addAll(newElements);
    }

    //finds a block the player can probably see.  this is how visualizations "cling" to the ground or ceiling
    private static Location getVisibleLocation(World world, int x, int y, int z, boolean waterIsTransparent) {
        Block block = world.getBlockAt(x, y, z);
        BlockFace direction = (isTransparent(block, waterIsTransparent)) ? BlockFace.DOWN : BlockFace.UP;

        while (block.getY() >= 1 &&
                block.getY() < world.getMaxHeight() - 1 &&
                (!isTransparent(block.getRelative(BlockFace.UP), waterIsTransparent) || isTransparent(block, waterIsTransparent)))
        {
            block = block.getRelative(direction);
        }

        return block.getLocation();
    }

    //helper method for above.  allows visualization blocks to sit underneath partly transparent blocks like grass and fence
    private static boolean isTransparent(Block block, boolean waterIsTransparent) {
        Material blockMaterial = block.getType();
        //Blacklist
        switch (blockMaterial) {
            case SNOW:
                return false;
        }

        //Whitelist TODO: some of this might already be included in isTransparent()
        switch (blockMaterial) {
            case AIR:
            case OAK_FENCE:
            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case DARK_OAK_FENCE:
            case JUNGLE_FENCE:
            case NETHER_BRICK_FENCE:
            case SPRUCE_FENCE:
            case OAK_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
                return true;
        }

        return (waterIsTransparent && block.getType() == Material.WATER) ||
                block.getType().isTransparent();
    }

    private boolean containsIncludingIgnoringHeight(BoundingBox box, Vector vector) {
        return vector.getBlockX() >= box.getMinX()
                && vector.getBlockX() <= box.getMaxX()
                && vector.getBlockZ() >= box.getMinZ()
                && vector.getBlockZ() <= box.getMaxZ();
    }

    //removes any elements which are out of visualization range
    private void removeElementsOutOfRange(List<VisualizationElement> elements, int minx, int minz, int maxx, int maxz) {
        for (int i = 0; i < elements.size(); i++) {
            Location location = elements.get(i).location;
            if (location.getX() < minx || location.getX() > maxx || location.getZ() < minz || location.getZ() > maxz) {
                elements.remove(i--);
            }
        }
    }

}
