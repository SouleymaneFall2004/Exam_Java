package org.example.demo.data.entities.enums;

public enum State {
    ENABLED, DISABLED;

    public static State getState(String state){
        if (state.equalsIgnoreCase("ENABLED")) {
            return State.ENABLED;
        }
        if (state.equalsIgnoreCase("DISABLED")) {
            return State.DISABLED;
        }
        return null;
    }
}