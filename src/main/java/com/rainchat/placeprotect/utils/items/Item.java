package com.rainchat.placeprotect.utils.items;

import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.placeholder.PlaceholderSupply;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Item {


    private String name;
    private String material;
    private short durability;
    private String[] lore;
    private int custom_model = -1;
    private Map<Enchantment, Integer> enchantments;
    private String base64;

    public ItemStack skullTextured() {
        UUID id = UUID.nameUUIDFromBytes(base64.getBytes());
        int less = (int) id.getLeastSignificantBits();
        int most = (int) id.getMostSignificantBits();
        return Bukkit.getUnsafe().modifyItemStack(
                new ItemStack(Material.PLAYER_HEAD),
                "{SkullOwner:{Id:[I;" + (less * most) + "," + (less >> 23) + "," + (most / less) + "," + (most * 8731) + "],Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public String getMaterial() {
        return material;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public Item material(String material) {
        this.material = material;
        return this;
    }

    public Item setCustomModelDate(int value) {
        this.custom_model = value;
        return this;
    }


    public Item durability(int durability) {
        this.durability = (short) durability;
        return this;
    }


    public Item lore(List<String> lore) {
        this.lore = lore.toArray(new String[0]);
        return this;
    }

    public Item lore(String... lore) {
        this.lore = lore;
        return this;
    }

    public Item lore(List<String> lore, String... lore2) {
        this.lore = lore.toArray(new String[0]);
        for (String s : lore2) {
            lore.add(s);
        }
        return this;
    }

    public Item setBase64(String base64) {
        this.base64 = base64;
        return this;
    }



    public Item enchants(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemStack buildPlayer(OfflinePlayer offlinePlayer) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        if (itemMeta == null) return null;
        if (offlinePlayer != null) itemMeta.setOwningPlayer(offlinePlayer);
        if (name != null) itemMeta.setDisplayName(Chat.color(name));
        if (enchantments != null) enchantments.forEach(itemStack::addEnchantment);
        if (lore != null) itemMeta.setLore(Arrays.stream(lore).map(Chat::color).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack build(PlaceholderSupply<?>... replacementSource) {
        ItemStack itemStack;

        if (material != null) {
            material = Chat.translateRaw(material, replacementSource);
            if (material.contains("base64:")) {
                setBase64(material.replaceAll("base64:", ""));
            } else if (material.contains("material:")) {
                material = material.replaceAll("material:", "");
            }
        }

        if (base64 != null) {
            itemStack = skullTextured();
        } else {
            itemStack = new ItemStack(Material.valueOf(material.toUpperCase()));
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        if (durability >= 0) itemStack.setDurability(durability);
        if (name != null) itemMeta.setDisplayName(Chat.color(Chat.translateRaw(name, replacementSource)));
        if (enchantments != null) enchantments.forEach(itemStack::addEnchantment);
        if (lore != null)
            itemMeta.setLore(Chat.translateRaw(Arrays.stream(lore).map(Chat::color).collect(Collectors.toList()), replacementSource));
        if (custom_model != -1) itemMeta.setCustomModelData(custom_model);


        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack build(Player player, PlaceholderSupply<?>... replacementSource) {
        ItemStack itemStack;

        if (material != null) {
            material = Chat.translateRaw(material, replacementSource);
            if (material.contains("base64:")) {
                setBase64(material.replaceAll("base64:", ""));
            } else if (material.contains("material:")) {
                material = material.replaceAll("material:", "");
            }
        }

        if (base64 != null) {
            itemStack = skullTextured();
        } else {
            itemStack = new ItemStack(Material.valueOf(material.toUpperCase()));
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        if (durability >= 0) itemStack.setDurability(durability);
        if (name != null) itemMeta.setDisplayName(Chat.color(Chat.translateRaw(name, replacementSource)));
        if (enchantments != null) enchantments.forEach(itemStack::addEnchantment);
        if (lore != null)
            itemMeta.setLore(Chat.translateRaw(Arrays.stream(lore).map(Chat::color).collect(Collectors.toList()), replacementSource));
        if (custom_model != -1) itemMeta.setCustomModelData(custom_model);


        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}