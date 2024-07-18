package me.datsuns.soulslikedeaths;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SoulslikeDeathsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Handler h = new Handler();
		HudRenderCallback.EVENT.register(h);
		ClientTickEvents.END_CLIENT_TICK.register(h);
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}