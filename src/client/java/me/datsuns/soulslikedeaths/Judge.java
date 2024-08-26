package me.datsuns.soulslikedeaths;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class Judge {
    public final double HeightThreshold = 1.5;
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
            double threshold = player.getY() + HeightThreshold;
            if( source.getPosition().getY() > threshold ) {
                return true;
            }
        }
        return false;
    }
}
