package me.datsuns.soulslikedeaths.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SoulslikeDeathsNeoForge.MOD_ID, dist = Dist.CLIENT)
public class SoulslikeDeathsNeoForgeClient {
    public SoulslikeDeathsNeoForgeClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (minecraft, parent) -> new SoulslikeDeathsNeoForgeConfigScreen(parent));
    }
}
