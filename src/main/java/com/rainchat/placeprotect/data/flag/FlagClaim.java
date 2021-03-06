package com.rainchat.placeprotect.data.flag;

import org.bukkit.inventory.ItemStack;

public class FlagClaim {

    private String name;
    private String displayName;
    private ItemStack item;

    public FlagClaim(String name, String displayName, ItemStack item) {
        this.name = name;
        this.displayName = displayName;
        this.item = item;
    }

    public FlagClaim(String name, ItemStack item) {
        this.name = name;
        this.displayName = name;
        this.item = item;
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
