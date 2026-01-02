package de.example.blitzmod.events;

import de.example.blitzmod.capability.LaserData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerTickHandler {

    private static final LaserData DATA = new LaserData();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        DATA.tick();
    }
}
