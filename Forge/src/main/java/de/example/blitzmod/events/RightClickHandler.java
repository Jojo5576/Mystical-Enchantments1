package de.example.blitzmod.events;

import de.example.blitzmod.capability.LaserData;
import de.example.blitzmod.enchantment.ModEnchantments;
import de.example.blitzmod.util.ParticleUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RightClickHandler {

    private static final LaserData DATA = new LaserData();

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        if (!(item instanceof SwordItem || item instanceof AxeItem || item == Items.STICK)) return;

        if (EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantments.BLITZ.get(), stack) <= 0) return;

        if (!DATA.canFire()) return;

        Level level = player.level();

        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(30));

        HitResult hit = level.clip(new ClipContext(
                start, end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player));

        if (!level.isClientSide) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
            if (bolt != null) {
                bolt.moveTo(hit.getLocation());
                level.addFreshEntity(bolt);
            }
        }

        ParticleUtil.spawnLightningParticles(player);

        DATA.firingTicks++;

        if (DATA.firingTicks >= 100) {
            DATA.startCooldown();
            DATA.resetFire();
        }
    }
}
