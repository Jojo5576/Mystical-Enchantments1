package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StepOutWithABoomHandler {

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {

        if (!(event.getEntity() instanceof Player player))
            return;

        if (player.level().isClientSide) return;

        // Prüfen: mindestens ein Rüstungsteil hat das Enchantment
        boolean hasBoom = false;
        for (ItemStack armor : player.getInventory().armor) {
            if (!armor.isEmpty() &&
                    EnchantmentHelper.getItemEnchantmentLevel(
                            ModEnchantments.STEP_OUT_WITH_A_BOOM.get(), armor) > 0) {
                hasBoom = true;
                break;
            }
        }

        if (!hasBoom) return;

        Level level = player.level();

        // 💥 Explosion ohne Blockschaden
        level.explode(
                player,
                player.getX(),
                player.getY(),
                player.getZ(),
                4.0F,              // Stärke ≈ 2 TNT
                Level.ExplosionInteraction.NONE
        );

        // 💀 Extra Schaden wie 2 TNT (sicher & kontrolliert)
        for (LivingEntity entity : level.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(6.0),
                e -> e != player
        )) {
            entity.hurt(
                    level.damageSources().explosion(player, player),
                    30.0F
            );

        }
    }
}
