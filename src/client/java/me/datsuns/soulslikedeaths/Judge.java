package me.datsuns.soulslikedeaths;

import net.minecraft.entity.player.PlayerEntity;

public class Judge {
    public boolean onClientTick(PlayerEntity p){
        SoulslikeDeaths.LOGGER.info("onClientTick");
        return false;
    }

    public boolean onRenderCallback(PlayerEntity p){
        SoulslikeDeaths.LOGGER.info("onRenderCallback");
        return false;
    }
}
