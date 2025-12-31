package de.example.blitzmod.enchantment;

import de.example.blitzmod.BlitzMod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BlitzMod.MODID);

    public static final RegistryObject<Enchantment> BLITZ =
            ENCHANTMENTS.register("blitz", LightningEnchantment::new);

    public static final RegistryObject<Enchantment> FEUERKUGEL =
            ENCHANTMENTS.register("feuerkugel", FireballEnchantment::new);

    public static final RegistryObject<Enchantment> DOPPELSPRUNG =
            ENCHANTMENTS.register("doppelsprung", DoubleJumpEnchantment::new);

    public static final RegistryObject<Enchantment> WATERBUBBLE =
            ENCHANTMENTS.register("waterbubble", WaterBubbleEnchantment::new);

    public static final RegistryObject<Enchantment> LIFESTEAL =
            ENCHANTMENTS.register("lifesteal", LifestealEnchantment::new);

    public static final RegistryObject<Enchantment> HEALING_WATERS =
            ENCHANTMENTS.register("healing_waters", HealingWatersEnchantment::new);

    public static final RegistryObject<Enchantment> STEP_OUT_WITH_A_BOOM =
            ENCHANTMENTS.register(
                    "step_out_with_a_boom",
                    StepOutWithABoomEnchantment::new
            );

    public static final RegistryObject<Enchantment> WARRIOR_OF_THE_END =
            ENCHANTMENTS.register(
                    "warrior_of_the_end",
                    WarriorOfTheEndEnchantment::new
            );


}

