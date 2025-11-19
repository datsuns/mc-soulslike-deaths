package me.datsuns.soulslikedeaths.neoforge;

import me.datsuns.soulslikedeaths.common.SoulslikeDeathsConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SoulslikeDeathsNeoForgeConfig implements SoulslikeDeathsConfig {
    static final SoulslikeDeathsNeoForgeConfig INSTANCE = new SoulslikeDeathsNeoForgeConfig();
    static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue deathInWater;
    private static final ModConfigSpec.BooleanValue deathOnDamaged;
    private static final ModConfigSpec.BooleanValue deathByHeadShot;
    private static final ModConfigSpec.BooleanValue deathWhenRunning;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Soulslike Deaths settings").push("conditions");
        deathInWater = builder.comment("Instant death when entering water or rain.")
                .translation("text.config.soulslikedeaths.deathInWater")
                .define("deathInWater", true);
        deathOnDamaged = builder.comment("Instant death upon receiving any damage.")
                .translation("text.config.soulslikedeaths.deathOnDamaged")
                .define("deathOnDamaged", true);
        deathByHeadShot = builder.comment("Instant death if damage source hits the player's head.")
                .translation("text.config.soulslikedeaths.deathByHeadShot")
                .define("deathByHeadShot", true);
        deathWhenRunning = builder.comment("Instant death when running above the speed threshold.")
                .translation("text.config.soulslikedeaths.deathWhenRunning")
                .define("deathWhenRunning", true);
        builder.pop();
        SPEC = builder.build();
    }

    private SoulslikeDeathsNeoForgeConfig() {
    }

    public void save() {
        SPEC.save();
    }

    public void toggleDeathInWater() {
        deathInWater.set(!deathInWater.get());
    }

    public void toggleDeathOnDamaged() {
        deathOnDamaged.set(!deathOnDamaged.get());
    }

    public void toggleDeathByHeadShot() {
        deathByHeadShot.set(!deathByHeadShot.get());
    }

    public void toggleDeathWhenRunning() {
        deathWhenRunning.set(!deathWhenRunning.get());
    }

    @Override
    public boolean deathInWater() {
        return deathInWater.get();
    }

    @Override
    public boolean deathOnDamaged() {
        return deathOnDamaged.get();
    }

    @Override
    public boolean deathByHeadShot() {
        return deathByHeadShot.get();
    }

    @Override
    public boolean deathWhenRunning() {
        return deathWhenRunning.get();
    }
}
