package net.turtleboi.turtlerpgclasses.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_TRPG = "key.category.turtlerpgclasses.trpg";
    public static final String KEY_OPEN_CLASSSELECT_GUI = "key.turtlerpgclasses.open_classselect";
    public static final String KEY_OPEN_SKILLTREE_GUI = "key.turtlerpgclasses.open_talenttree";
    public static final String KEY_ACTIVE1 = "key.turtlerpgclasses.active1";
    public static final String KEY_ACTIVE2 = "key.turtlerpgclasses.active2";
    public static final String KEY_ACTIVE3 = "key.turtlerpgclasses.active3";
    public static final String KEY_ACTIVE4 = "key.turtlerpgclasses.active4";

    public static final KeyMapping OPEN_CLASSSELECT_KEY = new KeyMapping(KEY_OPEN_CLASSSELECT_GUI, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_TRPG);
    public static final KeyMapping OPEN_TALENTTREE_KEY = new KeyMapping(KEY_OPEN_SKILLTREE_GUI, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, KEY_CATEGORY_TRPG);
    public static final KeyMapping ACTIVE1 = new KeyMapping(KEY_ACTIVE1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_TRPG);
    public static final KeyMapping ACTIVE2 = new KeyMapping(KEY_ACTIVE2, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_T, KEY_CATEGORY_TRPG);
    public static final KeyMapping ACTIVE3 = new KeyMapping(KEY_ACTIVE3, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_TRPG);
    public static final KeyMapping ACTIVE4 = new KeyMapping(KEY_ACTIVE4, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY_TRPG);
}
