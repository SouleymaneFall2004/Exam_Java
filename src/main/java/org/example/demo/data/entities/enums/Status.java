package org.example.demo.data.entities.enums;

public enum Status {
    ACCEPTED, PENDING, CANCELLED;

    public static Status getStatus(String status){
        if (status.equalsIgnoreCase("ACCEPTED")) {
            return Status.ACCEPTED;
        }
        if (status.equalsIgnoreCase("PENDING")) {
            return Status.PENDING;
        }
        if (status.equalsIgnoreCase("CANCELLED")) {
            return Status.CANCELLED;
        }
        return null;
    }
}
