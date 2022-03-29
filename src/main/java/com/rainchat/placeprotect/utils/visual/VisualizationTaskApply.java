package com.rainchat.placeprotect.utils.visual;

import com.rainchat.placeprotect.PlaceProtect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class VisualizationTaskApply implements Runnable
{
    private final Visualization visualization;
    private final Player player;

    public VisualizationTaskApply(Player player, Visualization visualization) {
        this.visualization = visualization;
        this.player = player;
    }


    @Override
    public void run() {
        //for each element (=block) of the visualization
        for (VisualizationElement element: visualization.getElements()) {
            //send the player a fake block change event
            if (!element.location.getChunk().isLoaded()) continue;  //cheap distance check
            player.sendBlockChange(element.location, element.fakeBlock);
        }
        //schedule automatic visualization reversion in 60 seconds.
        PlaceProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(
                PlaceProtect.getInstance(),
                new VisualizationTaskRevert(player, visualization),
                20L * 30);  //30 seconds
    }
}
