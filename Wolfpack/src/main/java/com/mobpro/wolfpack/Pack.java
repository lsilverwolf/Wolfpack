package com.mobpro.wolfpack;

/**
 * Created by rachel on 12/16/13.
 */
public class Pack {
    String name;
    String creator;
    int points;

    public Pack(String name, String creator, int points) {
        this.name = name;
        this.creator = creator;
        this.points = points;
    }

    @Override
    public String toString() {
        return name + " created by " + creator;
    }
}
