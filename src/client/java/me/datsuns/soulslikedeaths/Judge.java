package me.datsuns.soulslikedeaths;

import net.minecraft.entity.player.PlayerEntity;

public class Judge {
    public boolean onTick(PlayerEntity p){
        if( p.isWet() ) {
            if( !SoulslikeDeathsClient.cfg.deathInWater ){
                return false;
            }
            return true;
        }
        return false;
    }
}
