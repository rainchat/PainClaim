package com.rainchat.placeprotect.utils.claim;

public enum ClaimCorner {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public ClaimCorner opposite() {
        switch (this) {
            case TOP_LEFT:
                return BOTTOM_RIGHT;
            case TOP_RIGHT:
                return BOTTOM_LEFT;
            case BOTTOM_LEFT:
                return TOP_RIGHT;
            case BOTTOM_RIGHT:
                return TOP_LEFT;
            default:
                return null;
        }
    }
}
