package com.rainchat.placeprotect.api.placeholder;

import com.rainchat.placeprotect.data.config.ClaimNameConfig;
import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.placeholder.BaseReplacements;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Random;

public class RandomGenerator extends BaseReplacements<String> {


    public RandomGenerator() {
        super("generator_");

    }


    @Override
    public Class<String> forClass() {
        return String.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {

        List<String> word = ClaimNameConfig.words.get(base);

        Bukkit.broadcastMessage("" + base + " " + fullKey);
        if (word != null) {
            return getClaimName(word);
        } else {
            return "";
        }
    }


    public static String getClaimName(List<String> array) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(array.size());
        String word = array.get(index);
        word = Chat.translateRaw(word);
        return word;
    }
}