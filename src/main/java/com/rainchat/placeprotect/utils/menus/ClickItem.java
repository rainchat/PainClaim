package com.rainchat.placeprotect.utils.menus;

import com.rainchat.placeprotect.utils.items.Item;

import java.util.List;

public class ClickItem extends Item {

    int slot = 0;
    List<String> commands;
    List<String> leftClick;
    List<String> rightClick;

    public List<String> getLeftClick() {
        return leftClick;
    }

    public void setLeftClick(List<String> leftClick) {
        this.leftClick = leftClick;
    }

    public List<String> getRightClick() {
        return rightClick;
    }

    public void setRightClick(List<String> rightClick) {
        this.rightClick = rightClick;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
