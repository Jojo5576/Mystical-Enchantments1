package de.example.blitzmod.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class HealingWatersEnchantment extends Enchantment {

    public HealingWatersEnchantment() {
        super(
                Rarity.RARE,
                EnchantmentCategory.ARMOR,
                new EquipmentSlot[]{
                        EquipmentSlot.HEAD,
                        EquipmentSlot.CHEST,
                        EquipmentSlot.LEGS,
                        EquipmentSlot.FEET
                }
        );
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
