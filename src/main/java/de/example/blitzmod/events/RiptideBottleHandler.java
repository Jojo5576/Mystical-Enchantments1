package de.example.blitzmod.events;

import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RiptideBottleHandler {

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack trident = player.getMainHandItem();

        if (!(trident.getItem() instanceof TridentItem)) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantments.RIPTIDE_OUT_OF_A_BOTTLE.get(), trident);

        if (level <= 0) return;

        ItemStack offhand = player.getOffhandItem();

        boolean used = false;

        // 💧 Wasserflasche
        if (offhand.getItem() == Items.POTION &&
                PotionUtils.getPotion(offhand) == Potions.WATER) {

            offhand.shrink(1);
            player.addItem(new ItemStack(Items.GLASS_BOTTLE));
            used = true;
        }

        // 🪣 Wassereimer
        if (offhand.getItem() == Items.WATER_BUCKET) {
            player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.BUCKET));
            used = true;
        }

        if (!used) return;

        // ⚡ Dash auslösen
        Vec3 look = player.getLookAngle();
        double strength = 1.5 + (level * 0.6);

        player.setDeltaMovement(
                look.x * strength,
                look.y * strength,
                look.z * strength
        );

        player.hasImpulse = true;
        player.swing(InteractionHand.MAIN_HAND);
        event.setCanceled(true);
    }
}
