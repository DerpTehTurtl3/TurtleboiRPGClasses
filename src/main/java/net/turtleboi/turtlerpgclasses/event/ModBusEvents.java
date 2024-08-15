package net.turtleboi.turtlerpgclasses.event;

import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.particle.ModParticles;
import net.turtleboi.turtlerpgclasses.particle.custom.LifeDrainParticles;

@Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        event.register(ModParticles.LIFE_DRAIN_PARTICLES.get(),
                LifeDrainParticles.Provider::new);
    }
}
