package me.datsuns.soulslikedeaths.mixin.client;

import me.datsuns.soulslikedeaths.event.ClientDamagedCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class ClientDamagedMixin {
    @ModifyVariable(
            method = "damage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private float injectDamageValue(float amount, ServerWorld world, DamageSource source) {
        return ClientDamagedCallback.EVENT.invoker().interact((PlayerEntity) (Object) this, source, amount);
    }
}
