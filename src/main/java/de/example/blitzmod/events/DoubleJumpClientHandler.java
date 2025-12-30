package de.example.blitzmod.events;

import de.example.blitzmod.enchantment.ModEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class DoubleJumpClientHandler {

    private static final Set<UUID> USED_DOUBLE_JUMP = new HashSet<>();
    private static boolean lastJumpState = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        ItemStack boots = player.getInventory().getArmor(0);

        boolean hasEnchant =
                !boots.isEmpty() &&
                        EnchantmentHelper.getItemEnchantmentLevel(
                                ModEnchantments.DOPPELSPRUNG.get(), boots) > 0;

        boolean jumpPressed = mc.options.keyJump.isDown();

        // Reset beim Landen
        if (player.onGround()) {
            USED_DOUBLE_JUMP.remove(player.getUUID());
            lastJumpState = jumpPressed;
            return;
        }

        if (!hasEnchant) {
            lastJumpState = jumpPressed;
            return;
        }

        // neu gedrückt?
        if (jumpPressed && !lastJumpState) {
            if (!USED_DOUBLE_JUMP.contains(player.getUUID())) {
                USED_DOUBLE_JUMP.add(player.getUUID());

                player.setDeltaMovement(
                        player.getDeltaMovement().x,
                        0.6,
                        player.getDeltaMovement().z
                );

                player.hasImpulse = true;
            }
        }

        lastJumpState = jumpPressed;
    }
}
