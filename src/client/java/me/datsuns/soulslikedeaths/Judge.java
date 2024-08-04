package me.datsuns.soulslikedeaths;

import net.minecraft.entity.player.PlayerEntity;

public class Judge {
    public boolean onTick(PlayerEntity p){
        if( p.isWet() ) {
            return SoulslikeDeathsClient.cfg.deathInWater;
        }
        return false;
    }

    public boolean onDamaged(float amount) {
        return SoulslikeDeathsClient.cfg.deathOnDamaged;
    }
}
