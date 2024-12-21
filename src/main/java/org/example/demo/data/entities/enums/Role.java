package org.example.demo.data.entities.enums;

public enum Role {
    SHOPKEEPER, ADMIN, CLIENT;

    public static Role getRole(String role) {
        if (role.equalsIgnoreCase("SHOPKEEPER")) {
            return Role.SHOPKEEPER;
        }
        if (role.equalsIgnoreCase("ADMIN")) {
            return Role.ADMIN;
        }
        if (role.equalsIgnoreCase("CLIENT")) {
            return Role.CLIENT;
        }
        return null;
    }
}