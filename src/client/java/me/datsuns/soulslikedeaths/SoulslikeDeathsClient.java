package me.datsuns.soulslikedeaths;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class SoulslikeDeathsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Handler h = new Handler();
		ClientTickEvents.END_CLIENT_TICK.register(h);
		ServerTickEvents.END_SERVER_TICK.register(h);
	}
}