package net.turtleboi.turtlerpgclasses.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.turtleboi.turtlecore.network.CoreNetworking;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.client.ui.cooldowns.CooldownOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.resources.ResourceOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentPointAllocator;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentScreen;
import net.turtleboi.turtlerpgclasses.entity.ModEntities;
import net.turtleboi.turtlerpgclasses.network.packet.experience.TalentExperienceHandler;
import net.turtleboi.turtlerpgclasses.util.KeyBinding;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModBusEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        minecraft.execute(() -> {
            if (minecraft.player != null) {
                CooldownOverlay.initializeSlots(minecraft.player);
            }

            // Register a listener for when the TalentScreen is fully initialized
            MinecraftForge.EVENT_BUS.register(new Object() {
                @SubscribeEvent
                public void onScreenInit(ScreenEvent.Init.Post event) {
                    if (event.getScreen() instanceof TalentScreen talentScreen) {
                        TalentPointAllocator allocator = talentScreen.getTalentPointAllocator();
                        if (allocator != null) {
                            CoreNetworking.experienceHandlerSupplier = () -> new TalentExperienceHandler(allocator);
                            //System.out.println("This allocator is named:" + allocator); //debug code
                        } else {
                            //System.out.println("Allocator is null"); //debug code
                        }
                    }
                }
            });
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
        event.registerAbove(Objects.requireNonNull(ResourceLocation.tryParse("player_health")),"rpg_resourcebar", ResourceOverlay.HUD_RESOURCESNUMBER);
        event.registerAbove(Objects.requireNonNull(ResourceLocation.tryParse("player_health")), "cooldown_bay", CooldownOverlay.HUD_COOLDOWNS);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
    }
}

