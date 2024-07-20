package me.datsuns.soulslikedeaths;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class Handler implements ServerTickEvents.EndTick, ClientTickEvents.EndTick {
    private Judge j;
    private PlayerEntity p;
    private UUID id;
    private HandlerEntry onEndTick;

    public Handler(){
        this.j = new Judge();
        this.p = null;
        this.id = null;
        this.onEndTick = new DefaultEndTickHandler();
    }

    private void load(MinecraftServer server) {
        if( this.p != null ){
            return;
        }
        if( this.id == null ){
            return;
        }
        if( server.getPlayerManager() == null) {
            return;
        }
        this.p = server.getPlayerManager().getPlayer(this.id);
        this.onEndTick = new EndTickHandler();
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        load(server);
        this.onEndTick.execute(this.j, this.p);
    }

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
                SoulslikeDeaths.LOGGER.info("kill");
                p.damage(p.getDamageSources().generic(), 1.0F);
            }
        }
    }
}
