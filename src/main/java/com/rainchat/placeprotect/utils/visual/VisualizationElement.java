package com.rainchat.placeprotect.utils.visual;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public class VisualizationElement {

    Location location;
    BlockData fakeBlock;
    BlockData realBlock;

    public VisualizationElement(Location location, BlockData visualizedBlock, BlockData realBlock){
        this.location = location;
        this.fakeBlock = visualizedBlock;
        this.realBlock = realBlock;
    }

    public Location getLocation() {
        return location;
    }

    public BlockData getFakeBlock() {
        return fakeBlock;
    }

    public BlockData getRealBlock() {
        return realBlock;
    }
}
