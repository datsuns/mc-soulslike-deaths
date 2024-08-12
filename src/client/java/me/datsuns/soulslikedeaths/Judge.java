package me.datsuns.soulslikedeaths;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class Judge {
    public boolean onTick(PlayerEntity p){
        if( p.isWet() ) {
            return SoulslikeDeathsClient.cfg.deathInWater;
        }
        return false;
    }

    public boolean onDamaged(PlayerEntity player, DamageSource source, float amount) {
        if( SoulslikeDeathsClient.cfg.deathOnDamaged ){
            return true;
        }
        if( SoulslikeDeathsClient.cfg.deathByHeadShot ) {
            double threshold = player.getY() + 1.5;
            if( source.getPosition().getY() > threshold ) {
                return true;
            }
        }
        return false;
    }
}
