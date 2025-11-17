package me.datsuns.soulslikedeaths;

import me.datsuns.soulslikedeaths.common.DamageContext;
import me.datsuns.soulslikedeaths.common.Judge;
import me.datsuns.soulslikedeaths.common.MovementContext;
import me.datsuns.soulslikedeaths.common.SoulslikeDeathsConfig;
import me.datsuns.soulslikedeaths.event.ClientDamagedCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class Handler implements ServerTickEvents.EndTick, ClientTickEvents.EndTick, ServerLifecycleEvents.ServerStopped, ServerPlayerEvents.AfterRespawn, ClientDamagedCallback {
    private final Judge j;
    private ServerPlayerEntity p;
    private UUID id;
    private HandlerEntry onEndTickBody;

    public Handler(SoulslikeDeathsConfig config){
        this.j = new Judge(config);
        this.p = null;
        this.id = null;
        this.onEndTickBody = new DefaultEndTickHandler();
    }

    private boolean load(MinecraftServer server) {
        if( this.p != null && !this.p.isRemoved() ){
            return true;
        }
        if( this.id == null ){
            return false;
        }
        if( server.getPlayerManager() == null) {
            return false;
        }
        this.p = server.getPlayerManager().getPlayer(this.id);
        if( this.p == null ){
            return false;
        }
        this.onEndTickBody = new EndTickHandler();
        return true;
    }

    // ServerTick
    @Override
    public void onEndTick(MinecraftServer server) {
        if( !load(server) ){
            return;
        }
        this.onEndTickBody.execute(this.j, this.p);
    }

    // ClientTick
    @Override
    public void onEndTick(MinecraftClient client) {
        if(this.id != null){
            return;
        }
        if(client.player == null){
            return;
        }
        this.id = client.player.getUuid();
    }

    @Override
    public void onServerStopped(MinecraftServer server) {
        SoulslikeDeaths.LOGGER.info("clear instance");
        this.id = null;
        this.p = null;
    }

    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        this.id = newPlayer.getUuid();
        this.p  = newPlayer;
        SoulslikeDeaths.LOGGER.info("player from {} to {}", oldPlayer, this.p);
    }

    // client damaged callback
    @Override
    public float interact(PlayerEntity player, DamageSource source, float amount) {
        Vec3d damagePos = source.getPosition();
        boolean isHeadShot = false;
        if(damagePos != null) {
            double threshold = player.getY() + Judge.HEADSHOT_HEIGHT_THRESHOLD;
            isHeadShot = damagePos.getY() > threshold;
        }
        DamageContext damageContext = new DamageContext(isHeadShot);
        if( this.j.shouldDieFromDamage(damageContext) ){
            //SoulslikeDeaths.LOGGER.info("force death on damaged");
            return Float.MAX_VALUE;
        }
        return amount;
    }

    public interface HandlerEntry {
        void execute(Judge j, ServerPlayerEntity p);
    }

    public class DefaultEndTickHandler implements HandlerEntry {
        @Override
        public void execute(Judge j, ServerPlayerEntity p) {}
    }

    public class EndTickHandler implements HandlerEntry {
        @Override
        public void execute(Judge j, ServerPlayerEntity p) {
            //SoulslikeDeaths.LOGGER.info("tick {}", p.getMovementSpeed());
            boolean inWater = p.isTouchingWaterOrRain();
            double speed = p.getMovement().horizontalLength();
            MovementContext context = new MovementContext(inWater, speed);
            if( j.shouldDieFromMovement(context) ){
                //SoulslikeDeaths.LOGGER.info("kill");
                ServerWorld world = p.getEntityWorld();
                if(world != null){
                    p.damage(world, p.getDamageSources().generic(), Float.MAX_VALUE);
                }
            }
        }
    }
}
