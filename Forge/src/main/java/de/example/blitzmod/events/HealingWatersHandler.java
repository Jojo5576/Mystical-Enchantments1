package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HealingWatersHandler {

    // Zählt Ticks pro Spieler
    private static final Map<UUID, Integer> TICK_COUNTER = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        boolean hasEnchant = hasHealingWaters(player);

        // === SERVER: Heilen ===
        if (!player.level().isClientSide) {

            if (!hasEnchant || !player.isInWater()) {
                TICK_COUNTER.remove(player.getUUID());
                return;
            }

            UUID id = player.getUUID();
            int ticks = TICK_COUNTER.getOrDefault(id, 0) + 1;

            if (ticks >= 20) { // 1 Sekunde
                ticks = 0;
                player.heal(1.0F); // ½ Herz
            }

            TICK_COUNTER.put(id, ticks);
        }

        // === CLIENT: Partikel ===
        if (player.level().isClientSide) {
            if (!hasEnchant || !player.isInWater()) return;
            spawnHealingParticles(player);
        }
    }

    // 🔍 Prüft alle Rüstungsteile
    private boolean hasHealingWaters(Player player) {
        for (ItemStack armor : player.getInventory().armor) {
            if (!armor.isEmpty() &&
                    EnchantmentHelper.getItemEnchantmentLevel(
                            ModEnchantments.HEALING_WATERS.get(), armor) > 0) {
                return true;
            }
        }
        return false;
    }

    // 🌿 Hellgrüne Partikel
    private void spawnHealingParticles(Player player) {
        for (int i = 0; i < 8; i++) {
            double x = player.getX() + (player.level().random.nextDouble() - 0.5);
            double y = player.getY() + 0.5 + player.level().random.nextDouble();
            double z = player.getZ() + (player.level().random.nextDouble() - 0.5);

            player.level().addParticle(
                    ParticleTypes.HAPPY_VILLAGER, // hellgrün
                    x, y, z,
                    0, 0.02, 0
            );
        }
    }
}
