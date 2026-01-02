package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class RevivalOfATitanHandler {

    private static final Map<UUID, Integer> TIMER = new HashMap<>();
    private static final Map<UUID, List<EndCrystal>> CRYSTALS = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.level().isClientSide) return;

        if (!player.isCrouching()) {
            reset(player);
            return;
        }

        ItemStack ritualArmor = getValidArmorPiece(player);
        if (ritualArmor.isEmpty()) return;

        UUID id = player.getUUID();
        int ticks = TIMER.getOrDefault(id, 0) + 1;
        TIMER.put(id, ticks);

        ServerLevel level = (ServerLevel) player.level();

        // Phase 1 – End Crystals (0–5s)
        if (ticks == 1) spawnCrystals(player, level);
        rotateCrystals(player);

        // Phase 2 – Dragon Breath Partikel (5–8s)
        if (ticks == 100) {
            level.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH,
                    player.getX(), player.getY() + 1, player.getZ(),
                    100, 2, 2, 2, 0.05
            );
        }

        // Phase 3 – Lila Strahlen (8–12s)
        if (ticks >= 160 && ticks < 240) {
            level.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.END_ROD,
                    player.getX(), player.getY(), player.getZ(),
                    30, 1.5, 3, 1.5, 0.1
            );
        }

        // Phase 4 – Explosion + Drache (12s)
        if (ticks >= 240) {
            explodeAndSummon(player, level);
            reset(player);
        }
    }

    /* ========================== */

    private ItemStack getValidArmorPiece(Player player) {
        for (ItemStack armor : player.getInventory().armor) {

            int revival = EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.REVIVAL_OF_A_TITAN.get(), armor);

            int warrior = EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.WARRIOR_OF_THE_END.get(), armor);

            if (revival > 0 && warrior > 0) {
                return armor;
            }
        }
        return ItemStack.EMPTY;
    }

    private void spawnCrystals(Player player, ServerLevel level) {
        List<EndCrystal> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            EndCrystal crystal = EntityType.END_CRYSTAL.create(level);
            if (crystal != null) {
                crystal.setShowBottom(false);
                crystal.setPos(player.getX(), player.getY() + 2, player.getZ());
                level.addFreshEntity(crystal);
                list.add(crystal);
            }
        }
        CRYSTALS.put(player.getUUID(), list);
    }

    private void rotateCrystals(Player player) {
        List<EndCrystal> list = CRYSTALS.get(player.getUUID());
        if (list == null) return;

        double time = System.currentTimeMillis() * 0.002;

        for (int i = 0; i < list.size(); i++) {
            EndCrystal c = list.get(i);
            double angle = time + (Math.PI * 2 / list.size()) * i;

            c.setPos(
                    player.getX() + Math.cos(angle) * 2.5,
                    player.getY() + 1.5,
                    player.getZ() + Math.sin(angle) * 2.5
            );
        }
    }

    private void explodeAndSummon(Player player, ServerLevel level) {

        level.explode(
                player,
                player.getX(),
                player.getY(),
                player.getZ(),
                6.0F,
                Level.ExplosionInteraction.NONE
        );

        List<EndCrystal> list = CRYSTALS.remove(player.getUUID());
        if (list != null) list.forEach(Entity::discard);

        // Zerstöre GENAU das gültige Rüstungsteil
        for (int i = 0; i < player.getInventory().armor.size(); i++) {
            ItemStack armor = player.getInventory().armor.get(i);

            int revival = EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.REVIVAL_OF_A_TITAN.get(), armor);

            int warrior = EnchantmentHelper.getItemEnchantmentLevel(
                    ModEnchantments.WARRIOR_OF_THE_END.get(), armor);

            if (revival > 0 && warrior > 0) {
                player.getInventory().armor.set(i, ItemStack.EMPTY);
                break;
            }
        }

        EnderDragon dragon = EntityType.ENDER_DRAGON.create(level);
        if (dragon != null) {
            dragon.setPos(player.getX(), player.getY() + 12, player.getZ());
            level.addFreshEntity(dragon);
        }

        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 0));
        player.addEffect(new MobEffectInstance(MobEffects.POISON, 3600, 0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 0));
    }

    private void reset(Player player) {
        TIMER.remove(player.getUUID());

        List<EndCrystal> list = CRYSTALS.remove(player.getUUID());
        if (list != null) list.forEach(Entity::discard);
    }
}
