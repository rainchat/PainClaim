package com.rainchat.placeprotect.utils.menus;


import com.hakan.inventoryapi.InventoryAPI;
import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.hooks.PlaceholderAPIBridge;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.managers.MenuManager;
import com.rainchat.placeprotect.utils.general.Color;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Executor {
    private static final Pattern ACTION_PATTERN = Pattern.compile("(?iu)\\[([\\w\\._-]+)\\](.*)");
    private final List<String> cmd;
    private final Player player;

    private final MenuManager menuManager = MenuManager.getInstance();
    private final MenuConstructor menuConstructor;

    public Executor(List<String> command, Player player, MenuConstructor menuConstructor) {
        this.cmd = command;
        this.player = player;
        this.menuConstructor = menuConstructor;
    }

    public void start() {


        for (String s : cmd) {
            s = Color.parseHexString(s);
            s = PlaceholderAPIBridge.setPlaceholders(s, player);
            Matcher result = ACTION_PATTERN.matcher(s);
            if (result.find()) {

                action(result.group(1), result.group(2));
            }
        }
    }

    public void action(String s, String action) {
        switch (s.toLowerCase()) {
            case "tell":
                player.sendMessage(action);
                break;
            case "title":
                player.sendTitle(action, action, 10, 70, 20);
                break;
            case "console":
                PlaceProtect.getInstance().getServer().dispatchCommand(PlaceProtect.getInstance().getServer().getConsoleSender(), action);
                break;
            case "op":
                if (player.isOp())
                    player.performCommand(action);
                else {
                    player.setOp(true);
                    player.performCommand(action);
                    player.setOp(false);
                }
                break;
            case "player":
                player.performCommand(action);
                break;
            case "close":
                InventoryAPI.getInstance(PlaceProtect.getInstance()).getInventoryManager().getPlayerInventory(player).close(player);
                break;
            case "next-page":
                menuConstructor.pagination.nextPage();
                break;
            case "previous-page":
                menuConstructor.pagination.previousPage();
                break;
            case "update":
                menuConstructor.setupItems();
                menuConstructor.openMenu();
                break;
            case "remove-claim":
                ClaimManager.INSTANCE.remove(UUID.fromString(action));
                break;
            case "menu-open":
                String[] words = action.split(" ");
                HashMap<String, String> param = menuConstructor.parameter;
                for (String word: words) {
                    String[] parse = word.split(":");
                    if (parse.length > 1) {
                        param.put(parse[0], parse[1]);
                    }
                }
                String menu = words[0];
                menuManager.openMenu(player, menu, param);
                break;
            default:
                System.out.println("such ACTION does not exist (" + s + ")");

        }
    }
}
