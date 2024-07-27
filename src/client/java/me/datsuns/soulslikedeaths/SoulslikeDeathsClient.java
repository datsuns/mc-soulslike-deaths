package me.datsuns.soulslikedeaths;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class SoulslikeDeathsClient implements ClientModInitializer {
	public static Config cfg;
	@Override
	public void onInitializeClient() {
		AutoConfig.register(Config.class, Toml4jConfigSerializer::new);
		cfg = AutoConfig.getConfigHolder(Config.class).getConfig();
		Handler h = new Handler();
		ClientTickEvents.END_CLIENT_TICK.register(h);
		ServerTickEvents.END_SERVER_TICK.register(h);
	}
}