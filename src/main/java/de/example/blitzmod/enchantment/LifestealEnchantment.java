package de.example.blitzmod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LifestealEnchantment extends Enchantment {

    public LifestealEnchantment() {
        super(
                Rarity.RARE,
                EnchantmentCategory.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
        );
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();

        // Nur Nahkampfwaffen
        return item instanceof SwordItem ||
                item instanceof AxeItem ||
                item == net.minecraft.world.item.Items.STICK;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
