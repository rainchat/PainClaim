package com.rainchat.placeprotect.managers;

import com.rainchat.placeprotect.data.flag.FlagClaim;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FlagManager {

    private final List<FlagClaim> flagClaims;

    public static final FlagManager INSTANCE = new FlagManager();

    private  FlagManager() {
        this.flagClaims = new ArrayList<>();

        flagClaims.add(new FlagClaim("PVP",new ItemStack(Material.LIME_DYE)));
        flagClaims.add(new FlagClaim("EXPLOSIONS",new ItemStack(Material.LIME_DYE)));
        flagClaims.add(new FlagClaim("DAMAGE_ANIMALS",new ItemStack(Material.LIME_DYE)));
        flagClaims.add(new FlagClaim("FIRE_SPREAD",new ItemStack(Material.LIME_DYE)));

    }

    public List<String> getStringFlags() {
        List<String> list = new ArrayList<>();
        for (FlagClaim flagClaim: flagClaims) {
            list.add(flagClaim.getName());
        }
        return list;
    }

    public FlagClaim getFlag(String flag) {
        for (FlagClaim flagClaim: flagClaims) {
            if (flagClaim.getName().equalsIgnoreCase(flag)) {
                return flagClaim;
            }
        }
        return null;
    }

    public List<FlagClaim> getFlags() {
        return flagClaims;
    }

    public void addFlag(FlagClaim flag) {
        this.flagClaims.add(flag);
    }

    public void removeFlag(String flag){
        flagClaims.removeIf(flagV -> flagV.getName().equalsIgnoreCase(flag));
    }

    public boolean contain(String flag){
        for (FlagClaim flagClaim: flagClaims) {
            if (flagClaim.getName().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }
    
}
