package me.datsuns.soulslikedeaths;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;

public class Handler implements HudRenderCallback, ClientTickEvents.EndTick {
    private Judge j;
    public Handler(){
        this.j = new Judge();
    }

    private PlayerEntity load() {
        MinecraftClient c = MinecraftClient.getInstance();
        return c.player;
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        PlayerEntity p = load();
        if( p == null ){
            return;
        }
        this.j.onRenderCallback(p);
    }

    @Override
    public void onEndTick(MinecraftClient client) {
        PlayerEntity p = load();
        if( p == null ){
            return;
        }
        this.j.onClientTick(p);
    }
}
