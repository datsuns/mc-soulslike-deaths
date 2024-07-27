package me.datsuns.soulslikedeaths;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;

    protected ConfigScreen(Screen parent) {
        super(Text.of("Your Mod Config"));
        this.parent = parent;
    }
}
