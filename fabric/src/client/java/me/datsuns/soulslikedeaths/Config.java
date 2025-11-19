package me.datsuns.soulslikedeaths;

import me.datsuns.soulslikedeaths.common.SoulslikeDeathsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;

@me.shedaniel.autoconfig.annotation.Config(name = "soulslikedeaths")
class Config implements ConfigData, SoulslikeDeathsConfig {
    boolean deathInWater = true;
    boolean deathOnDamaged = true;
    boolean deathByHeadShot = true;
    boolean deathWhenRunning = true;

    private Config() {
    }

    public static Config createConfig() {
        return new Config();
    }

    public void save(){
        AutoConfig.getConfigHolder(Config.class).save();
    }

    public void toggleDeathInWater() {
        this.deathInWater = !this.deathInWater;
    }

    public void toggleDeathOnDamaged() {
        this.deathOnDamaged = !this.deathOnDamaged;
    }

    public void toggleDeathByHeadShot() {
        this.deathByHeadShot = !this.deathByHeadShot;
    }

    public void toggleDeathWhenRunning() {
        this.deathWhenRunning = !this.deathWhenRunning;
    }

    @Override
    public boolean deathInWater() {
        return this.deathInWater;
    }

    @Override
    public boolean deathOnDamaged() {
        return this.deathOnDamaged;
    }

    @Override
    public boolean deathByHeadShot() {
        return this.deathByHeadShot;
    }

    @Override
    public boolean deathWhenRunning() {
        return this.deathWhenRunning;
    }
}
