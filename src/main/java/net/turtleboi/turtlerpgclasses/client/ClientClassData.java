package net.turtleboi.turtlerpgclasses.client;

public class ClientClassData {
    private static String playerClass;
    private static String playerSubclass;

    public static void setPlayerClass(String className) {
        playerClass = className;
    }

    public static String getPlayerClass() {
        if (playerClass != null){
            return playerClass;
        } else {
            return "No Class";
        }
    }

    public static void setPlayerSubclass(String newSubclass) {
        playerSubclass = newSubclass;
    }

    public static String getPlayerSubclass() {
        if (playerSubclass != null){
            return playerSubclass;
        } else {
            return "No Subclass";
        }
    }
}

