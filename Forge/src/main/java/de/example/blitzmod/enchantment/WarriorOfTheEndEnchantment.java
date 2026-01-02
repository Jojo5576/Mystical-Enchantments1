package de.example.blitzmod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WarriorOfTheEndEnchantment extends Enchantment {

    public WarriorOfTheEndEnchantment() {
        super(
                Rarity.VERY_RARE,
                EnchantmentCategory.BREAKABLE,
                new EquipmentSlot[]{
                        EquipmentSlot.MAINHAND,
                        EquipmentSlot.OFFHAND,
                        EquipmentSlot.HEAD,
                        EquipmentSlot.CHEST,
                        EquipmentSlot.LEGS,
                        EquipmentSlot.FEET
                }
        );
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();

        return item instanceof SwordItem ||
                item instanceof AxeItem ||
                item instanceof ArmorItem ||
                item instanceof ShieldItem;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasureOnly() {
        return true; // selten & besonders
    }
}
