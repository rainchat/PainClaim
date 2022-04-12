package com.rainchat.placeprotect.data.config;

import com.rainchat.placeprotect.managers.FileManager;
import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.general.Color;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public enum LanguageFile {
    PREFIX("Messages.general.prefix", "&9&lPaintClaim: &7"),
    RELOAD("Messages.general.reload", "&aconfigurations were successfully reloaded."),
    NO_COMMAND_PERMISSION("Messages.general.no-command-permission", "You do not have permissions for that command."),
    NO_PERMISSION("Messages.general.no-permission", "You do not have permissions for &b{0}&7."),
    PLAYER_OFFLINE("Messages.general.player-offline", "The player &b{0} &7does not seem to be online."),


    CLAIM_JOIN_REGION("Messages.custom.join-claim", "%actionbar%&7Welcome to &b%claim_name%'s &7claim."),
    CLAIM_JOIN_WILDERNESS("Messages.custom.join-wilderness", "%actionbar%&a&lWilderness!"),


    CLAIM_INFO("Messages.claim.info", Arrays.asList(
            "&7&o%claim_name%",
            "",
            "&7Size: &b%claim_size%",
            "&7Owner: &e%claim_owner%"
    )),
    CLAIM_NULL("Messages.claim.null-claim", "There is no claim here!"),
    CLAIM_ADD_PLAYER("Messages.claim.add-player", "&7Player has been successfully added to claim!"),
    CLAIM_REMOVE_PLAYER("Messages.claim.remove-player", "&7Player has been successfully remove from claim!"),
    CLAIM_NO_PLAYER("Messages.claim.not-player", "&eNo village was found on this chunk!"),
    CLAIM_NOT_IN_CLAIM("Messages.claim.not-in-claim", "You are not in your claim."),
    CLAIM_LIMIT_REGIONS("Messages.claim.limit-regions", "&cHas reached a limit of claims!"),
    CLAIM_LIMIT_BLOCKS("Messages.claim.limit-blocks", "&cNot enough blocks for claim!"),
    CLAIM_OVERLAPS("Messages.claim.overlaps", "&7Claims overlap."),
    CLAIM_INVALID_SIZE("Messages.claim.invalid-size", "&7Invalid claim size ({min} - {max})"),
    CLAIM_CREATE("Messages.claim.create", "&7Private has been successfully &acreated"),
    CLAIM_REMOVE("Messages.claim.remove", "&7Claim has been successfully &cdeleted"),

    CLAIM_ADMIN_MODE("Messages.claim.mode", "You have set admin mode: %mode%"),
    CLAIM_ADMIN_ADD_BLOCKS("Messages.claim.add-blocks", "&7Successfully add &9%blocks_add% &7blocks to &a%target_name%.");

    private static FileManager.CustomFile configuration;
    private final String path;
    private String def;
    private List<String> list;

    LanguageFile(String path, String def) {
        this.path = path;
        this.def = def;
    }

    LanguageFile(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    public static int addMissingMessages() {
        FileConfiguration file = configuration.getFile();
        int index = 0;

        boolean saveFile = false;
        for (LanguageFile message : values()) {
            index++;
            if (!file.contains(message.getPath())) {
                saveFile = true;
                if (message.getDefaultMessage() != null) {
                    file.set(message.getPath(), message.getDefaultMessage());
                } else {
                    file.set(message.getPath(), message.getDefaultListMessage());
                }
            }
        }
        if (saveFile) {
            configuration.saveFile();
        }


        return index;
    }

    public static String convertList(List<String> list) {
        String message = "";
        for (String line : list) {
            message += line + "\n";
        }
        return message;
    }

    public static void setConfiguration(FileManager.CustomFile configuration) {
        LanguageFile.configuration = configuration;
    }

    public String getDef() {
        return configuration.getFile().getString(path, def);
    }

    public String getMessage() {
        String message;
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = convertList(configuration.getFile().getStringList(path));
            } else {
                message = convertList(getDefaultListMessage());
            }
        } else {
            if (exists) {
                message = configuration.getFile().getString(path);
            } else {
                message = getDefaultMessage();
            }
        }

        return Chat.translateRaw(message);
    }

    private boolean exists() {
        return configuration.getFile().contains(path);
    }

    private boolean isList() {
        if (configuration.getFile().contains(path)) {
            return !configuration.getFile().getStringList(path).isEmpty();
        } else {
            return def == null;
        }
    }

    public List<String> toList() {
        return configuration.getFile().getStringList(path);
    }

    public String getPath() {
        return path;
    }

    public List<String> getList() {
        return list;
    }

    private String getDefaultMessage() {
        return def;
    }

    private List<String> getDefaultListMessage() {
        return list;
    }
}