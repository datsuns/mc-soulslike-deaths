package me.datsuns.soulslikedeaths.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public interface ClientDamagedCallback {
    Event<ClientDamagedCallback> EVENT = EventFactory.createArrayBacked(
            ClientDamagedCallback.class,
            (listeners) -> (player, source, amount) -> {
                if( amount == Float.MAX_VALUE ){
                    //SoulslikeDeaths.LOGGER.info("MAX damage");
                    return amount;
                }
                float result = 0.0f;
                for (ClientDamagedCallback listener : listeners) {
                    float v = listener.interact(player, source, amount);
                    if( v > result) {
                        result = v;
                    }
                }
                return result;
            }
    );
    float interact(PlayerEntity player, DamageSource source, float amount);
}
