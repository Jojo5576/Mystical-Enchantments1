package de.example.blitzmod.capability;

public class LaserData {

    public int firingTicks = 0;
    public int cooldownTicks = 0;

    public boolean canFire() {
        return cooldownTicks == 0 && firingTicks < 100;
    }

    public void tick() {
        if (cooldownTicks > 0) cooldownTicks--;
    }

    public void startCooldown() {
        cooldownTicks = 6;
    }

    public void resetFire() {
        firingTicks = 100;
    }
}
