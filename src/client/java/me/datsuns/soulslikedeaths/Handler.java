package me.datsuns.soulslikedeaths;

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

import java.util.UUID;

public class Handler implements ServerTickEvents.EndTick, ClientTickEvents.EndTick, ServerLifecycleEvents.ServerStopped, ServerPlayerEvents.AfterRespawn, ClientDamagedCallback {
    private Judge j;
    private PlayerEntity p;
    private UUID id;
    private HandlerEntry onEndTickBody;

    public Handler(){
        this.j = new Judge();
        this.p = null;
        this.id = null;
        this.onEndTickBody = new DefaultEndTickHandler();
    }

    private boolean load(MinecraftServer server) {
        if( this.p != null ){
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
        };
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
        PlayerEntity old = this.p;
        this.id = newPlayer.getUuid();
        this.p  = newPlayer.getServer().getPlayerManager().getPlayer(this.id);
        SoulslikeDeaths.LOGGER.info("player from {} to {}", old, this.p);
    }

    // client damaged callback
    @Override
    public float interact(DamageSource source, float amount) {
        if( this.j.onDamaged(amount) ){
            //SoulslikeDeaths.LOGGER.info("force death on damaged");
            return Float.MAX_VALUE;
        }
        return amount;
    }

    public interface HandlerEntry {
        void execute(Judge j, PlayerEntity p);
    }

    public class DefaultEndTickHandler implements HandlerEntry {
        @Override
        public void execute(Judge j, PlayerEntity p) {}
    }

    public class EndTickHandler implements HandlerEntry {
        @Override
        public void execute(Judge j, PlayerEntity p) {
            if( j.onTick(p) ){
                //SoulslikeDeaths.LOGGER.info("kill");
                p.damage(p.getDamageSources().generic(), Float.MAX_VALUE);
            }
        }
    }
}
