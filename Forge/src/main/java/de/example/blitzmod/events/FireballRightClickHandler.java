package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FireballRightClickHandler {

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        // Item-Check
        if (!(item instanceof SwordItem || item instanceof AxeItem || item == Items.STICK))
            return;

        // Enchantment-Check
        if (EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantments.FEUERKUGEL.get(), stack) <= 0)
            return;

        Level level = player.level();

        if (!level.isClientSide) {
            LargeFireball fireball = new LargeFireball(
                    level,
                    player,
                    player.getLookAngle().x,
                    player.getLookAngle().y,
                    player.getLookAngle().z,
                    1 // Explosionsstärke
            );

            fireball.setPos(
                    player.getX(),
                    player.getEyeY() - 0.2,
                    player.getZ()
            );

            level.addFreshEntity(fireball);

            // Haltbarkeit verringern
            stack.hurtAndBreak(1, player,
                    p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
    }
}
