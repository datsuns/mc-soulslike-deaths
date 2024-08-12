package me.datsuns.soulslikedeaths;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;

@me.shedaniel.autoconfig.annotation.Config(name = "soulslike-deaths")
class Config implements ConfigData {
    boolean deathInWater = true;
    boolean deathOnDamaged = true;
    boolean deathByHeadShot = true;

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
}
