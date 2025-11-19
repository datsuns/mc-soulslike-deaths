package me.datsuns.soulslikedeaths.neoforge;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/**
 * Custom config screen so we can mirror the Fabric UI with localized state labels.
 */
public class SoulslikeDeathsNeoForgeConfigScreen extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;

    private final Screen parent;
    private final SoulslikeDeathsNeoForgeConfig config = SoulslikeDeathsNeoForgeConfig.INSTANCE;

    public SoulslikeDeathsNeoForgeConfigScreen(Screen parent) {
        super(Component.translatable("option_title"));
        this.parent = parent;
    }

    private int centerX() {
        return (this.width / 2) - (BUTTON_WIDTH / 2);
    }

    @Override
    protected void init() {
        int startY = 40;
        this.addRenderableWidget(buildDeathInWaterButton(startY));
        this.addRenderableWidget(buildDeathOnDamagedButton(startY + 24));
        this.addRenderableWidget(buildDeathByHeadShotButton(startY + 48));
        this.addRenderableWidget(buildDeathWhenRunningButton(startY + 72));
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
                .pos(centerX(), this.height / 6 + 144)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private Button buildDeathInWaterButton(int yPos) {
        return createToggleButton(buildDeathInWaterTitle(), yPos, () -> {
            config.toggleDeathInWater();
            return buildDeathInWaterTitle();
        });
    }

    private Button buildDeathOnDamagedButton(int yPos) {
        return createToggleButton(buildDeathOnDamagedTitle(), yPos, () -> {
            config.toggleDeathOnDamaged();
            return buildDeathOnDamagedTitle();
        });
    }

    private Button buildDeathByHeadShotButton(int yPos) {
        return createToggleButton(buildDeathByHeadShotTitle(), yPos, () -> {
            config.toggleDeathByHeadShot();
            return buildDeathByHeadShotTitle();
        });
    }

    private Button buildDeathWhenRunningButton(int yPos) {
        return createToggleButton(buildDeathWhenRunningTitle(), yPos, () -> {
            config.toggleDeathWhenRunning();
            return buildDeathWhenRunningTitle();
        });
    }

    private Button createToggleButton(Component initial, int yPos, ToggleAction action) {
        return Button.builder(initial, button -> button.setMessage(action.run()))
                .pos(centerX(), yPos)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build();
    }

    private Component buildDeathInWaterTitle() {
        return buildButtonTitle("option.deathInWater.title", config.deathInWater());
    }

    private Component buildDeathOnDamagedTitle() {
        return buildButtonTitle("option.deathOnDamaged.title", config.deathOnDamaged());
    }

    private Component buildDeathByHeadShotTitle() {
        return buildButtonTitle("option.deathByHeadShot.title", config.deathByHeadShot());
    }

    private Component buildDeathWhenRunningTitle() {
        return buildButtonTitle("option.deathWhenRunning.title", config.deathWhenRunning());
    }

    private Component buildButtonTitle(String translationKey, boolean value) {
        Component label = Component.translatable(translationKey);
        Component state = Component.translatable(value ? "option.true" : "option.false")
                .copy()
                .withStyle(value ? ChatFormatting.RED : ChatFormatting.GREEN);
        return Component.empty()
                .append(label)
                .append(Component.literal(" : "))
                .append(state);
    }

    @Override
    public void onClose() {
        config.save();
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);
    }

    @FunctionalInterface
    private interface ToggleAction {
        Component run();
    }
}
