package net.turtleboi.turtlerpgclasses.client;

public class ClientClassData {
    private static String playerClass = "No Class";
    private static String playerSubclass = "No Subclass";

    public static void setPlayerClass(String newClass) {
        playerClass = newClass;
    }

    public static String getPlayerClass() {
        return playerClass;
    }

    public static void setPlayerSubclass(String newSubclass) {
        playerSubclass = newSubclass;
    }

    public static String getPlayerSubclass() {
        return playerSubclass;
    }
}

