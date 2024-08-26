package me.datsuns.soulslikedeaths;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends GameOptionsScreen {
    public final int ButtonWidth;
    public final int ButtonHeight;
    private final Screen parent;
    public final String TRUE;
    public final String FALSE;

    protected ConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable("option_title"));
        this.parent = parent;
        this.TRUE = "§c" + Text.translatable("option.true").getString();
        this.FALSE = "§a" + Text.translatable("option.false").getString();
        this.ButtonWidth  = 200;
        this.ButtonHeight = 20;
    }

    protected int posXCenter(int buttonWidth){
        return (this.width / 2) - (buttonWidth / 2);
    }

    protected Text buildButtonTitle(String key, boolean value) {
        String t = Text.translatable(key).getString() + " : " + (value ? this.TRUE : this.FALSE);
        return Text.of(t);
    }

    protected Text buildDeathInWaterButtonTitle() {
        return buildButtonTitle("option.deathInWater.title", SoulslikeDeathsClient.cfg.deathInWater);
    }

    protected Text buildDeathOnDamagedButtonTitle() {
        return buildButtonTitle("option.deathOnDamaged.title", SoulslikeDeathsClient.cfg.deathOnDamaged);
    }

    protected Text buildDeathByHeadShotButtonTitle() {
        return buildButtonTitle("option.deathByHeadShot.title", SoulslikeDeathsClient.cfg.deathByHeadShot);
    }

    protected Text buildDeathWhenRunningButtonTitle() {
        return buildButtonTitle("option.deathWhenRunning.title", SoulslikeDeathsClient.cfg.deathWhenRunning);
    }

    @Override
    protected void init(){
        this.addDrawableChild(buildDeathInWaterButton(20));
        this.addDrawableChild(buildDeathOnDamagedButton(40));
        this.addDrawableChild(buildDeathByHeadShotButton(60));
        this.addDrawableChild(buildDeathWhenRunningButton(80));

        this.addDrawableChild(
                ButtonWidget.builder(
                                Text.of("Done"),
                                button -> {
                                    SoulslikeDeathsClient.cfg.save();
                                    this.client.setScreen(this.parent);
                                }
                        )
                        .dimensions(
                                posXCenter(this.ButtonWidth),
                                this.height / 6 + 144,
                                this.ButtonWidth, this.ButtonHeight)
                        .build()
        );
    }

    ButtonWidget buildButton(ButtonWidget.Builder builder, int yPos) {
        return builder.dimensions(
                        posXCenter(this.ButtonWidth),
                        yPos,
                        this.ButtonWidth, this.ButtonHeight)
                .build();
    }

    ButtonWidget buildDeathInWaterButton(int yPos) {
        return buildButton( ButtonWidget.builder(
                        buildDeathInWaterButtonTitle(),
                        button -> {
                            SoulslikeDeathsClient.cfg.toggleDeathInWater();
                            button.setMessage(buildDeathInWaterButtonTitle());
                        }
                    ),
                yPos
        );
    }

    ButtonWidget buildDeathOnDamagedButton(int yPos) {
        return buildButton( ButtonWidget.builder(
                        buildDeathOnDamagedButtonTitle(),
                        button -> {
                            SoulslikeDeathsClient.cfg.toggleDeathOnDamaged();
                            button.setMessage(buildDeathOnDamagedButtonTitle());
                        }
                ),
                yPos
        );
    }

    ButtonWidget buildDeathByHeadShotButton(int yPos) {
        return buildButton( ButtonWidget.builder(
                        buildDeathByHeadShotButtonTitle(),
                        button -> {
                            SoulslikeDeathsClient.cfg.toggleDeathByHeadShot();
                            button.setMessage(buildDeathByHeadShotButtonTitle());
                        }
                ),
                yPos
        );
    }

    ButtonWidget buildDeathWhenRunningButton(int yPos) {
        return buildButton( ButtonWidget.builder(
                        buildDeathWhenRunningButtonTitle(),
                        button -> {
                            SoulslikeDeathsClient.cfg.toggleDeathWhenRunning();
                            button.setMessage(buildDeathWhenRunningButtonTitle());
                        }
                ),
                yPos
        );
    }

    @Override
    protected void addOptions() {

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("option_title"), width / 2, 5, 0xFFFFFF);
    }
}
