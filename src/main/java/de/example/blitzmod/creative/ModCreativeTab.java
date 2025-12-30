package de.example.blitzmod.creative;

import de.example.blitzmod.BlitzMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeTab {

    public static final CreativeModeTab BLITZ_TAB =
            CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.blitzmod"))
                    .icon(() -> new ItemStack(Items.STICK))
                    .build();
}
