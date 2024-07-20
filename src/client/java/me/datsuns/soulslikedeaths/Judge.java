package me.datsuns.soulslikedeaths;

import net.minecraft.entity.player.PlayerEntity;

public class Judge {
    public boolean onTick(PlayerEntity p){
        if( p.isWet() ) {
            return true;
        }
        return false;
    }
}
