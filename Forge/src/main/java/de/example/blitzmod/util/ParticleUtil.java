package de.example.blitzmod.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;

public class ParticleUtil {

    public static void spawnLightningParticles(Player player) {
        Level level = player.level();
        if (!level.isClientSide) return;

        for (int i = 0; i < 6; i++) {
            double angle = level.random.nextDouble() * Math.PI * 2;
            double radius = 0.6;

            double x = player.getX() + Math.cos(angle) * radius;
            double y = player.getY() + 1.0 + level.random.nextDouble() * 0.5;
            double z = player.getZ() + Math.sin(angle) * radius;

            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0.02, 0);
            level.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, 0, 0.01, 0);
        }
    }
}
