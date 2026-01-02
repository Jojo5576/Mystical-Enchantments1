package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LifestealHandler {

    // Treffer zählen pro Spieler
    private static final Map<UUID, Integer> HIT_COUNTER = new HashMap<>();

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {

        if (!(event.getSource().getEntity() instanceof Player player))
            return;

        if (player.level().isClientSide) return;

        ItemStack weapon = player.getMainHandItem();

        if (EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantments.LIFESTEAL.get(), weapon) <= 0)
            return;

        UUID id = player.getUUID();
        int hits = HIT_COUNTER.getOrDefault(id, 0) + 1;

        if (hits >= 5) {
            hits = 0;

            // Heilen: 0.5 Herz = 1.0 HP
            player.heal(1.0F);
        }

        HIT_COUNTER.put(id, hits);
    }
}
