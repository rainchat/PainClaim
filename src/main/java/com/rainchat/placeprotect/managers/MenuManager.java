package com.rainchat.placeprotect.managers;



import com.rainchat.placeprotect.utils.menus.MenuConstructor;
import com.rainchat.placeprotect.utils.menus.MenuSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {

    private static final MenuManager instance = new MenuManager();
    private final FileManager fileManager = FileManager.getInstance();
    public ClaimManager claimManager;
    public HashMap<String, MenuSettings> menus;


    public static MenuManager getInstance() {
        return instance;
    }


    public void setupMenus(ClaimManager claimManager) {
        this.claimManager = claimManager;
        setupMenus();
    }

    public void setupMenus() {
        this.menus = new HashMap<>();
        for (String menuName : fileManager.getAllCategory("menus")) {
            try {
                FileConfiguration file = fileManager.getFile(menuName).getFile();
                menus.put(menuName, new MenuSettings(file));
            } catch (NullPointerException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void openMenu(Player player, String name) {
        MenuConstructor menuConstructor = new MenuConstructor(player, menus.get(name), new HashMap<>());
        menuConstructor.openMenu();
    }

    public void openMenu(Player player, String name, HashMap<String, String> param) {
        MenuConstructor menuConstructor = new MenuConstructor(player, menus.get(name), param);
        menuConstructor.openMenu();
    }

    public List<String> getAllMenu() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, MenuSettings> menu : menus.entrySet()) {
            list.add(menu.getKey());
        }
        return list;
    }
}
