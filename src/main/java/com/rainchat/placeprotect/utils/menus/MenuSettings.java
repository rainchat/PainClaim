package com.rainchat.placeprotect.utils.menus;


import com.rainchat.placeprotect.utils.general.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

public class MenuSettings {

    // ################# Settings #################
    public static final String
            SLOT = "slot",
            MATERIAL = "material",
            SKULL_TEXTURE = "skull-texture",
            NAME = "name",
            LORE = "lore",
            MODEL_DATE = "madel-date",
            ACTIONS = "actions",
            LEFT_CLICK = "left-click",
            RIGHT_CLICK = "right-click",
            TYPE = "type";
    private static final String MENU_TITLE = "Menu-settings.title";
    private static final String MENU_SIZE = "Menu-settings.size";
    private static final String PAGINATION_SLOTS = "Menu-settings.pagination-slots";
    public String title;
    public int size = 3;
    public List<Integer> slots;
    public Set<ClickItem> itemDataList = new HashSet<>();
    public PaginationItem paginationItem;
    public ClickItem feelItem;

    //#############################################


    public MenuSettings(FileConfiguration file) {

        title = file.getString(MENU_TITLE);
        size = file.getInt(MENU_SIZE);


        loadItemDatas(file);
    }


    private void loadItemDatas(FileConfiguration file) {
        LinkedList<ClickItem> itemdatas = new LinkedList<>();

        for (String subSectionName : file.getConfigurationSection("Menu-items").getKeys(false)) {

            ConfigurationSection s = file.getConfigurationSection("Menu-items." + subSectionName);
            ClickItem itemdata = new ClickItem();
            itemdata.setSlot(s.getInt(SLOT));
            if (s.isString(MATERIAL)) {
                itemdata
                        .name(s.getString(NAME))
                        .material(s.getString(MATERIAL));
                if (s.isInt(MODEL_DATE)) {
                    itemdata.setCustomModelDate(s.getInt(MODEL_DATE));
                }
                if (s.isSet(SKULL_TEXTURE)) {
                    itemdata.setBase64(s.getString(SKULL_TEXTURE));
                }
            } else {
                //THROW ERROR
                Bukkit.getLogger().log(Level.WARNING, "You need at least ID or SKULL TEXTURE to create any item");
            }

            itemdata.setCommands(getListFromFile(s,ACTIONS));
            itemdata.setLeftClick(getListFromFile(s,LEFT_CLICK));
            itemdata.setRightClick(getListFromFile(s,RIGHT_CLICK));

            itemdata.lore(s.getStringList(LORE));
            itemdatas.add(itemdata);
            itemDataList.add(itemdata);
        }

        feelItem = new ClickItem();

        if (file.isString("Menu-settings.fill-items." + MATERIAL)) {
            feelItem
                    .name(file.getString("Menu-settings.fill-items." + NAME))
                    .material(file.getString("Menu-settings.fill-items." + MATERIAL));
            if (file.isInt("Menu-settings.fill-items." + MODEL_DATE)) {
                feelItem.setCustomModelDate(file.getInt("Menu-settings.fill-items." + MODEL_DATE));
            }
            if (file.isList("Menu-settings.fill-items." + LORE)) {
                feelItem.lore(file.getStringList("Menu-settings.fill-items." + LORE));
            }
            if (file.isString("Menu-settings.fill-items." + SKULL_TEXTURE)) {
                feelItem.setBase64(file.getString("Menu-settings.fill-items." + SKULL_TEXTURE));
            }
        } else {
            feelItem = null;
        }

        if (file.isString(PAGINATION_SLOTS)) {
            String[] strings = file.getString(PAGINATION_SLOTS).split(",");
            slots = new ArrayList<>();
            for (String s : strings) {
                if (MathUtil.isInt(s)) {
                    slots.add(Integer.parseInt(s));
                }
            }
        }

        paginationItem = new PaginationItem();

        if (file.isString("Pagination-items." + NAME)) {
            paginationItem.name(file.getString("Pagination-items." + NAME));
            if (file.isString("Pagination-items." + MATERIAL)) {
                paginationItem.material(file.getString("Pagination-items." + MATERIAL));
            }
            if (file.isInt("Pagination-items." + MODEL_DATE)) {
                paginationItem.setCustomModelDate(file.getInt("Pagination-items." + MODEL_DATE));
            }
            if (file.isList("Pagination-items." + LORE)) {
                paginationItem.lore(file.getStringList("Pagination-items." + LORE));
            }
            if (file.isString("Pagination-items." + SKULL_TEXTURE)) {
                feelItem.setBase64(file.getString("Pagination-items." + SKULL_TEXTURE));
            }
            paginationItem.setCommands(getListFromFile(file,"Pagination-items." + ACTIONS));
            paginationItem.setLeftClick(getListFromFile(file,"Pagination-items." + LEFT_CLICK));
            paginationItem.setRightClick(getListFromFile(file,"Pagination-items." + RIGHT_CLICK));
            if (file.isString("Pagination-items." + TYPE)) {
                paginationItem.setType(file.getString("Pagination-items." + TYPE));
            } else {
                paginationItem = null;
            }

        } else {
            paginationItem = null;
        }

    }

    public List<String> getListFromFile(ConfigurationSection configurationSection, String path) {
        if (configurationSection.contains(path)) {
            return configurationSection.getStringList(path);
        }
        return new ArrayList<>();
    }

    public List<String> getListFromFile(FileConfiguration configurationSection, String path) {
        if (configurationSection.contains(path)) {
            return configurationSection.getStringList(path);
        }
        return new ArrayList<>();
    }


}
