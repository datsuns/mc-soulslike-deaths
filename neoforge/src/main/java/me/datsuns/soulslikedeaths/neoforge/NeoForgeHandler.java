package me.datsuns.soulslikedeaths.neoforge;

import me.datsuns.soulslikedeaths.common.DamageContext;
import me.datsuns.soulslikedeaths.common.Judge;
import me.datsuns.soulslikedeaths.common.MovementContext;
import me.datsuns.soulslikedeaths.common.SoulslikeDeathsConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class NeoForgeHandler {
    private final Judge judge;

    public NeoForgeHandler(SoulslikeDeathsConfig config) {
        this.judge = new Judge(config);
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }
        boolean inWater = player.isInWaterOrRain();
        double horizontalSpeed = player.getDeltaMovement().horizontalDistance();
        MovementContext context = new MovementContext(inWater, horizontalSpeed);
        if (judge.shouldDieFromMovement(context)) {
            player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
        }
    }

    @SubscribeEvent
    public void onPlayerDamaged(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }
        DamageSource source = event.getSource();
        boolean isHeadShot = false;
        Vec3 damagePos = source.getSourcePosition();
        if (damagePos != null) {
            double threshold = player.getY() + Judge.HEADSHOT_HEIGHT_THRESHOLD;
            isHeadShot = damagePos.y > threshold;
        }
        DamageContext context = new DamageContext(isHeadShot);
        if (judge.shouldDieFromDamage(context)) {
            event.setNewDamage(Float.MAX_VALUE);
        }
    }
}
