package de.example.blitzmod;

import de.example.blitzmod.events.HealingWatersHandler;
import de.example.blitzmod.events.LifestealHandler;
import de.example.blitzmod.events.WaterBubbleHandler;
import de.example.blitzmod.events.DoubleJumpClientHandler;
import de.example.blitzmod.events.FireballRightClickHandler;
import de.example.blitzmod.enchantment.ModEnchantments;
import de.example.blitzmod.events.PlayerTickHandler;
import de.example.blitzmod.events.RightClickHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlitzMod.MODID)
public class BlitzMod {

    public static final String MODID = "blitzmod";

    public BlitzMod() {
        ModEnchantments.ENCHANTMENTS.register(
                FMLJavaModLoadingContext.get().getModEventBus()
        );

        MinecraftForge.EVENT_BUS.register(new RightClickHandler());
        MinecraftForge.EVENT_BUS.register(new FireballRightClickHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
        MinecraftForge.EVENT_BUS.register(new WaterBubbleHandler());
        MinecraftForge.EVENT_BUS.register(new LifestealHandler());
        MinecraftForge.EVENT_BUS.register(new HealingWatersHandler());






    }

}


