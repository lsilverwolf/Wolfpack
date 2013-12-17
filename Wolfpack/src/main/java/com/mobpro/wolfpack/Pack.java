package com.mobpro.wolfpack;

/**
 * Created by rachel on 12/16/13.
 */
public class Pack {
    String name;
    String creator;

    public Pack(String name, String creator) {
        this.name = name;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
