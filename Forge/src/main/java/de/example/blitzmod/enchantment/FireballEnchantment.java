package de.example.blitzmod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FireballEnchantment extends Enchantment {

    public FireballEnchantment() {
        super(Rarity.RARE,
                EnchantmentCategory.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SwordItem
                || item instanceof AxeItem
                || item == Items.STICK;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return canEnchant(stack);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
