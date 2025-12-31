package de.example.blitzmod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.ItemStack;

public class RiptideOutOfABottleEnchantment extends Enchantment {

    public RiptideOutOfABottleEnchantment() {
        super(
                Rarity.RARE,
                EnchantmentCategory.TRIDENT,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
        );
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof TridentItem;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
