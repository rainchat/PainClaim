package com.rainchat.placeprotect;

import com.google.gson.reflect.TypeToken;
import com.rainchat.placeprotect.builder.PaginationBuilder;
import com.rainchat.placeprotect.data.config.ClaimNameConfig;
import com.rainchat.placeprotect.data.config.ConfigFlags;
import com.rainchat.placeprotect.data.config.ConfigCliam;
import com.rainchat.placeprotect.data.config.LanguageFile;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.managers.FileManager;
import com.rainchat.placeprotect.managers.FlagManager;
import com.rainchat.placeprotect.managers.MenuManager;
import com.rainchat.placeprotect.resourses.commands.AdminCommands;
import com.rainchat.placeprotect.resourses.commands.ClaimCommand;
import com.rainchat.placeprotect.resourses.listeners.ConnectListener;
import com.rainchat.placeprotect.resourses.listeners.CuboidEvent;
import com.rainchat.placeprotect.resourses.listeners.MoveEvent;
import com.rainchat.placeprotect.resourses.listeners.flags.global.AnimalSpawningListener;
import com.rainchat.placeprotect.resourses.listeners.flags.global.BurnEvent;
import com.rainchat.placeprotect.resourses.listeners.flags.global.EntityListener;
import com.rainchat.placeprotect.resourses.listeners.flags.global.MonsterSpawningListener;
import com.rainchat.placeprotect.resourses.listeners.flags.local.ClaimListener;
import com.rainchat.placeprotect.resourses.listeners.flags.local.PlayerListener;
import com.rainchat.placeprotect.schedullers.ClaimBlocksTask;
import com.rainchat.placeprotect.utils.claim.ClaimWriter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.Set;
import java.util.logging.Level;

public final class PlaceProtect extends JavaPlugin {

    private static PlaceProtect instance;
    private static ClaimManager claimManager;
    private static FlagManager flagManager;
    private final MenuManager menuManager = MenuManager.getInstance();

    private ClaimBlocksTask claimBlocksTask;

    @Override
    public void onEnable() {
        instance = this;

        flagManager = FlagManager.INSTANCE;

        FileManager fileManager = FileManager.getInstance();
        fileManager.registerCustomFilesFolder("menus")
                .registerCustomFilesFolder("/language")
                .registerDefaultGenerateFiles("En_en.yml", "/language", "/language")
                .setup(this);

        ConfigCliam.setup();
        ConfigFlags.setup();
        ClaimNameConfig.setup(FileManager.Files.GENERATOR.getFile());

        LanguageFile.setConfiguration(fileManager.getFile(ConfigCliam.LANGUAGE));
        getServer().getLogger().log(Level.INFO, "Registered " + LanguageFile.addMissingMessages() + " message(s).");

        PaginationBuilder actionBuilder = PaginationBuilder.INSTANCE;


        claimManager = ClaimManager.INSTANCE;
        claimManager.load(new TypeToken<Set<PaintClaim>>() {
        }.getType());

        ClaimWriter.setup(claimManager);

        claimBlocksTask = new ClaimBlocksTask(claimManager);
        claimBlocksTask.runTaskTimer(this, 0, 20);

        //Commands register
        BukkitCommandHandler commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(
                new AdminCommands(claimManager),
                new ClaimCommand(claimManager)
        );

        //Listener register
        getServer().getPluginManager().registerEvents(new BurnEvent(claimManager), this);
        getServer().getPluginManager().registerEvents(new EntityListener(claimManager), this);
        getServer().getPluginManager().registerEvents(new AnimalSpawningListener(claimManager), this);
        getServer().getPluginManager().registerEvents(new MonsterSpawningListener(claimManager), this);

        getServer().getPluginManager().registerEvents(new CuboidEvent(claimManager), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(claimManager), this);
        getServer().getPluginManager().registerEvents(new ConnectListener(claimManager), this);
        getServer().getPluginManager().registerEvents(new ClaimListener(claimManager), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(claimManager), this);

        menuManager.setupMenus(claimManager);
    }

    @Override
    public void onDisable() {
        claimManager.unload();

        for (PaintPlayer paintPlayer: claimManager.getPlayers()) {
            paintPlayer.getVisualization().revert(paintPlayer.getPlayer());
        }

        claimBlocksTask.cancel();
    }

    public static PlaceProtect getInstance() {
        return instance;
    }

    public static ClaimManager getClaimManager() {
        return claimManager;
    }

    public static FlagManager getFlagManager() {
        return flagManager;
    }
}
