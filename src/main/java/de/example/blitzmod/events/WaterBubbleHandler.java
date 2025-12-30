package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class WaterBubbleHandler {

    private static final double RADIUS = 3.0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // === SERVER: Knockback ===
        if (!player.level().isClientSide) {

            if (!player.isCrouching()) return;

            if (!hasWaterBubble(player)) return;

            List<Entity> entities = player.level().getEntities(
                    player,
                    player.getBoundingBox().inflate(RADIUS),
                    e -> e instanceof LivingEntity && e != player
            );

            for (Entity entity : entities) {
                double dx = entity.getX() - player.getX();
                double dz = entity.getZ() - player.getZ();

                entity.push(dx * 0.6, 0.3, dz * 0.6);
            }
        }

        // === CLIENT: Partikel ===
        if (player.level().isClientSide) {
            if (!player.isCrouching()) return;
            if (!hasWaterBubble(player)) return;

            spawnWaterBubbleParticles(player);
        }
    }

    // 🔍 Prüft alle Rüstungsteile
    private boolean hasWaterBubble(Player player) {
        for (ItemStack armor : player.getInventory().armor) {
            if (!armor.isEmpty() &&
                    EnchantmentHelper.getItemEnchantmentLevel(
                            ModEnchantments.WATERBUBBLE.get(), armor) > 0) {
                return true;
            }
        }
        return false;
    }

    // 🫧 Wasserblase-Partikel
    private void spawnWaterBubbleParticles(Player player) {
        for (int i = 0; i < 20; i++) {
            double angle = player.level().random.nextDouble() * Math.PI * 2;
            double radius = 2.4;

            double x = player.getX() + Math.cos(angle) * radius;
            double y = player.getY() + 1.0 + player.level().random.nextDouble() * 1.5;
            double z = player.getZ() + Math.sin(angle) * radius;

            // Blasen
            player.level().addParticle(
                    ParticleTypes.BUBBLE,
                    x, y, z,
                    0, 0.03, 0
            );

            // Wassertropfen
            player.level().addParticle(
                    ParticleTypes.SPLASH,
                    x, y, z,
                    0, 0.01, 0
            );
        }
    }
}
