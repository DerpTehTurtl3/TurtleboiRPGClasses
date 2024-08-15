package net.turtleboi.turtlerpgclasses.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.client.render.ArrowRenderer;
import net.turtleboi.turtlerpgclasses.client.ui.cooldowns.CooldownOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.resources.ResourceOverlay;
import net.turtleboi.turtlerpgclasses.entity.ModEntities;
import net.turtleboi.turtlerpgclasses.entity.client.SanctuaryDomeModel;
import net.turtleboi.turtlerpgclasses.entity.client.UnleashFuryModel;
import net.turtleboi.turtlerpgclasses.util.KeyBinding;
import net.turtleboi.turtlerpgclasses.entity.client.renderer.UnleashFuryRenderer;
import net.turtleboi.turtlerpgclasses.entity.client.renderer.SanctuaryDomeRenderer;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModBusEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.UNLEASH_FURY.get(), UnleashFuryRenderer::new);
        EntityRenderers.register(ModEntities.SANCTUARY_DOME.get(), SanctuaryDomeRenderer::new);

        Minecraft.getInstance().execute(() -> {
            if (Minecraft.getInstance().player != null) {
                CooldownOverlay.initializeSlots(Minecraft.getInstance().player);
            }
        });
    }

    @SubscribeEvent
    public  static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.OPEN_CLASSSELECT_KEY);
        event.register(KeyBinding.OPEN_TALENTTREE_KEY);
        event.register(KeyBinding.ACTIVE1);
        event.register(KeyBinding.ACTIVE2);
        event.register(KeyBinding.ACTIVE3);
        event.register(KeyBinding.ACTIVE4);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        //event.registerAbove(Objects.requireNonNull(ResourceLocation.tryParse("inventory")),"rpg_class", ClassOverlay.HUD_CLASSNAME);
        //event.registerAbove(Objects.requireNonNull(ResourceLocation.tryParse("inventory")),"rpg_subclass", ClassOverlay.HUD_SUBCLASSNAME);
        event.registerAbove(Objects.requireNonNull(ResourceLocation.tryParse("player_health")),"rpg_resourcebar", ResourceOverlay.HUD_RESOURCESNUMBER);
        event.registerAbove(Objects.requireNonNull(ResourceLocation.tryParse("player_health")), "cooldown_bay", CooldownOverlay.HUD_COOLDOWNS);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(UnleashFuryModel.UNLEASH_FURY_LAYER, UnleashFuryModel::createBodyLayer);
        event.registerLayerDefinition(SanctuaryDomeModel.SANCTUARY_DOME_LAYER, SanctuaryDomeModel::createBodyLayer);
    }
}

