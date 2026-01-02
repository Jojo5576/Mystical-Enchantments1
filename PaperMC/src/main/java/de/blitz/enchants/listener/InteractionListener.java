package de.blitz.enchants.listener;

import de.blitz.enchants.util.EnchantUtil;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (EnchantUtil.hasEnchant(p.getInventory().getItemInMainHand(), "Blitz")) {
            if (p.getTargetBlockExact(30) != null)
                p.getWorld().strikeLightningEffect(
                        p.getTargetBlockExact(30).getLocation()
                );
        }

        if (EnchantUtil.hasEnchant(p.getInventory().getItemInMainHand(), "Fireball")) {
            Fireball fb = p.launchProjectile(Fireball.class);
            fb.setYield(2f);
        }
    }
}
