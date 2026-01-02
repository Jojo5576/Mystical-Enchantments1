package de.blitz.enchants.listener;

import de.blitz.enchants.util.EnchantUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (EnchantUtil.hasEnchant(
                e.getEntity().getInventory().getChestplate(),
                "Step out with a Boom")) {

            e.getEntity().getWorld().createExplosion(
                    e.getEntity().getLocation(),
                    6f,
                    false,
                    false
            );
        }
    }
}
