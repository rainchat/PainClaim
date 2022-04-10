package com.rainchat.placeprotect.builder;


import com.rainchat.placeprotect.builder.pagination.*;
import com.rainchat.placeprotect.utils.builder.Builder;
import com.rainchat.placeprotect.utils.builder.CollectionUtils;
import com.rainchat.placeprotect.utils.menus.MenuConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationBuilder extends Builder<String, Action> {

    public static final PaginationBuilder INSTANCE = new PaginationBuilder();

    private PaginationBuilder() {
        registerDefaultActions();
    }

    private void registerDefaultActions() {
        register(s -> new OnlinePagination(), "online-players");
        register(s -> new ClaimsPagination(), "claims");
        register(s -> new FlagsPagination(), "flags");
        register(s -> new ClaimMembersPagination(), "members");
        register(s -> new ClaimPermissionsPagination(), "permissions");
    }

    public List<Action> getActions(MenuConstructor menuConstructor, Object object) {
        return CollectionUtils.createStringListFromObject(object, true)
                .stream()
                .map(string -> {
                    String[] split = string.split(":", 2);
                    String name = split[0];
                    String value = split.length > 1 ? split[1] : "";

                    Action action = build(name.trim(), value.trim()).orElseGet(OnlinePagination::new);
                    action.setMenu(menuConstructor);
                    action.setPlayer(menuConstructor.player);
                    action.setupItems();
                    return action;
                })
                .collect(Collectors.toList());
    }
}