package me.datsuns.soulslikedeaths;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;

    protected ConfigScreen(Screen parent) {
        super(Text.of("Your Mod Config"));
        this.parent = parent;
    }

    @Override
    protected void init(){
        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.of("Toggle"),
                                button -> SoulslikeDeathsClient.cfg.toggleDeathInWater()
                        )
                        .dimensions(
                                this.width / 2 - 100,
                                20,
                                200, 20)
                        .build()
        );
        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.of("Done"),
                                button -> {
                                    SoulslikeDeathsClient.cfg.save();
                                    this.client.setScreen(this.parent);
                                }
                        )
                        .dimensions(
                                this.width / 2 - 100,
                                this.height / 6 + 144,
                                200, 20)
                        .build()
        );
    }
}
