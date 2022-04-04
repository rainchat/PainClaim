package com.rainchat.placeprotect.utils.menus;

public class PaginationItem extends ClickItem implements Cloneable{
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public PaginationItem clone() {
        try {
            PaginationItem clone = (PaginationItem) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
