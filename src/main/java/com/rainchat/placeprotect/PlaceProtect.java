package com.rainchat.placeprotect;

import com.google.gson.reflect.TypeToken;

import com.rainchat.placeprotect.commands.AdminCommands;
import com.rainchat.placeprotect.commands.ClaimCommand;
import com.rainchat.placeprotect.data.village.PaintClaim;
import com.rainchat.placeprotect.data.village.PaintPlayer;
import com.rainchat.placeprotect.listeners.ConnectListener;
import com.rainchat.placeprotect.listeners.CuboidEvent;
import com.rainchat.placeprotect.listeners.MoveEvent;
import com.rainchat.placeprotect.listeners.flags.global.BurnEvent;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.utils.claim.ClaimWriter;

import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.Set;

public final class PlaceProtect extends JavaPlugin {

    public BukkitCommand testCommand;
    private static PlaceProtect instance;
    private static ClaimManager claimManager;

    @Override
    public void onEnable() {
        instance = this;



        claimManager = new ClaimManager(this);

        claimManager.load(new TypeToken<Set<PaintClaim>>() {
        }.getType());

        ClaimWriter.setup(claimManager);

        //Commands register
        //CubeCore.getAPI().getCommandManager().registerCommand(new ClaimCommand(claimManager));
        //CubeCore.getAPI().getCommandManager().registerCommand(new AdminCommands(claimManager));
        BukkitCommandHandler commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(
                new AdminCommands(claimManager),
                new ClaimCommand(claimManager)
        );

        //Listener register
        getServer().getPluginManager().registerEvents(new CuboidEvent(claimManager), this);
        getServer().getPluginManager().registerEvents(new BurnEvent(claimManager), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(claimManager), this);
        getServer().getPluginManager().registerEvents(new ConnectListener(claimManager), this);

    }

    @Override
    public void onDisable() {
        claimManager.unload();

        for (PaintPlayer paintPlayer: claimManager.getPlayers()) {
            paintPlayer.getVisualization().revert(paintPlayer.getPlayer());
        }
        // Plugin shutdown logic
    }

    public static PlaceProtect getInstance() {
        return instance;
    }

    public static ClaimManager getClaimManager() {
        return claimManager;
    }




}
