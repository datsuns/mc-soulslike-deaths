package me.datsuns.soulslikedeaths.common;

/**
 * Evaluates whether the player should instantly die based on current config and context.
 */
public class Judge {
    public static final double HEADSHOT_HEIGHT_THRESHOLD = 1.5;
    public static final double SPEED_THRESHOLD = 0.275;

    private final SoulslikeDeathsConfig config;

    public Judge(SoulslikeDeathsConfig config) {
        this.config = config;
    }

    public boolean shouldDieFromMovement(MovementContext context) {
        if (context.inWater() && config.deathInWater()) {
            return true;
        }
        if (config.deathWhenRunning() && context.horizontalSpeed() > SPEED_THRESHOLD) {
            return true;
        }
        return false;
    }

    public boolean shouldDieFromDamage(DamageContext context) {
        if (config.deathOnDamaged()) {
            return true;
        }
        return config.deathByHeadShot() && context.isHeadShot();
    }
}
