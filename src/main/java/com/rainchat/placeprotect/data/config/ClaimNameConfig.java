package com.rainchat.placeprotect.data.config;

import com.rainchat.placeprotect.api.placeholder.RandomGenerator;
import com.rainchat.placeprotect.utils.general.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ClaimNameConfig {

    private static List<String> line = new ArrayList<>();
    public static HashMap<String, List<String>> words = new HashMap<>();
    private static Random randomGenerator;

    public static void setup(FileConfiguration fileConfiguration) {

        for (String path: fileConfiguration.getKeys(false)) {
            if (Objects.equals(path, "name-formats")) {
                line = fileConfiguration.getStringList(path);
            } else {
                words.put(path,fileConfiguration.getStringList(path));
            }
        }
        randomGenerator = new Random();

    }

    public static String getClaimName() {

        int index = randomGenerator.nextInt(line.size());
        String word = line.get(index);
        word = Chat.translateRaw(word, new RandomGenerator());
        return word;
    }

}
