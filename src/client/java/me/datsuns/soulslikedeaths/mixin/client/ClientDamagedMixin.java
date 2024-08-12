package me.datsuns.soulslikedeaths.mixin.client;

import me.datsuns.soulslikedeaths.event.ClientDamagedCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public class ClientDamagedMixin {
    @ModifyArg(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", index = 1,  at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private float injectDamageValue(DamageSource source, float amount) {
        float result = ClientDamagedCallback.EVENT.invoker().interact((PlayerEntity) (Object) this, source, amount);
        return result;
    }
}
