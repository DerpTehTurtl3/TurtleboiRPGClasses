package net.turtleboi.turtlerpgclasses.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class UIConfig {
    public static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec spec;

    public static ForgeConfigSpec.ConfigValue<Integer> cooldownX;
    public static ForgeConfigSpec.ConfigValue<Integer> cooldownY;
    public static ForgeConfigSpec.ConfigValue<String> cooldownAnchor;
    public static ForgeConfigSpec.ConfigValue<String> cooldownOrientation;
    public static ForgeConfigSpec.ConfigValue<Integer> resourceMainX;
    public static ForgeConfigSpec.ConfigValue<Integer> resourceMainY;
    public static ForgeConfigSpec.ConfigValue<String> resourceMainAnchor;
    public static ForgeConfigSpec.ConfigValue<Integer> resourceSecondaryX;
    public static ForgeConfigSpec.ConfigValue<Integer> resourceSecondaryY;
    public static ForgeConfigSpec.ConfigValue<String> resourceSecondaryAnchor;

    static {
        builder.comment("UI Positions").push("positions");

        cooldownX = builder
                .comment("X offset of the Cooldown UI component")
                .comment("Default Value: 2")
                .define("cooldownXOffset", 2);

        cooldownY = builder
                .comment("Y offset of the Cooldown UI component")
                .comment("Default Value: 2")
                .define("cooldownYOffset", 2);

        cooldownAnchor = builder
                .comment("Anchor position of the Cooldown UI component")
                .comment("Valid Anchors: TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT, TOP_CENTER, BOTTOM_CENTER, CENTER")
                .comment("Default Value: TOP_LEFT")
                .define("cooldownAnchor", "TOP_LEFT");

        cooldownOrientation = builder
                .comment("Orientation direction of the Cooldown UI component")
                .comment("Valid Orientations: HORIZONTAL, VERTICAL")
                .comment("Default Value: HORIZONTAL")
                .define("cooldownOrientation", "HORIZONTAL");

        resourceMainX = builder
                .comment("X offset of the Main Resource Bar UI component")
                .comment("Default Value: 49")
                .define("resourceMainBarXOffset", 49);

        resourceMainY = builder
                .comment("Y offset of the Main Resource Bar UI component")
                .comment("Default Value: 50")
                .define("resourceMainBarYOffset", 50);

        resourceMainAnchor = builder
                .comment("Anchor position of the Main Resource Bar UI component")
                .comment("Valid Anchors: TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT, TOP_CENTER, BOTTOM_CENTER, CENTER")
                .comment("Default Value: BOTTOM_CENTER")
                .define("resourceMainAnchor", "BOTTOM_CENTER");

        resourceSecondaryX = builder
                .comment("X offset of the Secondary Resource Bar UI component")
                .comment("Default Value: 49")
                .define("resourceSecondaryXOffset", 49);

        resourceSecondaryY = builder
                .comment("Y offset of the Secondary Resource Bar UI component")
                .comment("Default Value: 66")
                .define("resourceSecondaryYOffset", 66);

        resourceSecondaryAnchor = builder
                .comment("Anchor position of the Secondary Resource Bar UI component")
                .comment("Valid Anchors: TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT, TOP_CENTER, BOTTOM_CENTER, CENTER")
                .comment("Default Value: BOTTOM_CENTER")
                .define("resourceSecondaryAnchor", "BOTTOM_CENTER");

        builder.pop();
        spec = builder.build();
    }

    public static void setCooldownPosition(int x, int y) {
        cooldownX.set(x);
        cooldownY.set(y);
        spec.save();
        reload();
    }

    public static void setMainResourceBarPosition(int x, int y) {
        resourceMainX.set(x);
        resourceMainY.set(y);
        spec.save();
        reload();
    }

    public static void setSecondaryResourceBarPosition(int x, int y) {
        resourceSecondaryX.set(x);
        resourceSecondaryY.set(y);
        spec.save();
        reload();
    }

    public static void reload() {

    }
}
