package me.datsuns.soulslikedeaths.event;

import me.datsuns.soulslikedeaths.SoulslikeDeaths;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface ClientDamagedCallback {
    Event<ClientDamagedCallback> EVENT = EventFactory.createArrayBacked(
            ClientDamagedCallback.class,
            (listeners) -> (source, amount) -> {
                if( amount == Float.MAX_VALUE ){
                    //SoulslikeDeaths.LOGGER.info("MAX damage");
                    return amount;
                }
                float result = 0.0f;
                for (ClientDamagedCallback listener : listeners) {
                    float v = listener.interact(source, amount);
                    if( v > result) {
                        result = v;
                    }
                }
                return result;
            }
    );
    float interact(DamageSource source, float amount);
}
