package com.rainchat.placeprotect.data.config;

import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.managers.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ConfigCliam {

    public static String LANGUAGE;
    public static boolean ECONOMY_ENABLE;
    public static List<String> REGIONS_WORLD_GUARD;


    public static long CLAIM_EXPIRATION_CLAIM_TIME;

    public static List<String> CLAIM_ENABLED_WORLDS_LIST;

    public static int CLAIM_DEFAULT_CLAIM_LIMIT;
    public static int CLAIM_MIN_SIZE;
    public static int CLAIM_MAX_SIZE;

    public static int START_CLAIM_BLOCKS;
    public static int MAX_BLOCK_BONUS;
    public static int ADD_BLOCK_PER_MINUTE;

    public static int SUB_DEFAULT_CLAIM_LIMIT;
    public static int SUB_CLAIM_ADD_PER_MEMBER;

    public static HashMap<String, String> PLACEHOLDERS;


    public static int CREATE_MONEY_TAKE;
    public static int CLAIM_MONEY_TAKE;


    public static void setup() {
        PLACEHOLDERS = new HashMap<>();
        FileConfiguration config = FileManager.Files.CONFIG.getFile();
        LANGUAGE = config.getString("Settings-Global.default-language", "en_En");
        createLanguage();
        REGIONS_WORLD_GUARD = config.getStringList("WorldGuard.ignores-regions");

        CLAIM_ENABLED_WORLDS_LIST = config.getStringList("Settings-Claim.enabled-worlds");

        CLAIM_EXPIRATION_CLAIM_TIME = config.getLong("Settings-Claim.expiration.time", 5);

        CLAIM_DEFAULT_CLAIM_LIMIT = config.getInt("Claim.claims-limit", 6);


        CLAIM_MAX_SIZE = config.getInt("Claim.max-claim-size", 256);
        CLAIM_MIN_SIZE = config.getInt("Claim.min-claim-size", 10);

        START_CLAIM_BLOCKS = config.getInt("Claim.start-blocks", 225);
        MAX_BLOCK_BONUS = config.getInt("Claim.start-blocks", 25000);
        ADD_BLOCK_PER_MINUTE = config.getInt("Claim.add-blocks-per-minute", 4);

        SUB_DEFAULT_CLAIM_LIMIT = config.getInt("Settings-Sub-Claim.default-claim-limit", 3);
        SUB_CLAIM_ADD_PER_MEMBER = config.getInt("Settings-Sub-Claim.default-claim-per-member", 1);

        ECONOMY_ENABLE = config.getBoolean("Settings-Global.economy-enable", false);
        CLAIM_MONEY_TAKE = config.getInt("Economy.claim-money-take", 800);
        CREATE_MONEY_TAKE = config.getInt("Economy.create-money-take", 5000);

        ConfigurationSection section = config.getConfigurationSection("Placeholders");

        if (section == null) {
            return;
        }

        for (String path: section.getKeys(false)) {
            if (section.contains(path)) {
                PLACEHOLDERS.put(path,section.getString(path));
            }
        }
    }


    private static void createLanguage() {
        File file = new File(PlaceProtect.getInstance().getDataFolder(), "language" + File.separator + LANGUAGE + ".yml");
        try {
            if (file.createNewFile()) {
                System.out.println(LANGUAGE + ".yml" + " was successfully created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
